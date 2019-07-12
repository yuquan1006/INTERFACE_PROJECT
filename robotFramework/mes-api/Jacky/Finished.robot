*** Settings ***
Resource          ../公共资源.robot

*** Variables ***
${env}            TestOne

*** Test Cases ***
s2s_token_sale
    Set Log Level    TRACE
    log    ${TEST NAME}
    #连数据库获取案例测试数据    #根据环境设定，读取测试数据集
    ${env_flag}    Run Keyword If    '${env}'=='TestOne'    Set Variable    1
    ...    ELSE IF    '${env}'=='TestTwo'    Set Variable    1
    ...    ELSE IF    '${env}'=='LianTiao'    Set Variable    2
    @{query_data}    DB_Connector_Mysql    SELECT * FROM t_input_assert_info WHERE trans_type = '${TEST NAME}' AND env_flag = '${env_flag}' AND id = '55708'    #AND id = '55708'    #IN ('55708','55709','55710')
    #循环执行测试案例
    : FOR    ${query_info}    IN    @{query_data}
    \    v2u0_s2s_token_sale    ${env}    ${query_info}
