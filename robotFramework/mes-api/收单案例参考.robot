*** Settings ***
Resource          公共资源.robot

*** Test Cases ***
s2s_token_sale
    Set Log Level    TRACE
    #连数据库获取案例测试数据    #根据环境设定，读取测试数据集
    ${env_flag}    Run Keyword If    '${env}'=='TestOne'    Set Variable    1
    ...    ELSE IF    '${env}'=='TestTwo'    Set Variable    1
    ...    ELSE IF    '${env}'=='LianTiao'    Set Variable    2
    @{query_data}    DB_Connector_Mysql    SELECT * FROM t_input_assert_info WHERE trans_type = '${TEST NAME}' AND env_flag = '${env_flag}' AND id = '55754'
    #循环执行测试案例
    : FOR    ${query_info}    IN    @{query_data}
    \    v2u0_s2s_token_sale    ${env}    ${query_info}
    ${EMPTY}

*** Keywords ***
v2u0_s2s_token_sale
    [Arguments]    ${env}    ${case_list}
    #Author Jacky
    ${pkey_file}    Set Variable    PrivateKey.pem
    ${case_dic}    Split String    ${case_list[9]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${case_dic2}    To Json    ${case_dic[1]}    #步骤2测试数据
    Run_Pre    ${env}    ${case_list}    #前置数据库处理
    #前置交易    v2u0_s2s_create_token
    ${registerUserId}    Replace_Id    ${case_dic1}    #ID生成和替换
    ${pre_data}    Sign_Data    ${case_dic1}    ${pkey_file}    #拼装加签的报文体
    ${Pre_Respones}    OpenAPI_Post    ${pre_data}    %{OpenAPI}
    ${token}    Get From Dictionary    ${Pre_Respones}    token    #数据关联
    ${status}    Get From Dictionary    ${Pre_Respones}    status
    ${result}    Run Keyword And Ignore Error    Should Be Equal As Strings    ${status}    succes    #断言
    Continue For Loop If    '${result[0]}'=='FAIL'
    #案例执行    s2s_token_sale
    Replace_Id    ${case_dic2}    ${registerUserId}    ${token}    #ID生成和替换关联参数
    ${case_data}    Sign_Data    ${case_dic2}    ${pkey_file}    #拼装加签的报文体
    ${Respones}    OpenAPI_Post    ${case_data}    %{OpenAPI}
    #响应断言
    ${expect_data}    TO JSON    ${case_list[10]}
    Run Keyword And Continue On Failure    Assert Respone    ${Respones}    ${expect_data}
    [Teardown]    Run_Post    ${env}    ${case_list}
