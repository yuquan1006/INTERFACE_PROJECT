*** Settings ***
Library           String
Library           Collections
Library           OperatingSystem
Library           Process
Library           DateTime
Library           Screenshot
Library           DatabaseLibrary
Library           RequestsLibrary
Library           Library/data_center.py
Library           Library/iPay_main.py
Resource          L0_基础关键字.robot
Resource          L1_API接口.robot
Resource          L2_API测试案例.robot
Library           Library/utils.py
Library           Library/RedisLibrary.py

*** Keywords ***
Public_Setup
    [Arguments]    ${OUTPUT DIR}
    Comment    Create Log Path    ${OUTPUT DIR}
    ${rc}    Run Keyword And Ignore Error    File Should Exist    %{TOTAL_RESULT}
    Run Keyword If    '${rc[0]}'=='PASS'    Remove File    %{TOTAL_RESULT}

Public_Teardown
    ${rc}    Run Keyword And Ignore Error    File Should Exist    %{TOTAL_RESULT}
    Run Keyword If    '${rc[0]}'=='FAIL'    Create File    %{TOTAL_RESULT}    ${TEST NAME}::${TEST STATUS}::${TEST MESSAGE}
    ...    ELSE IF    '${rc[0]}'=='PASS'    Append To File    %{TOTAL_RESULT}    |${TEST NAME}::${TEST STATUS}::${TEST MESSAGE}
