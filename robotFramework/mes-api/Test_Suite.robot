*** Settings ***
Resource          公共资源.robot

*** Variables ***
${env}            Dev
${case_type}      all

*** Test Cases ***
Excel读取范例
    #Author Jacky
    @{Read Excel Data}    Read Excel Data    B_login_data.xlsx
    : FOR    ${row}    IN    @{Read Excel Data}
    \    log many    row[0]:${row[0]}    row[1]:${row[1]}    row[2]:${row[2]}
    @{Read Excel File}    Read Excel File    B_login_data.xlsx
    @{Read Excel Column}    Read Excel Column    B_login_data.xlsx    2
    @{Read Excel Sheet}    Read Excel Sheet    B_login_data.xlsx    测试数据
    @{Read Excel Data Cus}    Read Excel Data Cus    B_login_data.xlsx    3    2
    @{Read Excel Sheet Cus}    Read Excel Sheet Cus    B_login_data.xlsx    测试数据    3    2
    ${Read Excel Data Cus}    Read Excel Data Cus    B_login_data.xlsx    3    2
    log    ${Read Excel Data Cus[0][2]}

login_case
    #读取测试数据
    @{Login_ Data}    Read Excel Data    login_case.xlsx
    #post请求
    : FOR    ${test_data}    IN    @{Login_ Data}
    \    login_test    ${test_data}

signup&login
    ##前置交易    用户注册
    signup_test    signup&login.xlsx
    ##读取测试数据
    @{Login_ Data}    Read Excel Sheet    signup&login.xlsx    测试数据
    #post请求
    : FOR    ${test_data}    IN    @{Login_ Data}
    \    login_test    ${test_data}

login_return_token
    Set Log Level    TRACE
    ${Login_ Data}    Read Excel Data    login_token.xlsx
    @{Login_ Data}    Choose Case Data    ${Login_ Data}    ${case_type}
    #post请求
    : FOR    ${test_data}    IN    @{Login_ Data}
    \    login_token    ${test_data}

login_jsonData
    Set Log Level    TRACE
    #读取测试数据
    ${Login_ Data}    Read Excel Data    login_jsonData.xlsx
    #案例执行控制
    @{Login_ Data}    Choose Case Data    ${Login_ Data}    ${case_type}
    #post请求
    Log    "prepare to send request...。。。。。。。。。。。。。。。。。。。。。。。。。。。"
    : FOR    ${test_data}    IN    @{Login_ Data}
    \    login_json    ${test_data}

logout
    Set Log Level    TRACE
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    logout    ${headers}

ShopList
    Set Log Level    TRACE
    ${Shop_ Data}    Read Excel Data    ShopList_jsonData.xlsx
    #案例执行控制
    @{Shop_ Data}    Choose Case Data    ${Shop_ Data}    ${case_type}
    #post请求
    Log    "prepare to send request...。。。。。。。。。。。。。。。。。。。。。。。。。。。"
    : FOR    ${test_data}    IN    @{Shop_ Data}
    \    ShopList_json    ${test_data}

ShopDetail
    Set Log Level    TRACE
    #读取测试数据
    ${Login_ Data}    Read Excel Data    ShopDetail_jsonData.xlsx
    #案例执行控制
    @{Login_ Data}    Choose Case Data    ${Login_ Data}    ${case_type}
    #post请求
    Log    "prepare to send request...。。。。。。。。。。。。。。。。。。。。。。。。。。。"
    : FOR    ${shopId}    IN    @{Login_ Data}
    \    ShopDetail_json    ${shopId}

addShop
    Set Log Level    TRACE
    #读取数据
    ${addShop_data}    Read Excel Data    addShop_jsonData.xlsx
    @{addShop_data}    Choose Case Data    ${addShop_data}    ${case_type}
    : FOR    ${test_data}    IN    @{addShop_data}
    \    addShop_json    ${test_data}

modifyLoginPwd
    ${modify_data}    Read Excel Data    modifyLoginPwd_jsonData.xlsx
    @{modify_data}    Choose Case Data    ${modify_data}    ${case_type}
    : FOR    ${pwd_data}    IN    @{modify_data}
    \    modifyLoginPwd_json    ${pwd_data}

modifyEmail
    ${modify_data}    Read Excel Data    modifyEmail_jsonData.xlsx
    @{modify_data}    Choose Case Data    ${modify_data}    ${case_type}
    : FOR    ${email_data}    IN    @{modify_data}
    \    modifyEmail_json    ${email_data}

setPayPassword
    ${paypassword_data}    Read Excel Data    setPayPwd_jsonData.xlsx
    @{paypassword_data}    Choose Case Data    ${paypassword_data}    ${case_type}
    : FOR    ${test_data}    IN    @{paypassword_data}
    \    setPayPassword_json    ${test_data}

verifyPayPassword
    ${verify_paypassword_data}    Read Excel Data    verifyPayPwd_jsonData.xlsx
    @{verify_paypassword_data}    Choose Case Data    ${verify_paypassword_data}    ${case_type}
    : FOR    ${test_data}    IN    @{verify_paypassword_data}
    \    verifyPayPassword_json    ${test_data}

modifyPayPwd
    ${modify_data}    Read Excel Data    modifyPayPwd_jsonData.xlsx
    @{modify_data}    Choose Case Data    ${modify_data}    ${case_type}
    : FOR    ${paypwd_data}    IN    @{modify_data}
    \    modifyPayPwd_json    ${paypwd_data}

querryMemberInfo
    Set Log Level    TRACE
    querryMemberInfo_json

modifyEmailByEmail
    ${modify_data}    Read Excel Data    modifyEmailByEmail_jsonData.xlsx
    @{modify_data}    Choose Case Data    ${modify_data}    ${case_type}
    : FOR    ${test_data}    IN    @{modify_data}
    \    modifyEmailByEmail_json    ${test_data}

modifyLoginPwdByEmail
    ${modify_data}    Read Excel Data    modifyLoginPwdByEmail_jsonData.xlsx
    @{modify_data}    Choose Case Data    ${modify_data}    ${case_type}
    : FOR    ${pwd_data}    IN    @{modify_data}
    \    modifyLoginPwdByEmail_json    ${pwd_data}

modifyMobileNoByEmail
    ${modify_data}    Read Excel Data    modifyMobileNoByEmail_jsonData.xlsx
    @{modify_data}    Choose Case Data    ${modify_data}    ${case_type}
    : FOR    ${mobile_data}    IN    @{modify_data}
    \    modifyMobileNoByEmail_json    ${mobile_data}

modifyMobileNo
    ${modify_data}    Read Excel Data    modifyMobileNo_jsonData.xlsx
    @{modify_data}    Choose Case Data    ${modify_data}    ${case_type}
    : FOR    ${mobile_data}    IN    @{modify_data}
    \    modifyMobileNo_json    ${mobile_data}

modifyShopRemark
    ${modify_data}    Read Excel Data    modifyShopRemark_jsonData.xlsx
    @{modify_data}    Choose Case Data    ${modify_data}    ${case_type}
    : FOR    ${remark_data}    IN    @{modify_data}
    \    modifyShopRemark_json    ${remark_data}

queryUnShopCount
    queryUnShopCount_json

souqTradeOrderCsvTemplate
    souqTradeOrderCsvTemplate_json

souqTradeOrderExcelTemplate
    souqTradeOrderExcelTemplate_json

queryCurrencyAccountDetail
    ${query_data}    Read Excel Data    queryCurrencyAccountDetail_jsonData.xlsx
    @{query_data}    Choose Case Data    ${query_data}    ${case_type}
    : FOR    ${test_data}    IN    @{query_data}
    \    queryCurrencyAccountDetail_json    ${test_data}

queryCurrencyAmounts
    queryCurrencyAmounts_json

queryDevInfo
    ${query_data}    Read Excel Data    queryDevInfo_jsonData.xlsx
    @{query_data}    Choose Case Data    ${query_data}    ${case_type}
    : FOR    ${test_data}    IN    @{query_data}
    \    queryDevInfo_json    ${test_data}

querySaleCategory
    ${query_data}    Read Excel Data    querySaleCategory_jsonData.xlsx
    @{query_data}    Choose Case Data    ${query_data}    ${case_type}
    : FOR    ${test_data}    IN    @{query_data}
    \    querySaleCategory_json    ${test_data}

checkVerifyCode
    ${check_data}    Read Excel Data    checkVerifyCode_jsonData.xlsx
    @{check_data}    Choose Case Data    ${check_data}    ${case_type}
    : FOR    ${test_data}    IN    @{check_data}
    \    checkVerifyCode_json    ${test_data}

sendEmail
    ${sendemail_data}    Read Excel Data    sendEmail_jsonData.xlsx
    @{sendemail_data}    Choose Case Data    ${sendemail_data}    ${case_type}
    : FOR    ${test_data}    IN    @{sendemail_data}
    \    sendEmail_json    ${test_data}

getSingleCountry
    ${countrycode_data}    Read Excel Data    getSingleCountry_jsonData.xlsx
    @{countrycode_data}    Choose Case Data    ${countrycode_data}    ${case_type}
    : FOR    ${test_data}    IN    @{countrycode_data}
    \    getSingleCountry_json    ${test_data}

getAllCountry
    getAllCountry_json

queryBankCards
    ${cards_data}    Read Excel Data    queryBankCards_jsonData.xlsx
    @{cards_data}    Choose Case Data    ${cards_data}    ${case_type}
    : FOR    ${test_data}    IN    @{cards_data}
    \    queryBankCards_json    ${test_data}

getWorryFreeLoanUrl
    ${supplycode_data}    Read Excel Data    getWorryFreeLoanUrl_jsonData.xlsx
    @{supplycode_data}    Choose Case Data    ${supplycode_data}    ${case_type}
    : FOR    ${test_data}    IN    @{supplycode_data}
    \    getWorryFreeLoanUrl_json    ${test_data}

queryMemberCoupons
    ${coupons_data}    Read Excel Data    queryMemberCoupons_jsonData.xlsx
    @{coupons_data}    Choose Case Data    ${coupons_data}    ${case_type}
    : FOR    ${test_data}    IN    @{coupons_data}
    \    queryMemberCoupons_json    ${test_data}

companyAuthenticationResult
    companyAuthenticationResult_json

queryCityList
    ${citylist_data}    Read Excel Data    queryCityList_jsonData.xlsx
    @{citylist_data}    Choose Case Data    ${citylist_data}    ${case_type}
    : FOR    ${test_data}    IN    @{citylist_data}
    \    queryCityList_json    ${test_data}

queryProvinceList
    queryProvinceList_json

getAuthenticationStatus
    getAuthenticationStatus_json

addReceiptor
    ${receiptor_data}    Read Excel Data    addReceiptor_jsonData.xlsx
    @{receiptor_data}    Choose Case Data    ${receiptor_data}    ${case_type}
    : FOR    ${test_data}    IN    @{receiptor_data}
    \    addReceiptor_json    ${test_data}

receiptorLists
    ${receiptorlists_data}    Read Excel Data    receiptorLists_jsonData.xlsx
    @{receiptorlists_data}    Choose Case Data    ${receiptorlists_data}    ${case_type}
    : FOR    ${test_data}    IN    @{receiptorlists_data}
    \    receiptorLists_json    ${test_data}

index_balance
    index_balance_json

index_baseInfo
    index_baseInfo_json

index_recentTradeCash
    ${recentTradeCash_Data}    Read Excel Data    recentTradeCash_jsonData.xlsx
    @{recentTradeCash_Data}    Choose Case Data    ${recentTradeCash_Data}    ${case_type}
    : FOR    ${test_data}    IN    @{recentTradeCash_Data}
    \    index_recentTradeCash_json    ${test_data}

index_recentWithdraw
    ${recentWithdraw_ Data}    Read Excel Data    recentWithdraw_jsonData.xlsx
    @{recentWithdraw_ Data}    Choose Case Data    ${recentWithdraw_ Data}    ${case_type}
    : FOR    ${test_data}    IN    @{recentWithdraw_ Data}
    \    index_recentWithdraw_json    ${test_data}

index_inAccount
    index_inAccount_json

index_withdew
    index_withdew_json

index_exchangeRate
    ${exchangeRate_ Data}    Read Excel Data    exchangeRate_jsonData.xlsx
    @{exchangeRate_ Data}    Choose Case Data    ${exchangeRate_ Data}    ${case_type}
    : FOR    ${test_data}    IN    @{exchangeRate_ Data}
    \    exchangeRate_json    ${test_data}

baseInfo
    baseInfo_json

addBankCard
    ${bankcard_data}    Read Excel Data    addBankCard_jsonData.xlsx
    @{bankcard_data}    Choose Case Data    ${bankcard_data}    ${case_type}
    : FOR    ${test_data}    IN    @{bankcard_data}
    \    addBankCard_json    ${test_data}

bankCardDetail
    ${detail_data}    Read Excel Data    bankCardDetail_jsonData.xlsx
    @{detail_data}    Choose Case Data    ${detail_data}    ${case_type}
    : FOR    ${test_data}    IN    @{detail_data}
    \    bankCardDetail_json    ${test_data}

bankCardList
    bankCardList_json

removeCard
    ${remove_data}    Read Excel Data    removeCard_jsonData.xlsx
    @{remove_data}    Choose Case Data    ${remove_data}    ${case_type}
    : FOR    ${test_data}    IN    @{remove_data}
    \    removeCard_json    ${test_data}

updateCurrency
    ${update_data}    Read Excel Data    updateCurrency_jsonData.xlsx
    @{update_data}    Choose Case Data    ${update_data}    ${case_type}
    : FOR    ${test_data}    IN    @{update_data}
    \    updateCurrency_json    ${test_data}

queryPlatAuthResult
    ${platauth_data}    Read Excel Data    queryPlatAuthResult_jsonData.xlsx
    @{platauth_data}    Choose Case Data    ${platauth_data}    ${case_type}
    : FOR    ${test_data}    IN    @{platauth_data}
    \    queryPlatAuthResult_json    ${test_data}

queryPlatAuthUrl
    ${authurl_data}    Read Excel Data    queryPlatAuthUrl_jsonData.xlsx
    @{authurl_data}    Choose Case Data    ${authurl_data}    ${case_type}
    : FOR    ${test_data}    IN    @{authurl_data}
    \    queryPlatAuthUrl_json    ${test_data}

queryPlatSiteList
    ${sitelist_data}    Read Excel Data    queryPlatSiteList_jsonData.xlsx
    @{sitelist_data}    Choose Case Data    ${sitelist_data}    ${case_type}
    : FOR    ${test_data}    IN    @{sitelist_data}
    \    queryPlatSiteList_json    ${test_data}

checkEmail
    ${checkEmail_data}    Read Excel Data    checkEmail_jsonData.xlsx
    @{checkEmail_data}    Choose Case Data    ${checkEmail_data}    ${case_type}
    : FOR    ${test_data}    IN    @{checkEmail_data}
    \    checkEmail_json    ${test_data}

checkMobile
    ${checkMobile_data}    Read Excel Data    checkMobile_jsonData.xlsx
    @{checkMobile_data}    Choose Case Data    ${checkMobile_data}    ${case_type}
    : FOR    ${test_data}    IN    @{checkMobile_data}
    \    checkMobile_json    ${test_data}

activeEmail
    ${activeEmail_data}    Read Excel Data    activeEmail_jsonData.xlsx
    @{activeEmail_data}    Choose Case Data    ${activeEmail_data}    ${case_type}
    : FOR    ${test_data}    IN    @{activeEmail_data}
    \    activeEmail_json    ${test_data}

signupEmail
    ${signupEmail_data}    Read Excel Data    signupEmail_jsonData.xlsx
    @{signupEmail_data}    Choose Case Data    ${signupEmail_data}    ${case_type}
    : FOR    ${test_data}    IN    @{signupEmail_data}
    \    signupEmail_json    ${test_data}

firstLoginActive
    ${firstActive_data}    Read Excel Data    firstLoginActive_jsonData.xlsx
    @{firstActive_data}    Choose Case Data    ${firstActive_data}    ${case_type}
    : FOR    ${test_data}    IN    @{firstActive_data}
    \    firstLoginActive_json    ${test_data}

firstTimeLogin
    ${firstTimeLogin_data}    Read Excel Data    firstTimeLogin_jsonData.xlsx
    @{firstTimeLogin_data}    Choose Case Data    ${firstTimeLogin_data}    ${case_type}
    : FOR    ${test_data}    IN    @{firstTimeLogin_data}
    \    firstTimeLogin_json    ${test_data}

improveInfo
    ${improveInfo_data}    Read Excel Data    improveInfo_jsonData.xlsx
    @{improveInfo_data}    Choose Case Data    ${improveInfo_data}    ${case_type}
    : FOR    ${test_data}    IN    @{improveInfo_data}
    \    improveInfo_json    ${test_data}

signupMobile
    ${signupMobile_data}    Read Excel Data    signupMobile_jsonData.xlsx
    @{signupMobile_data}    Choose Case Data    ${signupMobile_data}    ${case_type}
    : FOR    ${test_data}    IN    @{signupMobile_data}
    \    signupMobile_json    ${test_data}

changeAuth
    [Documentation]    POST /api/shop/manager/changeAuth.json
    ...    变更授权接口（亚马逊）
    ${changeAuth_data}    Read Excel Data    changeAuth_jsonData.xlsx
    @{changeAuth_data}    Choose Case Data    ${changeAuth_data}    ${case_type}
    : FOR    ${test_data}    IN    @{changeAuth_data}
    \    changeAuth_json    ${test_data}

completedBanding
    [Documentation]    POST /api/shop/manager/completedBanding.json
    ...    完善资料接口（亚马逊）
    ${completedBanding_data}    Read Excel Data    completedBanding_jsonData.xlsx
    @{completedBanding_data}    Choose Case Data    ${completedBanding_data}    ${case_type}
    : FOR    ${test_data}    IN    @{completedBanding_data}
    \    completedBanding_json    ${test_data}

reAuthEbay
    [Documentation]    POST /api/shop/manager/reAuthEbay.json
    ...    重新授权接口（eBay）
    ${reAuthEbay_data}    Read Excel Data    reAuthEbay_jsonData.xlsx
    @{reAuthEbay_data}    Choose Case Data    ${reAuthEbay_data}    ${case_type}
    : FOR    ${test_data}    IN    @{reAuthEbay_data}
    \    reAuthEbay_json    ${test_data}

reSubmitShop
    [Documentation]    POST /api/shop/manager/reSubmitShop.json
    ...    重新提交接口
    ${reSubmitShop_data}    Read Excel Data    reSubmitShop_jsonData.xlsx
    @{reSubmitShop_data}    Choose Case Data    ${reSubmitShop_data}    ${case_type}
    : FOR    ${test_data}    IN    @{reSubmitShop_data}
    \    reSubmitShop_json    ${test_data}

souqTradeOrderFailureResult
    [Documentation]    POST /api/shop/manager/souqTradeOrderFailureResult.json
    ...    下载Souq订单记录文件
    ${failureResult_data}    Read Excel Data    souqTradeOrderFailureResult_jsonData.xlsx
    @{failureResult_data}    Choose Case Data    ${failureResult_data}    ${case_type}
    : FOR    ${test_data}    IN    @{failureResult_data}
    \    souqTradeOrderFailureResult_json    ${test_data}

souqTradeOrderSelectBatch
    [Documentation]    POST /api/shop/manager/souqTradeOrderSelectBatch.json
    ...    上传Souq订单历史记录
    ${selectBatch_data}    Read Excel Data    souqTradeOrderSelectBatch_jsonData.xlsx
    @{selectBatch_data}    Choose Case Data    ${selectBatch_data}    ${case_type}
    : FOR    ${test_data}    IN    @{selectBatch_data}
    \    souqTradeOrderSelectBatch_json    ${test_data}

souqTradeOrderUpload
    [Documentation]    POST /api/shop/manager/souqTradeOrderUpload.json
    ...    上传店铺订单数据接口
    ${upload_data}    Read Excel Data    souqTradeOrderUpload_jsonData.xlsx
    @{upload_data}    Choose Case Data    ${upload_data}    ${case_type}
    : FOR    ${test_data}    IN    @{upload_data}
    \    souqTradeOrderUpload_json    ${test_data}

recvVoucher
    [Documentation]    POST /api/voucher/recvVoucher.json
    ...    会员优惠券领取
    ${recvVoucher_data}    Read Excel Data    recvVoucher_jsonData.xlsx
    @{recvVoucher_data}    Choose Case Data    ${recvVoucher_data}    ${case_type}
    : FOR    ${test_data}    IN    @{recvVoucher_data}
    \    recvVoucher_json    ${test_data}
    ${EMPTY}

applyWithdraw
    [Documentation]    POST /api/withdraw/applyWithdraw.json
    ...    申请提现接口
    ${applyWithdraw_data}    Read Excel Data    applyWithdraw_jsonData.xlsx
    @{applyWithdraw_data}    Choose Case Data    ${applyWithdraw_data}    ${case_type}
    : FOR    ${test_data}    IN    @{applyWithdraw_data}
    \    applyWithdraw_json    ${test_data}

lockExchangeRate
    [Documentation]    POST /api/withdraw/lockExchangeRate.json
    ...    查询单一外币对人民币汇率接口
    ${lockExchangeRate_data}    Read Excel Data    lockExchangeRate_jsonData.xlsx
    @{lockExchangeRate_data}    Choose Case Data    ${lockExchangeRate_data}    ${case_type}
    : FOR    ${test_data}    IN    @{lockExchangeRate_data}
    \    lockExchangeRate_json    ${test_data}

queryExchangeRate
    [Documentation]    POST /api/withdraw/queryExchangeRates.json
    ...    查询所有收款外币对人民币汇率接口
    queryExchangeRate_json

billInfoReport
    [Documentation]    POST /api/transaction/billInfoReport.json
    ...    账单明细报表excel
    ${billInfoReport_data}    Read Excel Data    billInfoReport_jsonData.xlsx
    @{billInfoReport_data}    Choose Case Data    ${billInfoReport_data}    ${case_type}
    : FOR    ${test_data}    IN    @{billInfoReport_data}
    \    billInfoReport_json    ${test_data}

exportBillInfo
    [Documentation]    POST /api/transaction/exportBillInfo.json
    ...    账单明细查询预览/下载
    ${exportBillInfo_data}    Read Excel Data    exportBillInfo_jsonData.xlsx
    @{exportBillInfo_data}    Choose Case Data    ${exportBillInfo_data}    ${case_type}
    : FOR    ${test_data}    IN    @{exportBillInfo_data}
    \    exportBillInfo_json    ${test_data}

queryBillInfo
    [Documentation]    POST /api/transaction/queryBillInfo.json
    ...    账单明细查询
    ${queryBillInfo_data}    Read Excel Data    queryBillInfo_jsonData.xlsx
    @{queryBillInfo_data}    Choose Case Data    ${queryBillInfo_data}    ${case_type}
    : FOR    ${test_data}    IN    @{queryBillInfo_data}
    \    queryBillInfo_json    ${test_data}

queryShopBySiteAndPlatName
    [Documentation]    POST /api/transaction/queryShopBySiteAndPlatName.json
    ...    通过店铺平台和站点查询店铺名称
    ${queryShop_data}    Read Excel Data    queryShopBySiteAndPlatName_jsonData.xlsx
    @{queryShop_data}    Choose Case Data    ${queryShop_data}    ${case_type}
    : FOR    ${test_data}    IN    @{queryShop_data}
    \    queryShopBySiteAndPlatName_json    ${test_data}

querySiteByPlatName
    [Documentation]    POST /api/transaction/querySiteByPlatName.json
    ...    通过平台名称查询站点
    ${querySite_data}    Read Excel Data    querySiteByPlatName_jsonData.xlsx
    @{querySite_data}    Choose Case Data    ${querySite_data}    ${case_type}
    : FOR    ${test_data}    IN    @{querySite_data}
    \    querySiteByPlatName_json    ${test_data}

shopWithdrawDetail
    [Documentation]    POST /api/transaction/shopWithdrawalDetails.json
    ...    店铺提现交易明细
    ${shopWithdrawalDetails_data}    Read Excel Data    shopWithdrawDetails_jsonData.xlsx
    @{shopWithdrawalDetails_data}    Choose Case Data    ${shopWithdrawalDetails_data}    ${case_type}
    : FOR    ${test_data}    IN    @{shopWithdrawalDetails_data}
    \    shopWithdrawalDetails_json    ${test_data}

shopWithdrawReport
    [Documentation]    POST /api/transaction/shopWithdrawalReport.json
    ...    店铺提现交易报表excel
    ${shopWithdrawalReport_data}    Read Excel Data    shopWithdrawReport_jsonData.xlsx
    @{shopWithdrawalReport_data}    Choose Case Data    ${shopWithdrawalReport_data}    ${case_type}
    : FOR    ${test_data}    IN    @{shopWithdrawalReport_data}
    \    shopWithdrawalReport_json    ${test_data}

storeAccountingDetails
    [Documentation]    POST /api/transaction/storeAccountingDetails.json
    ...    店铺入账交易明细
    ${store_data}    Read Excel Data    storeAccountingDetails_jsonData.xlsx
    @{store_data}    Choose Case Data    ${store_data}    ${case_type}
    : FOR    ${test_data}    IN    @{store_data}
    \    storeAccountingDetails_json    ${test_data}

storeAccountingReport
    [Documentation]    POST /api/transaction/storeAccountingReport.json
    ...    店铺入账交易报表excel
    ${storeReport_data}    Read Excel Data    storeAccountingReport_jsonData.xlsx
    @{storeReport_data}    Choose Case Data    ${storeReport_data}    ${case_type}
    : FOR    ${test_data}    IN    @{storeReport_data}
    \    storeAccountingReport_json    ${test_data}

doAddMemberAddress
    [Documentation]    POST /api/modifyLoginPwd/doAddMemberAddress.json
    ...    补充资料
    ${addMember_data}    Read Excel Data    doAddMemberAddress_jsonData.xlsx
    @{addMember_data}    Choose Case Data    ${addMember_data}    ${case_type}
    : FOR    ${test_data}    IN    @{addMember_data}
    \    doAddMemberAddress_json    ${test_data}

doPersonAuthentication
    [Documentation]    POST /api/modifyLoginPwd/doPersonAuthentication.json
    ...    个人实名认证
    ${personAuth_data}    Read Excel Data    doPersonAuthentication_jsonData.xlsx
    @{personAuth_data}    Choose Case Data    ${personAuth_data}    ${case_type}
    : FOR    ${test_data}    IN    @{personAuth_data}
    \    doPersonAuthentication_json    ${test_data}

doCompanyAuthentication
    [Documentation]    POST /api/modifyLoginPwd/doCompanyAuthentication.json
    ...    公司实名认证
    ${companyAuth_data}    Read Excel Data    doCompanyAuthentication_jsonData.xlsx
    @{companyAuth_data}    Choose Case Data    ${companyAuth_data}    ${case_type}
    : FOR    ${test_data}    IN    @{companyAuth_data}
    \    doCompanyAuthentication_json    ${test_data}

sendSmsCode
    [Documentation]    POST /api/sign/signup/sendSmsCode.json
    ...    发送手机验证码
    ${sendSmsCode_data}    Read Excel Data    sendSmsCode_jsonData.xlsx
    @{sendSmsCode_data}    Choose Case Data    ${sendSmsCode_data}    ${case_type}
    : FOR    ${test_data}    IN    @{sendSmsCode_data}
    \    sendSmsCode_json    ${test_data}

receiptorRemove
    ${receiptorRemove_data}    Read Excel Data    receiptorRemove_jsonData.xlsx
    @{receiptorRemove_data}    Choose Case Data    ${receiptorRemove_data}    ${case_type}
    : FOR    ${test_data}    IN    @{receiptorRemove_data}
    \    receiptorRemove_json    ${test_data}

*** Keywords ***
login_test
    [Arguments]    ${test_data}
    ${login_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "formFreeLogin": "string", \ \ \ \ "freeEmail": "string", \ \ \ \ "isRegisterSuccess": "string", \ \ \ \ "loginName": "string", \ \ \ \ "notifyAddress": "string", \ \ \ \ "password": "string", \ \ \ \ "platformMemberCode": "string", \ \ \ \ "platformName": "string", \ \ \ \ "randCode": "string", \ \ \ \ "sourceType": "string", \ \ \ \ "utm_source": "string" \ \ }, \ \ "traceId": "string" }    #定义报文模板    responseMsg
    ${login_post_data}    To Json    ${login_post_data}
    #根据测试数据替换报文内容    ${res}
    ${requestObj}    Get From Dictionary    ${login_post_data}    requestObj
    Set To Dictionary    ${requestObj}    loginName=${test_data[0]}    password=${test_data[1]}
    Set To Dictionary    ${login_post_data}    requestObj=${requestObj}
    log    ${login_post_data}
    #发送登录请求    #172.17.1.131
    ${res}    http_Post    %{Dev}    /mes-gateway/api/security/login.json    ${login_post_data}
    #断言
    ${responseMsg}    Get From Dictionary    ${res}    responseMsg
    Run Keyword And Continue On Failure    Should Be Equal As Strings    ${responseMsg}    ${test_data[2]}

signup_test
    [Arguments]    ${data_file}
    #读取数据
    ${pre_data}    Read Excel Sheet    ${data_file}    前置
    ${signup_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "clientCode": "string", \ \ \ \ "country": "string", \ \ \ \ "email": "jackyroc@111.com", \ \ \ \ "inviteCode": "string", \ \ \ \ "ip": "string", \ \ \ \ "loginPhone": "13311223344", \ \ \ \ "moblileVerifCode": "123123", \ \ \ \ "payPwd": "112233aa", \ \ \ \ "payPwdRepeat": "112233aa", \ \ \ \ "pwd": "12345678A", \ \ \ \ "pwdRepeat": "12345678A", \ \ \ \ "recommendCode": 0, \ \ \ \ "registerSource": "string", \ \ \ \ "signType": 0, \ \ \ \ "sourceType": "string", \ \ \ \ "terminalType": "string", \ \ \ \ "traceId": "string" \ \ }, \ \ "traceId": "string" }    #定义报文模板
    ${signup_post_data}    To Json    ${signup_post_data}
    #根据测试数据替换报文内容
    ${requestObj}    Get From Dictionary    ${signup_post_data}    requestObj
    Set To Dictionary    ${requestObj}    mail=${pre_data[0][0]}    pwd=${pre_data[0][1]}    pwdRepeat=${pre_data[0][1]}    loginPhone=${pre_data[0][2]}    payPwd=${pre_data[0][3]}
    ...    payPwdRepeat=${pre_data[0][3]}
    Set To Dictionary    ${signup_post_data}    requestObj=${requestObj}
    log    ${signup_post_data}
    #发送登录请求    #172.17.1.131
    ${res}    http_Post    http://101.132.113.199:8054    /mes-gateway/api/sign/signup/improveInfo.json    ${signup_post_data}
    ${responseMsg}    Get From Dictionary    ${res}    responseMsg
    Run Keyword And Continue On Failure    Should Be Equal As Strings    ${responseMsg}    成功响应

login_json
    [Arguments]    ${test_data}
    ${login_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "requestObj": { \ \ \ \ "loginName": "string", \ \ \ \ "password": "string" \ \ }, \ \ "traceId": "string" }    #定义报文模板    responseMsg
    ${login_post_data}    To Json    ${login_post_data}
    Log    ${test_data}
    ${case_dic}    Split String    ${test_data[4]}    &    #分离出测试数据
    Log    ${case_dic}
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

logout
    [Arguments]    ${headers}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/logout.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    log    返回报文：
    log    ${res}

ShopList_json
    [Arguments]    ${test_data}
    ${shoplist_post_data}    Set Variable    { \ \ "nameLike": "string" }    #定义报文模板
    ${shoplist_post_data}    To Json    ${shoplist_post_data}
    ${case_dic}    Split String    ${test_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    #根据测试数据替换报文内容    ${res}
    ${shoplist_post_data}    Replace Test Jsondata    ${shoplist_post_data}    ${case_dic1}
    log    ${shoplist_post_data}
    #发送查询请求    #172.17.1.131
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/query/queryShopList.json    ${shoplist_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    log    返回报文：
    log    ${res}
    #断言
    ${expect_data}    TO JSON    ${test_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

ShopDetail_json
    [Arguments]    ${shopId}
    ${shopdetail_post_id}    Set Variable    { \ \ "shopId": "string" }    #定义报文模板
    ${shopdetail_post_id}    To Json    ${shopdetail_post_id}
    ${case_dic}    Split String    ${shopId[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    #根据测试数据替换报文内容    ${res}
    ${shopdetail_post_id}    Replace Test Jsondata    ${shopdetail_post_id}    ${case_dic1}
    #发送查询请求    #172.17.1.131
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/query/queryShopDetail.json    ${shopdetail_post_id}    ${headers}
    ${res}    TO JSON    ${response.content}
    log    返回报文：
    log    ${res}

addShop_json
    [Arguments]    ${test_data}
    ${addshop_post_data}    Set Variable    { \ \ "address": "string", \ \ "authData": "string", \ \ "bankAccountName": "string", \ \ "businessCode": "string", \ \ "businessLicenseUrl": "string", \ \ "cerBackUrl": "string", \ \ "cerCode": "string", \ \ "cerFrontUrl": "string", \ \ "eBaySessionID": "string", \ \ "id": 0, \ \ "memberCode": 0, \ \ "mwsAuthToken": "string", \ \ "name": "string", \ \ "needShopAuth": 0, \ \ "payPal": "string", \ \ "platDeveloperId": "string", \ \ "platId": 0, \ \ "platName": "string", \ \ "salesCategory": "string", \ \ "sellerId": "string", \ \ "shopIentity": "string", \ \ "status": 0, \ \ "unShopFlag": 0, \ \ "wish": "string" }    #创建模板
    ${addshop_post_data}    To Json    ${addshop_post_data}
    ${case_dict}    Split String    ${test_data[4]}    &    #分割数据
    ${case_dict_1}    To Json    ${case_dict[0]}
    ${addshop_post_data}    Replace Test Jsondata    ${addshop_post_data}    ${case_dict_1}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${res}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/addShop.json    ${addshop_post_data}    ${headers}
    ${res}    TO JSON    ${res.content}
    #断言
    ${expect_data}    TO JSON    ${test_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

modifyLoginPwd_json
    [Arguments]    ${pwd_data}
    ${modify_post_data}    Set Variable    { \ \ "confirmedNewPassword": "string", \ \ "newPassword": "string", \ \ "oldPassword": "string" }    #定义报文模板
    ${modify_post_data}    To Json    ${modify_post_data}
    ${case_dic}    Split String    ${pwd_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${modify_post_data}    Replace Test Jsondata    ${modify_post_data}    ${case_dic1}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/modifyLoginPwd.json    ${modify_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${pwd_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

modifyEmail_json
    [Arguments]    ${email_data}
    ${modify_post_data}    Set Variable    { \ \ "confirmedNewEmail": "string", \ \ "newEmail": "string" }    #定义报文模板
    ${modify_post_data}    To Json    ${modify_post_data}
    ${case_dic}    Split String    ${email_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${modify_post_data}    Replace Test Jsondata    ${modify_post_data}    ${case_dic1}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/modifyEmail.json    ${modify_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${email_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

setPayPassword_json
    [Arguments]    ${test_data}
    ${set_post_data}    Set Variable    { \ \ "confirmedNewPassword": "string", \ \ "newPassword": "string" }    #定义报文模板
    ${set_post_data}    To Json    ${set_post_data}
    ${case_dic}    Split String    ${test_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${set_post_data}    Replace Test Jsondata    ${set_post_data}    ${case_dic1}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/setPayPwd.json    ${set_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${test_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

verifyPayPassword_json
    [Arguments]    ${test_data}
    ${verify_post_data}    Set Variable    { \ \ "payPassword": "string" }
    ${verify_post_data}    To Json    ${verify_post_data}
    ${case_dic}    Split String    ${test_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${verify_post_data}    Replace Test Jsondata    ${verify_post_data}    ${case_dic1}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/verifyPayPassword.json    ${verify_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${test_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

modifyPayPwd_json
    [Arguments]    ${paypwd_data}
    ${modify_post_data}    Set Variable    { \ \ "confirmedNewPassword": "string", \ \ "newPassword": "string", \ \ "oldPassword": "string" }    #定义报文模板
    ${modify_post_data}    To Json    ${modify_post_data}
    ${case_dic}    Split String    ${paypwd_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${modify_post_data}    Replace Test Jsondata    ${modify_post_data}    ${case_dic1}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/modifyPayPwd.json    ${modify_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${paypwd_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

querryMemberInfo_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/querryMenberInfo.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}

modifyEmailByEmail_json
    [Arguments]    ${byemail_data}
    ${modify_post_data}    Set Variable    { \ \ "confirmedNewEmail": "string", \ \ "newEmail": "string", \ \ "token": "string" }    #定义报文模板
    ${modify_post_data}    To Json    ${modify_post_data}
    ${case_dic}    Split String    ${byemail_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${modify_post_data}    Replace Test Jsondata    ${modify_post_data}    ${case_dic1}
    Redis_Set    123    20000001486    43200
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/modifyEmailByEmail.json    ${modify_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${byemail_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

modifyLoginPwdByEmail_json
    [Arguments]    ${pwd_data}
    ${modify_post_data}    Set Variable    { \ \ "confirmedNewPassword": "string", \ \ "newPassword": "string", \ \ "oldPassword": "string", \ \ "token": "string" }    #定义报文模板
    ${modify_post_data}    To Json    ${modify_post_data}
    ${case_dic}    Split String    ${pwd_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${modify_post_data}    Replace Test Jsondata    ${modify_post_data}    ${case_dic1}
    Redis_Set    123    20000001486    43200
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/modifyLoginPwdByEmail.json    ${modify_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${pwd_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

modifyMobileNoByEmail_json
    [Arguments]    ${mobile_data}
    ${modify_post_data}    Set Variable    { \ \ "newMobileNo": "string", \ \ "token": "string" }
    ${modify_post_data}    To Json    ${modify_post_data}
    ${case_dic}    Split String    ${mobile_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${modify_post_data}    Replace Test Jsondata    ${modify_post_data}    ${case_dic1}
    Redis_Set    123    20000001486    43200
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/modifyMobileNoByEmail.json    ${modify_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${mobile_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

modifyMobileNo_json
    [Arguments]    ${mobile_data}
    ${modify_post_data}    Set Variable    { \ \ "codeType": "string", \ \ "newMobileNo": "string", \ \ "verifyCode": "string" }
    ${modify_post_data}    To Json    ${modify_post_data}
    ${case_dic}    Split String    ${mobile_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${modify_post_data}    Replace Test Jsondata    ${modify_post_data}    ${case_dic1}
    Redis_Set    MES_GATEWAY_SMS_15927514949_modify_mobile    "123456"    300
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/security/modifyMobileNo.json    ${modify_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${mobile_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

modifyShopRemark_json
    [Arguments]    ${remark_data}
    ${modify_post_data}    Set Variable    { \ \ "clientCode": "string", \ \ "memberCode": 0, \ \ "remark": "string", \ \ "shopId": 0, \ \ "traceId": "string" }
    ${modify_post_data}    To Json    ${modify_post_data}
    ${case_dic}    Split String    ${remark_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${modify_post_data}    Replace Test Jsondata    ${modify_post_data}    ${case_dic1}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/modifyShopRemark.json    ${modify_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    #断言
    ${expect_data}    TO JSON    ${remark_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

queryUnShopCount_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/queryUnShopCount.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    ${responseObj}    Get From Dictionary    ${res}    responseObj
    log    返回店铺数量：
    log    ${responseObj}

souqTradeOrderCsvTemplate_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${res}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/souqTradeOrderCsvTemplate.htm    \    ${headers}
    ${responseBody}    Set Variable    ${res.content}
    Create Binary File    template.csv    ${responseBody}
    Log    分界线————————————————————
    #断言
    File Should Exist    template.csv

souqTradeOrderExcelTemplate_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/manager/souqTradeOrderExcelTemplate.htm    \    ${headers}
    ${responseBody}    Set Variable    ${response.content}
    Create Binary File    template.xls    ${responseBody}
    Log    分界线————————————————————
    #断言
    File Should Exist    template.xls

Page_contain
    [Arguments]    ${test_data}
    ${result}    Set Variable    False
    ${temp_data}    Set Variable    Download
    Run_keyword_if    ${temp_data}in${test_data}    ${result}    Set Variable    True
    [Return]    ${result}

queryCurrencyAccountDetail_json
    [Arguments]    ${query_data}
    ${query_post_data}    Set Variable    { \ \ "currency": "string" }    #定义报文模板
    ${query_post_data}    To Json    ${query_post_data}
    ${case_dic}    Split String    ${query_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${query_post_data}    Replace Test Jsondata    ${query_post_data}    ${case_dic1}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/balance/queryCurrencyAccountDetail.json    ${query_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${query_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

queryCurrencyAmounts_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/balance/queryCurrencyAmounts.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}

queryDevInfo_json
    [Arguments]    ${devinfo_data}
    ${devinfo_post_data}    Set Variable    { \ \ "platId": 0 }
    ${devinfo_post_data}    To Json    ${devinfo_post_data}
    ${case_dic}    Split String    ${devinfo_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${devinfo_post_data}    Replace Test Jsondata    ${devinfo_post_data}    ${case_dic1}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/plat/queryDevInfo.json    ${devinfo_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${devinfo_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

querySaleCategory_json
    [Arguments]    ${category_data}
    ${category_post_data}    Set Variable    { \ \ "platId": 0 }
    ${category_post_data}    To Json    ${category_post_data}
    ${case_dic}    Split String    ${category_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${category_post_data}    Replace Test Jsondata    ${category_post_data}    ${case_dic1}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/shop/plat/querySaleCategory.json    ${category_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${category_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

checkVerifyCode_json
    [Arguments]    ${check_data}
    ${check_post_data}    Set Variable    { \ \ "codeType": "string", \ \ "mobileNo": "string", \ \ "verifyCode": "string" }
    ${check_post_data}    To Json    ${check_post_data}
    ${case_dic}    Split String    ${check_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${check_post_data}    Replace Test Jsondata    ${check_post_data}    ${case_dic1}
    Log    ${check_post_data}
    ${type}    Get From Dictionary    ${check_post_data}    codeType
    ${codeType}    catenate    SEPARATOR=    MES_GATEWAY_SMS_15927514949_    ${type}    #连接字符串keyword是catenate，"SEPARATOR="是参数，消除字符串连接后的中间空格
    LOG    ${codeType}
    Redis_set    ${codeType}    "123456"    300
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/common/checkVerifyCode.json    ${check_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${check_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

sendEmail_json
    [Arguments]    ${sendemail_data}
    ${sendemail_post_data}    Set Variable    { \ \ "email": "string", \ \ "emailType": "string" }
    ${sendemail_post_data}    To Json    ${sendemail_post_data}
    ${case_dic}    Split String    ${sendemail_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${sendemail_post_data}    Replace Test Jsondata    ${sendemail_post_data}    ${case_dic1}
    Log    ${sendemail_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/common/sendEmail.json    ${sendemail_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${sendemail_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

sendVerifyCode_json
    [Arguments]    ${sendmobile_data}
    ${sendmobile_post_data}    Set Variable    { \ \ "codeType": "string", \ \ "mobileNo": "string" }
    ${sendmobile_post_data}    To Json    ${sendmobile_post_data}
    ${case_dic}    Split String    ${sendmobile_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${sendmobile_post_data}    Replace Test Jsondata    ${sendmobile_post_data}    ${case_dic1}
    Log    ${sendmobile_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/common/sendVerifyCode.json    ${sendmobile_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${sendmobile_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

getAllCountry_json
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/country/getAll.json    \    ${headers}
    ${res}    TO JSON    ${response.content}
    ${countryList}    Get From Dictionary    ${res}    responseList
    Log    ${countryList}

getSingleCountry_json
    [Arguments]    ${countrycode_data}
    ${countrycode_post_data}    Set Variable    { \ \ "code": "string" }
    ${countrycode_post_data}    To Json    ${countrycode_post_data}
    ${case_dic}    Split String    ${countrycode_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${countrycode_post_data}    Replace Test Jsondata    ${countrycode_post_data}    ${case_dic1}
    Log    ${countrycode_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/country/getSingle.json    ${countrycode_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${countrycode_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

queryBankCards_json
    [Arguments]    ${querycards_data}
    ${querycards_post_data}    Set Variable    { \ \ "currency": "string" }
    ${querycards_post_data}    To Json    ${querycards_post_data}
    ${case_dic}    Split String    ${querycards_data[4]}    &    #分离出测试数据
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${querycards_post_data}    Replace Test Jsondata    ${querycards_post_data}    ${case_dic1}
    Log    ${querycards_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/liquidate/queryBankCards.json    ${querycards_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${querycards_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

getWorryFreeLoanUrl_json
    [Arguments]    ${supplycode_data}
    ${supplycode_post_data}    Set Variable    { \ \ "supplyCode": "string" }
    ${supplycode_post_data}    To Json    ${supplycode_post_data}
    ${case_dic}    Split String    ${supplycode_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${supplycode_post_data}    Replace Test Jsondata    ${supplycode_post_data}    ${case_dic1}
    Log    ${supplycode_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/worryFreeLoan/getWorryFreeLoanUrl.json    ${supplycode_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${supplycode_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}

queryMemberCoupons_json
    [Arguments]    ${coupons_data}
    ${coupons_post_data}    Set Variable    { \ \ "status": "0" }
    ${coupons_post_data}    To Json    ${coupons_post_data}
    ${case_dic}    Split String    ${coupons_data[4]}    &
    ${case_dic1}    To Json    ${case_dic[0]}    #步骤1测试数据
    ${coupons_post_data}    Replace Test Jsondata    ${coupons_post_data}    ${case_dic1}
    Log    ${coupons_post_data}
    ${headers}    Create_Dictionary    Content-Type=application/json    AUTH-TOKEN=${token}
    ${response}    http_Post    %{${env}}    /mes-gateway/api/voucher/queryMemberCoupons.json    ${coupons_post_data}    ${headers}
    ${res}    TO JSON    ${response.content}
    Log    ${res}
    #断言
    ${expect_data}    TO JSON    ${coupons_data[5]}    #预期结果数据
    Run Keyword And Continue On Failure    Assert Respone    ${res}    ${expect_data}
