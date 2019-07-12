import unittest,requests
from common.oracleCmd import OracleUtil
from config.globals import *
from common.requestCmd import RequestCmd

'''
url:"/mes-gateway/api/sign/signup/sendSmsCode.json"
method:post
data: {"requestObj":"phone"}
'''
class Test_verifyCode(unittest.TestCase):
    '''注册验证码接口'''
    @classmethod
    def setUpClass(cls):
        # 初始化DB
        cls.oracle = OracleUtil()
        cls.oracle.delete("FE.T_MEMBER", {"login_phone": "18900000001"})
        sql = "select FE.SEQ_MEMBER_MEMBER_CODE.nextval from dual" # 获得序列your_sequence的下一个值
        member_code = cls.oracle.executeSql(sql)[0]
        sql = "INSERT INTO FE.T_MEMBER (MEMBER_CODE, TYPE, SERVICE_LEVEL_CODE, GREETING, STATUS, SECURITY_QUESTION, SECURITY_ANSWER, SSO_USER_ID, LOGIN_TYPE, LOGIN_NAME, LOGIN_PWD, CREATE_DATE, UPDATE_DATE, WITHDRAW_CYCLE, IS_GAME, ACTIVE_TIME, LOCK_STATUS, BUSINESS_TYPE, LOGIN_PHONE, NATION_REGION, AUDI_STATUS, APPLICANT_TYPE, RESIDENT_REGION, NICKNAME, AUDI_REMARK, ACCT_STATUS, MEMBER_LEVEL, TRACE_STATUS, CUST_MANAGER_EMAIL, CUST_MANAGER_NAME, IP, RECOMMEND_CODE, REGISTER_SOURCE, SHOP_AUTH, PRODUCT_LIST, LIGHTNING_RECEIPT_ACTIVE, USER_ID, SUPPLY_USER_ID, SUPPLY_NAME, AUDITOR, REFUSE_REASON, ENTERPRISE_UPGRADE_STATUS, PAY_PWD, RISK_LEVEL) VALUES ( {},0, null, null, 1, null, null, null, null, '60000000qq.com', '09a7132957b49afda6e7e2bf4dedd3b950bca173', TO_DATE('2019-06-27 20:49:43', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2019-06-27 20:49:43', 'YYYY-MM-DD HH24:MI:SS'), 0, null, null, null, 1, '18900000001', null, 0, null, null, '18900000001', null, 1, 'V', 0, null, null, '116.247.106.162', null, null, 1, null, null, null, null, null, null, null, 0, 'ef08563d0a4d8c5855e6071dc8bef3a30b5ed4a3', null)".format(member_code)
        cls.oracle.executeCommit(sql)
        cls.oracle.delete("FE.T_MEMBER", {"login_phone": "15456112345"})
        cls.url = "/mes-gateway/api/sign/signup/sendSmsCode.json"
        cls.url = get_host('test1')+cls.url
        cls.s = RequestCmd()
    @classmethod
    def tearDownClass(cls):
        cls.oracle.orcle_close()
        cls.s.close()

    def tearDown(self):
        self.s.clear_cookies()

    def test_verifyCode_Flow_ToRegister(self):
        '''手机号未注册'''
        datas = {"requestObj":"15456112345"}
        response = self.s.send(self.url, json=datas)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()["responseCode"], "00000000")

    def test_verifyCode_Except_PhoneIsNull(self):
        '''手机号为空'''
        datas = {"requestObj":""}
        response = self.s.send(self.url,json=datas)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()["responseCode"], "20100001")
        self.assertEqual(response.json()["responseMsg"], "请求参数错误")

    def test_verifyCode_Except_PhoneIsExixtes(self):
        '''手机号已注册'''
        datas = {"requestObj":"18900000001"}
        response = self.s.send(self.url, json=datas)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()["responseCode"], "00000000")
        self.assertEqual(response.json()["responseMsg"], "成功响应")


    def test_verifyCode_Except_PhoneIsLong(self):
        '''手机号超出11位'''
        datas = {"requestObj":"15100210121111"}
        response = self.s.send(self.url,json=datas)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()["responseCode"], "20101003")
        self.assertEqual(response.json()["responseMsg"], "手机号无效")

    def test_verifyCode_Except_PhoneIsMix(self):
        '''手机号混合格式'''
        datas = {"requestObj":"151451214ac"}
        response = self.s.send(self.url,json=datas)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()["responseCode"], "20101003")
        self.assertEqual(response.json()["responseMsg"], "手机号无效")


if __name__ == '__main__':
    unittest.main()