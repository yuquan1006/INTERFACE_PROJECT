# import unittest,requests
# from common.oracleCmd import OracleUtil
# from config.globals import *
# import case.cfp_mesgateway.Flow.flow_data as data
#
# class Test_login(unittest.TestCase):
#     @classmethod
#     def setUpClass(cls):
#         '''初始化DB'''
#         OracleUtil().delete("FE.T_MEMBER",cond_dict={"login_name":data.mes_email})
#         OracleUtil().orcle_close()
#         cls.url = ""
#         cls.s = requests.Session()
#
#     @classmethod
#     def tearDownClass(cls):
#         '''清除插入数据'''
#         cls.s.close()
#
#     def tearDown(self):
#         self.s.cookies.clear()
#
#     def test_Flow_login(self):
#
# if __name__ == '__main__':
#     unittest.main()