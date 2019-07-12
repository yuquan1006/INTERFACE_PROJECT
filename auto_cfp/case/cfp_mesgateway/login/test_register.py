import unittest,requests
from common.oracleCmd import OracleUtil
from config.globals import *
from case.cfp_mesgateway.verifyCode.test_verifyCode import Test_verifyCode
from common.redisCmd import RedisCmd
from common.requestCmd import RequestCmd
from common.mesApi import Mes_Gateway_Api
'''
url:/mes-gateway/api/sign/signup/mobile.json
data:c
method :post
'''
class Test_register(unittest.TestCase):
    '''注册接口'''
    @classmethod
    def setUpClass(cls):
        '''初始化DB'''
        cls.url = "/mes-gateway/api/sign/signup/mobile.json"
        cls.url = get_host('test1')+cls.url
        cls.s = RequestCmd()

    @classmethod
    def tearDownClass(cls):
        cls.s.close()

    def tearDown(self):
        self.s.clear_cookies()

    @unittest.skip('待完成')
    def test_Flow_Register_ToPhone(self):
        '''通过手机号注册'''
        phone = '18914714713'
        Mes_Gateway_Api().send_register_verigy(phone) # 调用发送验证码接口
        moblileVerifCode = RedisCmd.get_to_redis("MES_GATEWAY_SMS_%s_user_regist" % phone) # redis中去验证码
        datas = '{"requestObj":{"agreement":true,"loginPhone":%s,"moblileVerifCode": %s}}'%(phone,moblileVerifCode)
        response = self.s.send(data=datas)
        self.assertEqual(response.json()['responseCode'],'00000000')





if __name__ == '__main__':
    unittest.main