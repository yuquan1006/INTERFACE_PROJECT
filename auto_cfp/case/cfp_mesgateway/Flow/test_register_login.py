'''注册登录流程'''
import unittest
import config.globals  as gc
from common.oracleCmd import OracleUtil
from common.mesApi import Mes_Gateway_Api
from common.requestCmd import RequestCmd
from common.redisCmd import RedisCmd
import case.cfp_mesgateway.Flow.flow_data as data

class Register_login(unittest.TestCase):
    def setUp(self):
        # 初始化DB
        OracleUtil().delete("FE.T_MEMBER", {"login_name": data.mes_email})
        self.mes = Mes_Gateway_Api()

    def test_register_login_to_phone(self):
        '''手机号注册登录'''
        self.mes.register_login_flow_toPhone(data.mes_email,data.mes_phone)
        # # 发送注册验证码
        # response = self.mes.send_register_verify(phone=data.mes_phone)
        # self.assertEqual(response.json()['responseCode'],"00000000")
        # # 从redis获取短信验证码
        # verifyCode = RedisCmd().get_to_redis("MES_GATEWAY_SMS_%s_user_regist" % data.mes_phone)
        # RedisCmd().del_to_redis("MES_GATEWAY_SMS_SEND_TIMES_%s_user_regist" % data.mes_phone)
        # # 通过手机注册
        # response = Mes_Gateway_Api.register_to_phone(data.mes_phone,verifyCode)
        # self.assertEqual(response.json()['responseCode'],"00000000")
        # verifyToken = response.json()['responseObj']
        # # 完善信息
        # response = Mes_Gateway_Api.improve_account_info(verifyToken)
        # self.assertEqual(response.text,'{"responseCode":"00000000","responseMsg":"成功响应","responseStatus":"1","responseObj":true}')
        # # 登录
        # response = Mes_Gateway_Api.login()

    def test_register_login_to_email(self):
        '''邮箱注册登录'''
        # 通过邮箱注册
        response = self.mes.register_to_email(data.mes_email)
        self.assertEqual(response.json()['responseCode'],"00000000")
        # redis中获取邮箱注册跳转链接token
        registerToken = RedisCmd().get_to_redis("MES_REDIS_EMAIL_%s"%data.mes_email)
        # 点击邮箱注册跳转链接
        response = self.mes.register_to_email_link(data.mes_email,registerToken)
        verfyToken = response.json()['responseObj']['verfyToken']
        self.assertEqual(response.json()['responseCode'],"00000000")
        #发送注册短信验证码
        response =  self.mes.send_register_verify(phone=data.mes_phone)
        self.assertEqual(response.json()['responseCode'],"00000000")
        # 从redis获取短信验证码
        verifyCode = RedisCmd().get_to_redis("MES_GATEWAY_SMS_%s_user_regist" % data.mes_phone)
        RedisCmd().del_to_redis("MES_GATEWAY_SMS_SEND_TIMES_%s_user_regist" % data.mes_phone)
        verifyCode = verifyCode.replace('"',"")
        # 完善信息
        response =  self.mes.improve_account_info(moblileVerifCode=verifyCode,signType=2,verfyToken=verfyToken)
        self.assertEqual(response.text,'{"responseCode":"00000000","responseMsg":"成功响应","responseStatus":"1","responseObj":true}')
        # 登录
        response =  self.mes.login()



if __name__ == '__main__':
    unittest.main(warnings='ignore')

