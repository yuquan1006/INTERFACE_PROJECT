'''实名认证'''
import unittest
import config.globals as gc
import  case.cfp_mesgateway.Flow.flow_data as data
from common.oracleCmd import OracleUtil
from common.mesApi import Mes_Gateway_Api
from common.redisCmd import RedisCmd
from case.cfp_mesgateway.Flow.test_register_login import Register_login
from common.requestCmd import RequestCmd
class RealNameAuth(unittest.TestCase):
    def setUp(self):
        # 初始化DB
        self.mes = Mes_Gateway_Api()



    def test_land_person_realNameAuth(self):
        '''大陆个人实名认证'''
        # 初始化DB
        oracle = OracleUtil()
        self.member_code =oracle.select("FE.T_MEMBER", cond_dict={"login_name": 'qiuhua.yang@ipaylinks.com'},fields = ["member_code"])[0]
        if self.member_code:
            OracleUtil().update("FE.T_MEMBER",{"audi_status":"0"},{"member_code":"%s"%self.member_code})
            OracleUtil().executeCommit("delete from FE.T_INDIVIDUAL_INFO t where t.member_code ='{0}' or t.cer_code = '{1}'".format(self.member_code,data.mes_idCardNo))    #删除 FE.T_INDIVIDUAL_INFO 根据member_code
            OracleUtil().executeCommit("delete from FE.T_ENTERPRISE_BASE t where t.member_code = '{0}' or t.biz_licence_code = '{1}'".format(self.member_code,data.mes_biz_licence_code))   #删除 FE.T_INDIVIDUAL_INFO 根据member_code
            OracleUtil().delete("FE.T_ENTERPRISE_CERTIFICATE",{"member_code":"{0}".format(self.member_code)})  #删除 FE.T_INDIVIDUAL_INFO 根据member_code
            OracleUtil().delete("FE.T_ENTERPRISE_CONTACT",{"member_code":"%s"%self.member_code})    #删除 FE.T_INDIVIDUAL_INFO 根据member_code
            OracleUtil().delete("FE.t_member_service",{"member_code":"%s"%self.member_code})
            OracleUtil().delete("FE.t_member_service_withdraw",{"member_code":"%s"%self.member_code})
            OracleUtil().delete("FE.t_member_service_wallet",{"member_code":"%s"%self.member_code})
            OracleUtil().delete("FE.t_member_service_exchange",{"member_code":"%s"%self.member_code})
            self.mes.login(data.mes_email,data.mes_loginPwd)
        else:
            # 调用注册登录
            response = self.mes.register_login_flow_toPhone(data.mes_email,data.mes_phone)
        personIdPath = self.mes.upload_imgFile(gc.mes_idCardImg01).json()['responseObj']['fileOssKey']
        personIdBackPath = self.mes.upload_imgFile(gc.mes_idCardImg02).json()['responseObj']['fileOssKey']
        personIdHoldPath = self.mes.upload_imgFile(gc.mes_idCardImg03).json()['responseObj']['fileOssKey']
        response = self.mes.land_person_realNameAuth(personIdPath,personIdBackPath,personIdHoldPath)
        self.assertEqual(response.json()['responseCode'],"00000000")
        self.assertEqual(response.json()['responseStatus'],"1")


    @unittest.skip('调试使用')
    def test_(self):
        self.mes.login()
        self.mes.upload_imgFile(gc.mes_idCardImg01)


if __name__ == '__main__':

    unittest.main()