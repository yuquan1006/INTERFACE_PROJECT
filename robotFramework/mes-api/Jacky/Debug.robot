*** Settings ***
Resource          ../公共资源.robot

*** Variables ***
${env}            TestOne

*** Test Cases ***
Query_Test
    query_查询成功    ${env}

API-Test
    [Documentation]    ${status_result} \ \ \ Post_request \ \ \ json \ \ \ ${right_link} \ \ \ data=${param} \ \ \ #发送请求至对象
    ...    \ \ #log \ \ \ ${status_result.content}
    ...
    ...    ${response_json} \ \ \ to_json \ \ \ ${status_result.content} \ \ \ #content方法格式化为json格式
    Comment    Set Log Level    TRACE
    ${headers}    Create_Dictionary    Content-Type=application/x-www-form-urlencoded
    Create Session    Test_Query    http://mapi.innertest3.ipaylinks.com    ${headers}
    ${data}    Set Variable    charset=UTF-8&merchantId=10000104397&orderId=1011902221745056742&reserved=reserved&signType=MD5&transId=20190222174532328174123951330267&transType=query&version=2.0&sign=9e878e71508bf25ea3e2b7b74ec66a16
    ${status_result}    Post Request    Test_Query    /mapi/OpenAPI.do    ${data}
    log    ${status_result.content}
    ${response_json}    To Json    ${status_result.content}
    Comment    ${res_body}    Get From Dictionary    ${response_json}    body
    Comment    ${errno}    Get From Dictionary    ${res_body}    errno

Debug
    ${trans_type}    Set Variable    s2s_token_sale
    ${env}    Set Variable    TestOne
    #读取测试数据
    @{query_data}    DB_Connector_Mysql    SELECT * FROM t_input_assert_info WHERE trans_type = '${trans_type}' AND env_flag = '1'
    : FOR    ${query_info}    IN    @{query_data}
    \    log    ${query_info}

Debug2
    ${aa}    Create Dictionary    a=1    b=2    c=3
    : FOR    ${a}    IN    @{aa}
    \    log    ${a}
    log    ${aa['a']}

aaa
    ${login_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "formFreeLogin": "string", \ \ \ \ "freeEmail": "string", \ \ \ \ "isRegisterSuccess": "string", \ \ \ \ "loginName": "string", \ \ \ \ "notifyAddress": "string", \ \ \ \ "password": "string", \ \ \ \ "platformMemberCode": "string", \ \ \ \ "platformName": "string", \ \ \ \ "randCode": "string", \ \ \ \ "sourceType": "string", \ \ \ \ "utm_source": "string" \ \ }, \ \ "traceId": "string" }
    ${login_post_data}    To Json    ${login_post_data}
    ${a}    Get From Dictionary    ${login_post_data}    clientCode
