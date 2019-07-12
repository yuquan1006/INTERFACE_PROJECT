*** Settings ***
Suite Setup       Setup_Pre_Condition
Suite Teardown
Test Setup
Test Teardown
Resource          公共资源.robot

*** Keywords ***
Setup_Testbed_Variable
    #Address
    Set Environment Variable    TestOne    http://mapi.innertest3.ipaylinks.com
    Set Environment Variable    TestTwo    http://mapi.innertest32.ipaylinks.com
    Set Environment Variable    OpenAPI    /mapi/OpenAPI.do
    Set Environment Variable    Dev    http://101.132.113.199:8054
    #Path Info
    Set Environment Variable    ROOT_DIR    c:\\iPayAutoTest
    Set Environment Variable    LOG_DIR    c:\\iPayAutoTest\\Logs
    Set Environment Variable    CURRENT_LOG    ${OUTPUT DIR}
    Set Environment Variable    TOTAL_RESULT    %{CURRENT_LOG}\\Total_Result.txt
    Set Environment Variable    OUTPUT_DIR    ${OUTPUT DIR}
    Set Environment Variable    LIBRARY_PATH    %{ROOT_DIR}\\library
    #清除遗留结果文件
    ${rc}    Run Keyword And Ignore Error    File Should Exist    %{TOTAL_RESULT}
    Run Keyword If    '${rc[0]}'=='PASS'    Remove File    %{TOTAL_RESULT}

Setup_Pre_Condition
    ${time}    Evaluate    datetime.datetime.now()    datetime
    Set Environment Variable    TASK_START_TIME    ${time}
    Setup_Testbed_Variable
