*** Settings ***
Resource          公共资源.robot

*** Keywords ***
OpenAPI_Post
    [Arguments]    ${data}    ${api_url}
    [Documentation]    收单接口调用，入参${data}，${api_url}返回${Respones}
    [Timeout]    30 seconds
    ${dict}    Create Dictionary    Content-Type=application/x-www-form-urlencoded; charset=UTF-8
    Create Session    Open_Api    %{${env}}
    ${Respones}    Post Request    Open_Api    ${api_url}    headers=${dict}    data=${data}
    Comment    ${Respones}    post    Open_Api    ${api_url}    headers=${dict}    data=${data}
    ${Res_data}    To Json    ${Respones.content}
    [Return]    ${Res_data}

DB_Connector_Mysql
    [Arguments]    ${SQL_command}
    [Documentation]    查询测试平台数据库    #${database}：数据库名（需要查询的数据库）
    ...
    ...    输入参数：${SQL_command}
    ...
    ...    ${SQL_command}：要执行的SQL语句
    ...
    ...
    ...
    ...    返回值：${query_data}：SQL查询返回值
    ${databaseConnection}    set variable    host='101.132.162.77',port=3306,user='qp',password='qp',database='qp',charset='utf8'
    Connect To Database Using Custom Params    pymysql    ${databaseConnection}
    @{query_data}    query    ${SQL_command}
    Disconnect From Database
    [Return]    @{query_data}

Redis_DEL
    [Arguments]    ${key}
    [Documentation]    连接Redis，删除${key}
    ${redis_conn}    Connect To Redis    139.196.75.96    3333    0    AdeTTT32312ttedsdcaHUHU
    Delete From Redis    ${redis_conn}    ${key}
    #Get From Redis

DB_Connector_Oracle
    [Arguments]    ${env}    ${SQL_command}
    [Documentation]    查询测试平台数据库
    ...
    ...    输入参数： ${env} | ${database} | ${SQL_command}
    ...
    ...    ${env}：TestOne、TestTwo、LianTiao
    ...
    ...    ${database}：数据库名（需要查询的数据库）
    ...
    ...    ${SQL_command}：要执行的SQL语句
    ...
    ...    返回值：${query_data}：SQL查询返回值
    ${databaseConnection}    Run Keyword If    '${env}'=='TestOne'    set variable    'sa','sa123456','139.224.10.101:2521/ipay'
    ...    ELSE IF    '${env}'=='TestTwo'    set variable    'sa','sa123456','139.196.122.1:2521/ipay'
    ...    ELSE IF    '${env}'=='LianTiao'    set variable    'ipayquery','rxIMencaOyaOBHYaubg5','47.100.8.70:2521/ipay'
    Connect To Database Using Custom Params    cx_Oracle    ${databaseConnection}
    Execute Sql String    ${SQL_command}
    Disconnect From Database

Run_Pre
    [Arguments]    ${env}    ${case_data}
    ${data_list}    Split String    ${case_data[7]}    &
    ${case_string}    Remove String    ${data_list[0]}    ;commit;
    ${len}    Get Length    ${case_data[7]}
    Run Keyword If    '${len}'!='0'    Run Keywords    DB_Connector_Oracle    ${env}    ${case_string}
    ...    AND    Redis_DEL    ${data_list[1]}
    Redis_Set    123    20000123    300

Run_Post
    [Arguments]    ${env}    ${case_data}
    ${data_list}    Split String    ${case_data[8]}    &
    ${case_string}    Remove String    ${data_list[0]}    ;commit;
    ${len}    Get Length    ${case_data[8]}
    Run Keyword If    '${len}'!='0'    Run Keywords    DB_Connector_Oracle    ${env}    ${case_string}
    ...    AND    Redis_DEL    ${data_list[1]}

Replace_Id
    [Arguments]    ${case_dic}    ${registerUserId}=0    ${token}=0
    [Documentation]    自动生成registerUserId和transId并替换json报文中对应字段的值
    ...
    ...    入参：
    ...    ${case_dic}---json报文； \ \ \ ${registerUserId}，${token}，这两个字段只有在做关联后需要替换对应字段的值才需要输入入参；
    ...
    ...    出参：
    ...    ${registerUserId}---供后续步骤关联使用，无需要则不用出参
    ...
    ...    例如：
    ...    只替换registerUserId和transId：
    ...    | Replace Id | ${case_dic} |
    ...    替换关联的registerUserId和token：
    ...    | Replace Id | ${case_dic} | ${registerUserId} | ${token} |
    ${registerUserId}    Run Keyword If    '${registerUserId}'=='0'    Set RandomId To Dictionary    ${case_dic}    registerUserId=20
    ...    ELSE    Set To Dictionary    ${case_dic}    registerUserId=${registerUserId}
    Run Keyword If    '${token}'!='0'    Set To Dictionary    ${case_dic}    token=${token}
    Set RandomId To Dictionary    ${case_dic}    transId=32
    [Return]    ${registerUserId}

Sign_Data
    [Arguments]    ${case_dic}    ${pkey_file}=
    ${pkey}    Get From Dictionary    ${case_dic}    pkey
    Remove From Dictionary    ${case_dic}    pkey    URL    URI    url    uri
    ...    #删除多余字段
    ${signType}    Get From Dictionary    ${case_dic}    signType
    ${case_str}    Join_Dictionary_To_String    ${case_dic}    #字符串排序、拼接
    ${case_str_md5}    Catenate    SEPARATOR=    ${case_str}    &pkey=    ${pkey}
    Comment    ${case_str}    Convert To String    ${case_str}
    Comment    ${case_str_md5}    Convert To String    ${case_str_md5}
    ${sign}    Run Keyword If    '${signType}'=='MD5'    Md 5    ${case_str_md5}
    ...    ELSE IF    '${signType}'=='RSA'    Rsa Sign    ${case_str}    ${pkey_file}
    #拼装加签的报文体
    ${signed}    Catenate    SEPARATOR=    ${case_str}    &sign=    ${sign}
    [Return]    ${signed}

Record_Result
    [Arguments]    ${result_data}
    ${rc}    Run Keyword And Ignore Error    File Should Exist    %{TOTAL_RESULT}
    Run Keyword If    '${rc[0]}'=='FAIL'    Create File    %{TOTAL_RESULT}    ${result_data}
    ...    ELSE IF    '${rc[0]}'=='PASS'    Append To File    %{TOTAL_RESULT}    |${result_data}    #加\n

http_Post
    [Arguments]    ${env}    ${api_url}    ${data}    ${headers}
    [Documentation]    收单接口调用，入参${data}，${api_url}返回${Respones}
    [Timeout]    30 seconds
    Create Session    http_api    ${env}    ${headers}
    ${Response}    Post Request    http_api    ${api_url}    ${data}
    [Return]    ${Response}

Redis_Set
    [Arguments]    ${key}    ${value}    ${time}
    [Documentation]    连接Redis，删除${key}
    ${redis_conn}    Connect To Redis    139.196.86.188    3333    0    AdeTTT32312ttedsdcaHUHU
    Set To Redis    ${redis_conn}    ${key}    ${value}    ${time}

login_token
    [Arguments]    ${test_data}
    ${login_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "loginName": "string", \ \ \ \ "password": "string" \ \ }, \ \ "traceId": "string" }    #定义报文模板    responseMsg
    ${login_post_data}    To Json    ${login_post_data}
    ${case_dic}    Split String    ${test_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    #根据测试数据替换报文内容    ${res}
    ${requestObj}    Get From Dictionary    ${login_post_data}    requestObj
    ${requestObj}    Replace Test Jsondata    ${requestObj}    ${case_dic1}
    Set To Dictionary    ${login_post_data}    requestObj=${requestObj}
    log    ${login_post_data}
    #发送登录请求    #172.17.1.131
    ${headers}    Create_Dictionary    Content-Type=application/json
    ${res}    http_Post    %{${env}}    /mes-gateway/api/security/login.json    ${login_post_data}    ${headers}
    ${res}    TO JSON    ${res.content}
    #断言
    ${expect_data}    TO JSON    ${test_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    #获取token
    ${responseObj}    Get From Dictionary    ${res}    responseObj
    ${token}    Get From Dictionary    ${responseObj}    token
    ${token}    Set Global Variable    ${token}
    [Return]    ${token}

queryPlatSiteList_json
    [Arguments]    ${sitelist_data}
    ${sitelist_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "pageBean": { \ \ \ \ "orderBy": "string", \ \ \ \ "pageNumber": 0, \ \ \ \ "pageSize": 0 \ \ }, \ \ "traceId": "string" }
    ${sitelist_post_data}    To Json    ${sitelist_post_data}
    ${case_dic}    Split String    ${sitelist_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${sitelist_post_data}    Replace Test Jsondata    ${sitelist_post_data}    ${case_dic1}
    Log    ${sitelist_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/plat/queryPlatSiteList.json    ${sitelist_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${sitelist_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

queryPlatAuthUrl_json
    [Arguments]    ${authurl_data}
    ${authurl_post_data}    Set Variable    { \ \ "platId": 0 }
    ${authurl_post_data}    To Json    ${authurl_post_data}
    ${case_dic}    Split String    ${authurl_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${authurl_post_data}    Replace Test Jsondata    ${authurl_post_data}    ${case_dic1}
    Log    ${authurl_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/plat/queryPlatAuthUrl.json    ${authurl_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${authurl_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

queryPlatAuthResult_json
    [Arguments]    ${platauth_data}
    ${platauth_post_data}    Set Variable    { \ \ "eBayDeveloperID": "string", \ \ "eBaySessionID": "string" }
    ${platauth_post_data}    To Json    ${platauth_post_data}
    ${case_dic}    Split String    ${platauth_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${platauth_post_data}    Replace Test Jsondata    ${platauth_post_data}    ${case_dic1}
    Log    ${platauth_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/plat/queryPlatAuthResult.json    ${platauth_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${platauth_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

index_withdew_json
    ${index_withdew_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/index/tradingDynamics/withdew.json    ${index_withdew_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    ${provinceData}    Get From Dictionary    ${res}    responseObj
    Log    ${provinceData}

exchangeRate_json
    [Arguments]    ${exchangerate}
    ${exchangerate_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "currency": "string", \ \ \ \ "period": 0, \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }    #定义报文模板    responseMsg
    ${exchangerate_post_data}    To Json    ${exchangerate_post_data}
    ${case_dic}    Split String    ${exchangerate[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    #根据测试数据替换报文内容    ${res}
    ${requestObj}    Get From Dictionary    ${exchangerate_post_data}    requestObj
    ${requestObj}    Replace Test Jsondata    ${requestObj}    ${case_dic1}
    Set To Dictionary    ${exchangerate_post_data}    requestObj=${requestObj}
    log    ${exchangerate_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${res}    http_Post    %{${env}}    /mes-gateway/api/index/exchangeRate.json    ${exchangerate_post_data}    ${headers}
    ${res}    TO JSON    ${res.content}
    #断言
    ${expect_data}    TO JSON    ${exchangerate[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

baseInfo_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/bankcard/baseInfo.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}

addBankCard_json
    [Arguments]    ${bankcard_data}
    ${bankcard_post_data}    Set Variable    { \ \ "acctName": "string", \ \ "bankAcct": "string", \ \ "bankAcctType": 0, \ \ "bankAddress": "string", \ \ "bankCnapsCode": "string", \ \ "bankCountry": "string", \ \ "bankName": "string", \ \ "branchBankId": "string", \ \ "city": 0, \ \ "currency": "string", \ \ "memberCode": 0, \ \ "openBankName": "string", \ \ "province": 0, \ \ "reservedPhone": "string", \ \ "swiftCode": "string", \ \ "verifyCode": "string", \ \ "verifyCodeTime": "string" }    #定义报文模板    responseMsg
    ${bankcard_post_data}    To Json    ${bankcard_post_data}
    ${case_dic}    Split String    ${bankcard_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    #根据测试数据替换报文内容    ${res}
    ${bankcard_post_data}    Replace Test Jsondata    ${bankcard_post_data}    ${case_dic1}
    log    ${bankcard_post_data}
    Redis_Set    MES_GATEWAY_SMS_15927514949_add_bank_card    "123456"    300
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${res}    http_Post    %{${env}}    /mes-gateway/api/bankcard/addBankCard.json    ${bankcard_post_data}    ${headers}
    ${res}    TO JSON    ${res.content}
    #断言
    ${expect_data}    TO JSON    ${bankcard_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

bankCardDetail_json
    [Arguments]    ${carddetail_data}
    ${carddetail_post_data}    Set Variable    { \ \ "currency": "string", \ \ "liquidateId": 0, \ \ "memberCode": 0 }    #定义报文模板    responseMsg
    ${carddetail_post_data}    To Json    ${carddetail_post_data}
    ${case_dic}    Split String    ${carddetail_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    #根据测试数据替换报文内容    ${res}
    ${carddetail_post_data}    Replace Test Jsondata    ${carddetail_post_data}    ${case_dic1}
    log    ${carddetail_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${res}    http_Post    %{${env}}    /mes-gateway/api/bankcard/detail.json    ${carddetail_post_data}    ${headers}
    ${res}    TO JSON    ${res.content}
    #断言
    ${expect_data}    TO JSON    ${carddetail_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

bankCardList_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/bankcard/pageList.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}

removeCard_json
    [Arguments]    ${remove_data}
    ${remove_post_data}    Set Variable    { \ \ "currency": "string", \ \ "liquidateId": 0, \ \ "memberCode": 0 }    #定义报文模板    responseMsg
    ${remove_post_data}    To Json    ${remove_post_data}
    ${case_dic}    Split String    ${remove_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    #根据测试数据替换报文内容    ${res}
    ${remove_post_data}    Replace Test Jsondata    ${remove_post_data}    ${case_dic1}
    log    ${remove_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${res}    http_Post    %{${env}}    /mes-gateway/api/bankcard/removeCard.json    ${remove_post_data}    ${headers}
    ${res}    TO JSON    ${res.content}
    #断言
    ${expect_data}    TO JSON    ${remove_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

updateCurrency_json
    [Arguments]    ${update_data}
    ${update_post_data}    Set Variable    { \ \ "currency": "string", \ \ "liquidateId": 0, \ \ "memberCode": 0 }    #定义报文模板    responseMsg
    ${update_post_data}    To Json    ${update_post_data}
    ${case_dic}    Split String    ${update_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    #根据测试数据替换报文内容    ${res}
    ${update_post_data}    Replace Test Jsondata    ${update_post_data}    ${case_dic1}
    log    ${update_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${res}    http_Post    %{${env}}    /mes-gateway/api/bankcard/updateCurrency.json    ${update_post_data}    ${headers}
    ${res}    TO JSON    ${res.content}
    #断言
    ${expect_data}    TO JSON    ${update_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

index_recentWithdraw_json
    [Arguments]    ${recentWithdraw_Data}
    ${recentWithdraw_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "currency": "string", \ \ \ \ "memberCode": 0, \ \ \ \ "opTime": "2019-03-25T07:48:49.916Z", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${recentWithdraw_post_data}    To Json    ${recentWithdraw_post_data}
    ${case_dic}    Split String    ${recentWithdraw_Data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    #根据测试数据替换报文内容    ${res}
    ${requestObj}    Get From Dictionary    ${recentWithdraw_post_data}    requestObj
    ${requestObj}    Replace Test Jsondata    ${requestObj}    ${case_dic1}
    Set To Dictionary    ${recentWithdraw_post_data}    requestObj=${requestObj}
    log    ${recentWithdraw_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${res}    http_Post    %{${env}}    /mes-gateway/api/index/recentWithdraw.json    ${recentWithdraw_post_data}    ${headers}
    ${res}    TO JSON    ${res.content}
    #断言
    ${expect_data}    TO JSON    ${recentWithdraw_Data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

index_inAccount_json
    ${inaccount_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/index/tradingDynamics/inAccount.json    ${inaccount_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}

index_recentTradeCash_json
    [Arguments]    ${recentTradeCash_data}
    ${recentTradeCash_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "currency": "string", \ \ \ \ "days": 0, \ \ \ \ "memberId": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${recentTradeCash_post_data}    To Json    ${recentTradeCash_post_data}
    ${case_dic}    Split String    ${recentTradeCash_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    #根据测试数据替换报文内容    ${res}
    ${requestObj}    Get From Dictionary    ${recentTradeCash_post_data}    requestObj
    ${requestObj}    Replace Test Jsondata    ${requestObj}    ${case_dic1}
    Set To Dictionary    ${recentTradeCash_post_data}    requestObj=${requestObj}
    log    ${recentTradeCash_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${res}    http_Post    %{${env}}    /mes-gateway/api/index/recentTradeCash.json    ${recentTradeCash_post_data}    ${headers}
    ${res}    TO JSON    ${res.content}
    #断言
    ${expect_data}    TO JSON    ${recentTradeCash_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

index_baseInfo_json
    ${index_balance_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/index/baseInfo.json    ${index_balance_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}

index_balance_json
    ${index_balance_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/index/balance.json    ${index_balance_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}

receiptorLists_json
    [Arguments]    ${receiptor_data}
    ${receiptor_post_data}    Set Variable    { \ \ "bankNo": "string", \ \ "clientCode": "string", \ \ "currency": "string", \ \ "memberCode": 0, \ \ "name": "string", \ \ "pageBean": { \ \ \ \ "orderBy": "string", \ \ \ \ "pageNumber": 0, \ \ \ \ "pageSize": 0 \ \ }, \ \ "region": "string", \ \ "status": 0, \ \ "traceId": "string", \ \ "type": 0 }
    ${receiptor_post_data}    To Json    ${receiptor_post_data}
    ${case_dic}    Split String    ${receiptor_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${receiptor_post_data}    Replace Test Jsondata    ${receiptor_post_data}    ${case_dic1}
    Log    ${receiptor_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/receiptor/lists.json    ${receiptor_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${receiptor_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

addReceiptor_json
    [Arguments]    ${receiptor_data}
    ${receiptor_post_data}    Set Variable    { \ \ "address": "string", \ \ "bankAccountName": "string", \ \ "bankAddress": "string", \ \ "bankName": "string", \ \ "bankNo": "string", \ \ "bankOpenCode": "string", \ \ "bankReceiptAddress": "string", \ \ "currency": "string", \ \ "email": "string", \ \ "memberCode": 0, \ \ "name": "string", \ \ "region": "string", \ \ "swiftCode": "string", \ \ "type": "string" }
    ${receiptor_post_data}    To Json    ${receiptor_post_data}
    ${case_dic}    Split String    ${receiptor_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${receiptor_post_data}    Replace Test Jsondata    ${receiptor_post_data}    ${case_dic1}
    Log    ${receiptor_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/receiptor/add.json    ${receiptor_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${receiptor_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

getAuthenticationStatus_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/modifyLoginPwd/getAuthenticationStatus.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    ${status}    Get From Dictionary    ${res}    responseObj
    Log    ${status}

queryProvinceList_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/modifyLoginPwd/queryProvinceList.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    ${provinceData}    Get From Dictionary    ${res}    responseObj
    Log    ${provinceData}

queryCityList_json
    [Arguments]    ${citylist_data}
    ${citylist_post_data}    Set Variable    { \ \ "province": "string" }
    ${citylist_post_data}    To Json    ${citylist_post_data}
    ${case_dic}    Split String    ${citylist_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${citylist_post_data}    Replace Test Jsondata    ${citylist_post_data}    ${case_dic1}
    Log    ${citylist_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/modifyLoginPwd/queryCityList.json    ${citylist_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${citylist_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

companyAuthenticationResult_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/modifyLoginPwd/companyAuthenticationResult.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}

checkEmail_json
    [Arguments]    ${check_data}
    ${check_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": "string", \ \ "traceId": "string" }
    ${check_post_data}    To Json    ${check_post_data}
    ${case_dic}    Split String    ${check_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${check_post_data}    Replace Test Jsondata    ${check_post_data}    ${case_dic1}
    Log    ${check_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/sign/check/email.json    ${check_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    ${result}    Get From Dictionary    ${res}    responseObj
    #断言
    ${expect_data}    TO JSON    ${check_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${result}

checkMobile_json
    [Arguments]    ${check_data}
    ${check_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": "string", \ \ "traceId": "string" }
    ${check_post_data}    To Json    ${check_post_data}
    ${case_dic}    Split String    ${check_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${check_post_data}    Replace Test Jsondata    ${check_post_data}    ${case_dic1}
    Log    ${check_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/sign/check/mobile.json    ${check_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    ${result}    Get From Dictionary    ${res}    responseObj
    #断言
    ${expect_data}    TO JSON    ${check_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${result}

activeEmail_json
    [Arguments]    ${activeEmail_data}
    ${active_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "email": "string", \ \ \ \ "token": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${active_post_data}    To Json    ${active_post_data}
    ${case_dic}    Split String    ${activeEmail_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${requestObj}    Get From Dictionary    ${active_post_data}    requestObj
    ${requestObj}    Replace Test Jsondata    ${requestObj}    ${case_dic1}
    Set To Dictionary    ${active_post_data}    requestObj=${requestObj}
    Log    ${active_post_data}
    Redis_Set    123    20000001486    43200
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/sign/signup/activeEmail.json    ${active_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${activeEmail_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

signupEmail_json
    [Arguments]    ${signupEmail_data}
    ${signup_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "agreement": true, \ \ \ \ "clientCode": "string", \ \ \ \ "email": "1010725843@qq.com", \ \ \ \ "recommendCode": "string", \ \ \ \ "sourceType": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${signup_post_data}    To Json    ${signup_post_data}
    ${case_dic}    Split String    ${signupEmail_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${requestObj}    Get From Dictionary    ${signup_post_data}    requestObj
    ${requestObj}    Replace Test Jsondata    ${requestObj}    ${case_dic1}
    Set To Dictionary    ${signup_post_data}    requestObj=${requestObj}
    Log    ${signup_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/sign/signup/email.json    ${signup_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${signupEmail_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

firstLoginActive_json
    [Arguments]    ${firstActive_data}
    ${active_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "email": "string", \ \ \ \ "token": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${active_post_data}    To Json    ${active_post_data}
    ${case_dic}    Split String    ${firstActive_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${requestObj}    Get From Dictionary    ${active_post_data}    requestObj
    ${requestObj}    Replace Test Jsondata    ${requestObj}    ${case_dic1}
    Set To Dictionary    ${active_post_data}    requestObj=${requestObj}
    Log    ${active_post_data}
    ${email}    Get From Dictionary    ${requestObj}    email
    ${token_active}    catenate    SEPARATOR=    MES_REDIS_EMAIL_    ${email}
    Log    ${token_active}
    Redis_Set    ${token_active}    123    43200
    ${headers}    Create_Dictionary    Content-Type=application/json
    ${response}    http_Post    %{${env}}    /mes-gateway/api/sign/signup/firstLoginActive.json    ${active_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${firstActive_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

firstTimeLogin_json
    [Arguments]    ${firstTimeLogin_data}
    ${firstTime_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "email": "string", \ \ \ \ "loginPhone": "string", \ \ \ \ "loginPwd": "string", \ \ \ \ "loginPwdRepeat": "string", \ \ \ \ "moblileVerifCode": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${firstTime_post_data}    To Json    ${firstTime_post_data}
    ${case_dic}    Split String    ${firstTimeLogin_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${requestObj}    Get From Dictionary    ${firstTime_post_data}    requestObj
    ${requestObj}    Replace Test Jsondata    ${requestObj}    ${case_dic1}
    Set To Dictionary    ${firstTime_post_data}    requestObj=${requestObj}
    Log    ${firstTime_post_data}
    ${email}    Get From Dictionary    ${requestObj}    email
    ${token_active}    catenate    SEPARATOR=    MES_REDIS_EMAIL_    ${email}
    Log    ${token_active}
    Redis_Set    ${token_active}    123    43200
    ${headers}    Create_Dictionary    Content-Type=application/json
    ${response}    http_Post    %{${env}}    /mes-gateway/api/sign/signup/firstTimeLogin.json    ${firstTime_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${firstTimeLogin_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

improveInfo_json
    [Arguments]    ${improveInfo_data}
    ${improveInfo_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "country": "string", \ \ \ \ "email": "string", \ \ \ \ "inviteCode": "string", \ \ \ \ "ip": "string", \ \ \ \ "loginPhone": "string", \ \ \ \ "moblileVerifCode": "string", \ \ \ \ "payPwd": "string", \ \ \ \ "payPwdRepeat": "string", \ \ \ \ "pwd": "string", \ \ \ \ "pwdRepeat": "string", \ \ \ \ "recommendCode": 0, \ \ \ \ "registerSource": "string", \ \ \ \ "signType": 0, \ \ \ \ "sourceType": "string", \ \ \ \ "terminalType": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${improveInfo_post_data}    To Json    ${improveInfo_post_data}
    ${case_dic}    Split String    ${improveInfo_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${requestObj}    Get From Dictionary    ${improveInfo_post_data}    requestObj
    ${requestObj}    Replace Test Jsondata    ${requestObj}    ${case_dic1}
    Set To Dictionary    ${improveInfo_post_data}    requestObj=${requestObj}
    Log    ${improveInfo_post_data}
    ${mobileNo}    Get From Dictionary    ${requestObj}    loginPhone
    ${mobileVerifyCode}    catenate    SEPARATOR=    MES_GATEWAY_SMS_    ${mobileNo}
    Log    ${mobileVerifyCode}
    Redis_Set    ${mobileVerifyCode}    "123456"    300
    ${headers}    Create_Dictionary    Content-Type=application/json
    ${response}    http_Post    %{${env}}    /mes-gateway/api/sign/signup/improveInfo.json    ${improveInfo_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${improveInfo_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

signupMobile_json
    [Arguments]    ${signupMobile_data}
    [Documentation]    POST /api/sign/signup/mobile.json
    ...    手机注册,点击立即注册
    ${signupMobile_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "agreement": true, \ \ \ \ "clientCode": "string", \ \ \ \ "loginPhone": "string", \ \ \ \ "moblileVerifCode": "string", \ \ \ \ "recommendCode": "string", \ \ \ \ "sourceType": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }
    ${signupMobile_post_data}    To Json    ${signupMobile_post_data}
    ${case_dic}    Split String    ${signupMobile_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${requestObj}    Get From Dictionary    ${signupMobile_post_data}    requestObj
    ${requestObj}    Replace Test Jsondata    ${requestObj}    ${case_dic1}
    Set To Dictionary    ${signupMobile_post_data}    requestObj=${requestObj}
    Log    ${signupMobile_post_data}
    ${mobileNo}    Get From Dictionary    ${requestObj}    loginPhone
    ${mobileVerifyCode}    catenate    SEPARATOR=    MES_GATEWAY_SMS_    ${mobileNo}    _user_regist
    Log    ${mobileVerifyCode}
    Redis_Set    ${mobileVerifyCode}    "123456"    300
    ${headers}    Create_Dictionary    Content-Type=application/json
    ${response}    http_Post    %{${env}}    /mes-gateway/api/sign/signup/mobile.json    ${signupMobile_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${signupMobile_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

changeAuth_json
    [Arguments]    ${changeAuth_data}
    [Documentation]    POST /api/shop/manager/changeAuth.json
    ...    变更授权接口（亚马逊）
    ${changeAuth_post_data}    Set Variable    { \ \ "address": "string", \ \ "authData": "string", \ \ "bankAccountName": "string", \ \ "businessCode": "string", \ \ "businessLicenseUrl": "string", \ \ "cerBackUrl": "string", \ \ "cerCode": "string", \ \ "cerFrontUrl": "string", \ \ "eBaySessionID": "string", \ \ "id": 0, \ \ "memberCode": 0, \ \ "mwsAuthToken": "string", \ \ "name": "string", \ \ "needShopAuth": 0, \ \ "payPal": "string", \ \ "platDeveloperId": "string", \ \ "platId": 0, \ \ "platName": "string", \ \ "salesCategory": "string", \ \ "sellerId": "string", \ \ "shopIentity": "string", \ \ "status": 0, \ \ "unShopFlag": 0, \ \ "wish": "string" }
    ${changeAuth_post_data}    To Json    ${changeAuth_post_data}
    ${case_dic}    Split String    ${changeAuth_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${changeAuth_post_data}    Replace Test Jsondata    ${changeAuth_post_data}    ${case_dic1}
    Log    ${changeAuth_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/changeAuth.json    ${changeAuth_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${changeAuth_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

completedBanding_json
    [Arguments]    ${completedBanding_data}
    [Documentation]    POST /api/shop/manager/completedBanding.json
    ...    完善资料接口（亚马逊)
    ${completedBanding_post_data}    Set Variable    { \ \ "address": "string", \ \ "authData": "string", \ \ "bankAccountName": "string", \ \ "businessCode": "string", \ \ "businessLicenseUrl": "string", \ \ "cerBackUrl": "string", \ \ "cerCode": "string", \ \ "cerFrontUrl": "string", \ \ "eBaySessionID": "string", \ \ "id": 0, \ \ "memberCode": 0, \ \ "mwsAuthToken": "string", \ \ "name": "string", \ \ "needShopAuth": 0, \ \ "payPal": "string", \ \ "platDeveloperId": "string", \ \ "platId": 0, \ \ "platName": "string", \ \ "salesCategory": "string", \ \ "sellerId": "string", \ \ "shopIentity": "string", \ \ "status": 0, \ \ "unShopFlag": 0, \ \ "wish": "string" }
    ${completedBanding_post_data}    To Json    ${completedBanding_post_data}
    ${case_dic}    Split String    ${completedBanding_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${completedBanding_post_data}    Replace Test Jsondata    ${completedBanding_post_data}    ${case_dic1}
    Log    ${completedBanding_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/completedBanding.json    ${completedBanding_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${completedBanding_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

reAuthEbay_json
    [Arguments]    ${reAuthEbay_data}
    [Documentation]    POST /api/shop/manager/reAuthEbay.json
    ...    重新授权接口（eBay）
    ${reAuthEbay_post_data}    Set Variable    { \ \ "address": "string", \ \ "authData": "string", \ \ "bankAccountName": "string", \ \ "businessCode": "string", \ \ "businessLicenseUrl": "string", \ \ "cerBackUrl": "string", \ \ "cerCode": "string", \ \ "cerFrontUrl": "string", \ \ "eBaySessionID": "string", \ \ "id": 0, \ \ "memberCode": 0, \ \ "mwsAuthToken": "string", \ \ "name": "string", \ \ "needShopAuth": 0, \ \ "payPal": "string", \ \ "platDeveloperId": "string", \ \ "platId": 0, \ \ "platName": "string", \ \ "salesCategory": "string", \ \ "sellerId": "string", \ \ "shopIentity": "string", \ \ "status": 0, \ \ "unShopFlag": 0, \ \ "wish": "string" }
    ${reAuthEbay_post_data}    To Json    ${reAuthEbay_post_data}
    ${case_dic}    Split String    ${reAuthEbay_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${reAuthEbay_post_data}    Replace Test Jsondata    ${reAuthEbay_post_data}    ${case_dic1}
    Log    ${reAuthEbay_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/reAuthEbay.json    ${reAuthEbay_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${reAuthEbay_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

reSubmitShop_json
    [Arguments]    ${reSubmitShop_data}
    [Documentation]    POST /api/shop/manager/reSubmitShop.json
    ...    重新提交接口
    ${reSubmitShop_post_data}    Set Variable    { \ \ "address": "string", \ \ "authData": "string", \ \ "bankAccountName": "string", \ \ "businessCode": "string", \ \ "businessLicenseUrl": "string", \ \ "cerBackUrl": "string", \ \ "cerCode": "string", \ \ "cerFrontUrl": "string", \ \ "eBaySessionID": "string", \ \ "id": 0, \ \ "memberCode": 0, \ \ "mwsAuthToken": "string", \ \ "name": "string", \ \ "needShopAuth": 0, \ \ "payPal": "string", \ \ "platDeveloperId": "string", \ \ "platId": 0, \ \ "platName": "string", \ \ "salesCategory": "string", \ \ "sellerId": "string", \ \ "shopIentity": "string", \ \ "status": 0, \ \ "unShopFlag": 0, \ \ "wish": "string" }
    ${reSubmitShop_post_data}    To Json    ${reSubmitShop_post_data}
    ${case_dic}    Split String    ${reSubmitShop_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${reSubmitShop_post_data}    Replace Test Jsondata    ${reSubmitShop_post_data}    ${case_dic1}
    Log    ${reSubmitShop_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/reSubmitShop.json    ${reSubmitShop_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${reSubmitShop_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

souqTradeOrderFailureResult_json
    [Arguments]    ${failureResult_data}
    [Documentation]    POST /api/shop/manager/reSubmitShop.json
    ...    重新提交接口
    ${failureResult_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "id": 0, \ \ "memberCode": 0, \ \ "platId": 0, \ \ "traceId": "string" }
    ${failureResult_post_data}    To Json    ${failureResult_post_data}
    ${case_dic}    Split String    ${failureResult_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${failureResult_post_data}    Replace Test Jsondata    ${failureResult_post_data}    ${case_dic1}
    Log    ${failureResult_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/souqTradeOrderFailureResult.json    ${failureResult_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${failureResult_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

souqTradeOrderSelectBatch_json
    [Arguments]    ${selectBatch_data}
    [Documentation]    POST /api/shop/manager/souqTradeOrderSelectBatch.json
    ...    上传Souq订单历史记录
    ${selectBatch_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "memberCode": 0, \ \ "orderStatus": "string", \ \ "pageBean": { \ \ \ \ "orderBy": "string", \ \ \ \ "pageNumber": 0, \ \ \ \ "pageSize": 0 \ \ }, \ \ "platId": 0, \ \ "traceId": "string" }
    ${selectBatch_post_data}    To Json    ${selectBatch_post_data}
    ${case_dic}    Split String    ${selectBatch_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${selectBatch_post_data}    Replace Test Jsondata    ${selectBatch_post_data}    ${case_dic1}
    Log    ${selectBatch_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/souqTradeOrderSelectBatch.json    ${selectBatch_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${selectBatch_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

souqTradeOrderUpload_json
    [Arguments]    ${upload_data}
    [Documentation]    POST /api/shop/manager/souqTradeOrderSelectBatch.json
    ...    上传Souq订单历史记录
    ${upload_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "filePath": "string", \ \ "memberCode": 0, \ \ "shopId": 0, \ \ "traceId": "string" }
    ${upload_post_data}    To Json    ${upload_post_data}
    ${case_dic}    Split String    ${upload_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${upload_post_data}    Replace Test Jsondata    ${upload_post_data}    ${case_dic1}
    Log    ${upload_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/souqTradeOrderUpload.json    ${upload_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${upload_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

recvVoucher_json
    [Arguments]    ${recvVoucher_data}
    [Documentation]    /api/voucher/recvVoucher.json
    ...    会员优惠券领取
    ${recvVoucher_post_data}    Set Variable    { \ \ "voucherId": 0 }
    ${recvVoucher_post_data}    To Json    ${recvVoucher_post_data}
    ${case_dic}    Split String    ${recvVoucher_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${recvVoucher_post_data}    Replace Test Jsondata    ${recvVoucher_post_data}    ${case_dic1}
    Log    ${recvVoucher_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/voucher/recvVoucher.json    ${recvVoucher_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${recvVoucher_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

applyWithdraw_json
    [Arguments]    ${applyWithdraw_data}
    [Documentation]    POST /api/withdraw/applyWithdraw.json
    ...    申请提现接口
    ${applyWithdraw_post_data}    Set Variable    { \ \ "bankAccountNo": "string", \ \ "checkMethod": "0", \ \ "couponIds": [ \ \ \ \ 0 \ \ ], \ \ "details": [ \ \ \ \ { \ \ \ \ \ \ "accountNo": "string", \ \ \ \ \ \ "currencyCode": "string", \ \ \ \ \ \ "id": 0, \ \ \ \ \ \ "memberCode": 0, \ \ \ \ \ \ "platId": 0, \ \ \ \ \ \ "shopId": 0, \ \ \ \ \ \ "withdrawAmount": 0 \ \ \ \ } \ \ ], \ \ "liquidateId": 0, \ \ "memberCode": 0, \ \ "paymengPwd": "string", \ \ "settleCurrency": "string", \ \ "verifyCode": "string", \ \ "withdrawAmount": 0, \ \ "withdrawCurrency": "string" }
    ${applyWithdraw_post_data}    To Json    ${applyWithdraw_post_data}
    ${case_dic}    Split String    ${applyWithdraw_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${applyWithdraw_post_data}    Replace Test Jsondata    ${applyWithdraw_post_data}    ${case_dic1}
    Log    ${applyWithdraw_post_data}
    Redis_Set    MES_GATEWAY_SMS_15927514949_payment_verification    "123456"    300
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/withdraw/applyWithdraw.json    ${applyWithdraw_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${applyWithdraw_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

lockExchangeRate_json
    [Arguments]    ${lockExchangeRate_data}
    [Documentation]    POST /api/withdraw/lockExchangeRate.json
    ...    查询单一外币对人民币汇率接口
    ${lockExchangeRate_post_data}    Set Variable    { \ \ "currency": "string" }
    ${lockExchangeRate_post_data}    To Json    ${lockExchangeRate_post_data}
    ${case_dic}    Split String    ${lockExchangeRate_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${lockExchangeRate_post_data}    Replace Test Jsondata    ${lockExchangeRate_post_data}    ${case_dic1}
    Log    ${lockExchangeRate_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/withdraw/lockExchangeRate.json    ${lockExchangeRate_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${lockExchangeRate_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

queryExchangeRate_json
    [Documentation]    POST /api/withdraw/queryExchangeRates.json
    ...    查询所有收款外币对人民币汇率接口
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/withdraw/queryExchangeRates.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}

billInfoReport_json
    [Arguments]    ${billInfoReport_data}
    [Documentation]    POST /api/transaction/billInfoReport.json
    ...    账单明细报表excel
    ${billInfoReport_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "endTime": "string", \ \ "name": "string", \ \ "pageBean": { \ \ \ \ "orderBy": "string", \ \ \ \ "pageNumber": 0, \ \ \ \ "pageSize": 0 \ \ }, \ \ "platId": 0, \ \ "platName": "string", \ \ "startTime": "string", \ \ "traceId": "string", \ \ "transferType": "string" }
    ${billInfoReport_post_data}    To Json    ${billInfoReport_post_data}
    ${case_dic}    Split String    ${billInfoReport_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${billInfoReport_post_data}    Replace Test Jsondata    ${billInfoReport_post_data}    ${case_dic1}
    Log    ${billInfoReport_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/transaction/billInfoReport.json    ${billInfoReport_post_data}    ${headers}
    Log    返回结果：-------------------------》
    Log    ${response}

exportBillInfo_json
    [Arguments]    ${exportBillInfo_data}
    [Documentation]    POST /api/transaction/exportBillInfo.json
    ...    账单明细查询预览/下载
    ${exportBillInfo_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "downloadFlag": "string", \ \ "month": "string", \ \ "name": "string", \ \ "pageBean": { \ \ \ \ "orderBy": "string", \ \ \ \ "pageNumber": 0, \ \ \ \ "pageSize": 0 \ \ }, \ \ "platId": 0, \ \ "platName": "string", \ \ "traceId": "string" }
    ${exportBillInfo_post_data}    To Json    ${exportBillInfo_post_data}
    ${case_dic}    Split String    ${exportBillInfo_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${exportBillInfo_post_data}    Replace Test Jsondata    ${exportBillInfo_post_data}    ${case_dic1}
    Log    ${exportBillInfo_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/transaction/exportBillInfo.json    ${exportBillInfo_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${exportBillInfo_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

queryBillInfo_json
    [Arguments]    ${queryBillInfo_data}
    [Documentation]    POST /api/transaction/queryBillInfo.json
    ...    账单明细查询
    ${queryBillInfo_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "endTime": "string", \ \ "name": "string", \ \ "pageBean": { \ \ \ \ "orderBy": "string", \ \ \ \ "pageNumber": 0, \ \ \ \ "pageSize": 0 \ \ }, \ \ "platId": 0, \ \ "platName": "string", \ \ "startTime": "string", \ \ "traceId": "string", \ \ "transferType": "string" }
    ${queryBillInfo_post_data}    To Json    ${queryBillInfo_post_data}
    ${case_dic}    Split String    ${queryBillInfo_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${queryBillInfo_post_data}    Replace Test Jsondata    ${queryBillInfo_post_data}    ${case_dic1}
    Log    ${queryBillInfo_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/transaction/queryBillInfo.json    ${queryBillInfo_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${queryBillInfo_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

queryShopBySiteAndPlatName_json
    [Arguments]    ${queryShop_data}
    [Documentation]    POST /api/transaction/queryShopBySiteAndPlatName.json
    ...    通过店铺平台和站点查询店铺名称
    ${queryShop_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "name": "string", \ \ "platName": "string", \ \ "site": "string", \ \ "traceId": "string" }
    ${queryShop_post_data}    To Json    ${queryShop_post_data}
    ${case_dic}    Split String    ${queryShop_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${queryShop_post_data}    Replace Test Jsondata    ${queryShop_post_data}    ${case_dic1}
    Log    ${queryShop_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/transaction/queryShopBySiteAndPlatName.json    ${queryShop_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${queryShop_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

querySiteByPlatName_json
    [Arguments]    ${querySite_data}
    [Documentation]    POST /api/transaction/querySiteByPlatName.json
    ...    通过平台名称查询站点
    ${querySite_post_data}    Set Variable    { \ \ "platName": "string" }
    ${querySite_post_data}    To Json    ${querySite_post_data}
    ${case_dic}    Split String    ${querySite_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${querySite_post_data}    Replace Test Jsondata    ${querySite_post_data}    ${case_dic1}
    Log    ${querySite_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/transaction/querySiteByPlatName.json    ${querySite_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${querySite_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

shopWithdrawalDetails_json
    [Arguments]    ${shopWithdrawalDetails_data}
    [Documentation]    POST /api/transaction/shopWithdrawalDetails.json
    ...    店铺提现交易明细
    ${shopWithdrawalDetails_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "endTime": "string", \ \ "name": "string", \ \ "pageBean": { \ \ \ \ "orderBy": "string", \ \ \ \ "pageNumber": 0, \ \ \ \ "pageSize": 0 \ \ }, \ \ "platId": 0, \ \ "platName": "string", \ \ "startTime": "string", \ \ "status": 0, \ \ "traceId": "string", \ \ "transferType": "string" }
    ${shopWithdrawalDetails_post_data}    To Json    ${shopWithdrawalDetails_post_data}
    ${case_dic}    Split String    ${shopWithdrawalDetails_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${shopWithdrawalDetails_post_data}    Replace Test Jsondata    ${shopWithdrawalDetails_post_data}    ${case_dic1}
    Log    ${shopWithdrawalDetails_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/transaction/shopWithdrawalDetails.json    ${shopWithdrawalDetails_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${shopWithdrawalDetails_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

shopWithdrawalReport_json
    [Arguments]    ${shopWithdrawalReport_data}
    [Documentation]    POST /api/transaction/shopWithdrawalDetails.json
    ...    店铺提现交易明细
    ${shopWithdrawalReport_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "endTime": "string", \ \ "name": "string", \ \ "pageBean": { \ \ \ \ "orderBy": "string", \ \ \ \ "pageNumber": 0, \ \ \ \ "pageSize": 0 \ \ }, \ \ "platId": 0, \ \ "platName": "string", \ \ "startTime": "string", \ \ "status": 0, \ \ "traceId": "string", \ \ "transferType": "string" }
    ${shopWithdrawalReport_post_data}    To Json    ${shopWithdrawalReport_post_data}
    ${case_dic}    Split String    ${shopWithdrawalReport_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${shopWithdrawalReport_post_data}    Replace Test Jsondata    ${shopWithdrawalReport_post_data}    ${case_dic1}
    Log    ${shopWithdrawalReport_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/transaction/shopWithdrawalReport.json    ${shopWithdrawalReport_post_data}    ${headers}
    Log    返回结果：-------------------------》
    Log    ${response}

storeAccountingDetails_json
    [Arguments]    ${store_data}
    [Documentation]    POST /api/transaction/storeAccountingDetails.json
    ...    店铺入账交易明细
    ${store_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "endTime": "string", \ \ "name": "string", \ \ "pageBean": { \ \ \ \ "orderBy": "string", \ \ \ \ "pageNumber": 0, \ \ \ \ "pageSize": 0 \ \ }, \ \ "platId": 0, \ \ "platName": "string", \ \ "startTime": "string", \ \ "status": 0, \ \ "traceId": "string", \ \ "transferType": "string" }
    ${store_post_data}    To Json    ${store_post_data}
    ${case_dic}    Split String    ${store_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${store_post_data}    Replace Test Jsondata    ${store_post_data}    ${case_dic1}
    Log    ${store_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/transaction/storeAccountingDetails.json    ${store_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${store_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

storeAccountingReport_json
    [Arguments]    ${storeReport_data}
    [Documentation]    POST /api/transaction/storeAccountingReport.json
    ...    店铺入账交易报表excel
    ${storeReport_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "endTime": "string", \ \ "name": "string", \ \ "pageBean": { \ \ \ \ "orderBy": "string", \ \ \ \ "pageNumber": 0, \ \ \ \ "pageSize": 0 \ \ }, \ \ "platId": 0, \ \ "platName": "string", \ \ "startTime": "string", \ \ "status": 0, \ \ "traceId": "string", \ \ "transferType": "string" }
    ${storeReport_post_data}    To Json    ${storeReport_post_data}
    ${case_dic}    Split String    ${storeReport_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${storeReport_post_data}    Replace Test Jsondata    ${storeReport_post_data}    ${case_dic1}
    Log    ${storeReport_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/transaction/storeAccountingDetails.json    ${storeReport_post_data}    ${headers}
    Log    返回结果：-------------------------》
    Log    ${response}

doAddMemberAddress_json
    [Arguments]    ${addMember_data}
    [Documentation]    POST /api/modifyLoginPwd/doAddMemberAddress.json
    ...    补充资料
    ${addMember_post_data}    Set Variable    { \ \ "birthDate": "string", \ \ "cerExpDate": "string", \ \ "cerStartExpDate": "string", \ \ "country": "string", \ \ "individualAddr": "string", \ \ "individualEnName": "string", \ \ "memberCode": 0, \ \ "reBackImg": "string", \ \ "reFrontImg": "string", \ \ "reHandImg": "string", \ \ "rePassport": "string" }
    ${addMember_post_data}    To Json    ${addMember_post_data}
    ${case_dic}    Split String    ${addMember_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${addMember_post_data}    Replace Test Jsondata    ${addMember_post_data}    ${case_dic1}
    Log    ${addMember_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/modifyLoginPwd/doAddMemberAddress.json    ${addMember_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${addMember_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

doPersonAuthentication_json
    [Arguments]    ${personAuth_data}
    [Documentation]    POST /api/modifyLoginPwd/doPersonAuthentication.json
    ...    个人实名认证
    ${personAuth_post_data}    Set Variable    { \ \ "address": "string", \ \ "bankCardNo": "string", \ \ "birthDate": "string", \ \ "cerCode": "string", \ \ "cerExpDate": "string", \ \ "cerStartExpDate": "string", \ \ "cerType": "string", \ \ "city": "string", \ \ "country": "string", \ \ "firstName": "string", \ \ "individualEnName": "string", \ \ "lastName": "string", \ \ "memberCode": 0, \ \ "mobile": "string", \ \ "name": "string", \ \ "nationRegion": "string", \ \ "passport": "string", \ \ "personIdBackPath": "string", \ \ "personIdHoldPath": "string", \ \ "personIdPath": "string", \ \ "province": "string" }
    ${personAuth_post_data}    To Json    ${personAuth_post_data}
    ${case_dic}    Split String    ${personAuth_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${personAuth_post_data}    Replace Test Jsondata    ${personAuth_post_data}    ${case_dic1}
    Log    ${personAuth_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/modifyLoginPwd/doPersonAuthentication.json    ${personAuth_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${personAuth_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

doCompanyAuthentication_json
    [Arguments]    ${companyAuth_data}
    [Documentation]    POST /api/modifyLoginPwd/doCompanyAuthentication.json
    ...    公司实名认证
    ${companyAuth_post_data}    Set Variable    { \ \ "addr": "string", \ \ "agenAuthorizationLetter": "string", \ \ "agentBackImgOssToken": "string", \ \ "agentEnName": "string", \ \ "agentFrontImgOssToken": "string", \ \ "agentName": "string", \ \ "agentPassportImgOssToken": "string", \ \ "agentRegisterPlace": "string", \ \ "authBookImg": "string", \ \ "bussImgOssToken": "string", \ \ "certificateType": "string", \ \ "country": "string", \ \ "enName": "string", \ \ "enterpriseUpgradeFlag": "string", \ \ "govCode": "string", \ \ "legalAddress": "string", \ \ "legalBackImgOssToken": "string", \ \ "legalCity": "string", \ \ "legalEnName": "string", \ \ "legalFrontImgOssToken": "string", \ \ "legalName": "string", \ \ "legalPassportImgOssToken": "string", \ \ "legalProvince": "string", \ \ "legalRegisterPlace": "string", \ \ "memberCode": 0, \ \ "pdfOssToken": "string", \ \ "regImgOssToken": "string", \ \ "regImgUrlOssToken": "string", \ \ "zhName": "string" }
    ${companyAuth_post_data}    To Json    ${companyAuth_post_data}
    ${case_dic}    Split String    ${companyAuth_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${companyAuth_post_data}    Replace Test Jsondata    ${companyAuth_post_data}    ${case_dic1}
    Log    ${companyAuth_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/modifyLoginPwd/doCompanyAuthentication.json    ${companyAuth_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${companyAuth_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

sendSmsCode_json
    [Arguments]    ${sendSmsCode_data}
    [Documentation]    POST /api/sign/signup/sendSmsCode.json
    ...    发送手机验证码
    ${sendSmsCode_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": "string", \ \ "traceId": "string" }
    ${sendSmsCode_post_data}    To Json    ${sendSmsCode_post_data}
    ${case_dic}    Split String    ${sendSmsCode_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${sendSmsCode_post_data}    Replace Test Jsondata    ${sendSmsCode_post_data}    ${case_dic1}
    Log    ${sendSmsCode_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json
    ${response}    http_Post    %{${env}}    /mes-gateway/api/sign/signup/sendSmsCode.json    ${sendSmsCode_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${sendSmsCode_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
    Log    返回结果：-------------------------》
    Log    ${res}

receiptorRemove_json
    [Arguments]    ${receiptor_data}
    ${receiptor_post_data}    Set Variable    { \ \ "memberCode": 0, \ \ "receiptorId": 0 }
    ${receiptor_post_data}    To Json    ${receiptor_post_data}
    ${case_dic}    Split String    ${receiptor_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${receiptor_post_data}    Replace Test Jsondata    ${receiptor_post_data}    ${case_dic1}
    Log    ${receiptor_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/receiptor/remove.json    ${receiptor_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${receiptor_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
