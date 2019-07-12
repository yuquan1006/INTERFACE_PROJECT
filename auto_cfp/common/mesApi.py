'''单个或多个接口正常调用封装，供流程自动化测试调用'''
import config.globals  as gc
from common.requestCmd import RequestCmd
import warnings
from common.oracleCmd import OracleUtil
from common.redisCmd import RedisCmd
import os
import case.cfp_mesgateway.Flow.flow_data as data


class Mes_Gateway_Api(object):
    def __init__(self):
        self.r = RequestCmd()

    def send_register_verify(self,phone):
        '''发送手机注册验证码'''
        method = "Post"
        path = "/mes-gateway/api/sign/signup/sendSmsCode.json"
        url = gc.get_host('test1') + path
        datas = {"requestObj":phone}
        response = self.r.send(url, method, json=datas)
        return response

    def register_to_phone(self,phone,verifCode):
        '''通过手机号注册'''
        method = "Post"
        path = "/mes-gateway/api/sign/signup/mobile.json"
        url = gc.get_host('test1') + path
        datas = {"requestObj":{"agreement":True,"loginPhone":phone,"moblileVerifCode":verifCode}}
        response =self.r.send(url,method,json=datas)
        return response


    def register_to_email(self,email):
        '''通过邮箱注册'''
        method = "Post"
        path = '/mes-gateway/api/sign/signup/email.json'
        url = gc.get_host('test1') + path
        datas = {"requestObj":{"agreement":True,"email":email,"recommendCode":""}}
        response =self.r.send(url,method,json=datas)
        return response
    def register_to_email_link(self,email,emailToken):
        '''点击邮箱注册跳转链接'''
        method = "Post"
        path = '/mes-gateway/api/sign/signup/activeEmail.json'
        url = gc.get_host('test1') + path
        datas = {"requestObj":{"agreement":True,"email":email,"token":emailToken}}
        response =self.r.send(url,method,json=datas)
        return response

    def improve_account_info(self,verfyToken,moblileVerifCode=None,signType=1,email=data.mes_email,phone=data.mes_phone,payPwd=data.mes_payPwd,loginPwd=data.mes_loginPwd):
        '''完善账户信息 signType=1手机号  =2邮箱'''
        method = "Post"
        path = "/mes-gateway/api/sign/signup/improveInfo.json"
        url = gc.get_host('test1') + path
        if signType==1:
            datas = {"requestObj":{"email":email,"loginPhone":phone,"payPwd":payPwd,"payPwdRepeat":payPwd,"pwd":loginPwd,"pwdRepeat":loginPwd,"recommendCode":"","signType":signType,"verfyToken":verfyToken}}
        else:
            datas = {"requestObj":{"email":email,"loginPhone":phone,"payPwd":payPwd,"payPwdRepeat":payPwd,"pwd":loginPwd,"pwdRepeat":loginPwd,"recommendCode":"","signType":signType,"verfyToken":verfyToken,"moblileVerifCode":moblileVerifCode}}
        response =self.r.send(url,method,json=datas)
        return response


    def login(self,email=data.mes_email,loginPwd=data.mes_loginPwd):
        '''登录'''
        method = "Post"
        path = "/mes-gateway/api/security/login.json"
        url = gc.get_host('test1') + path
        datas = {"requestObj":{"loginName":email,"password":loginPwd}}
        response =self.r.send(url,method,json=datas)
        token = response.json()['responseObj']['token']
        headers = {"AUTH-TOKEN":token}
        self.r.set_headers(headers=headers)
        return response

    def register_login_flow_toPhone(self,meail,phone):
        '''手机注册登录流程-'''
        RedisCmd().del_to_redis("MES_GATEWAY_SMS_SEND_TIMES_%s_user_regist" % phone)
        # 初始化DB
        OracleUtil().delete("FE.T_MEMBER", {"login_name": data.mes_email})
        # 发送注册验证码
        response = self.send_register_verify(phone=phone)
        assert response.json()['responseCode'] == "00000000"
        # 从redis获取短信验证码
        verifyCode = RedisCmd().get_to_redis("MES_GATEWAY_SMS_%s_user_regist" % data.mes_phone)
        verifyCode = verifyCode.replace('"',"")
        # 通过手机注册
        response = self.register_to_phone(phone, verifyCode)
        assert response.json()['responseCode']== "00000000"
        verifyToken = response.json()['responseObj']
        # 完善信息
        response = self.improve_account_info(verifyToken)
        assert response.text == '{"responseCode":"00000000","responseMsg":"成功响应","responseStatus":"1","responseObj":true}'
        # 登录
        response = self.login()


    def register_login_flow_toEmail(self):
        '''邮箱注册登录'''
        # 通过邮箱注册
        response = self.register_to_email(data.mes_email)
        assert response.json()['responseCode']=="00000000"
        # redis中获取邮箱注册跳转链接token
        registerToken = RedisCmd().get_to_redis("MES_REDIS_EMAIL_%s"%data.mes_email)
        # 点击邮箱注册跳转链接
        response = self.register_to_email_link(data.mes_email,registerToken)
        verfyToken = response.json()['responseObj']['verfyToken']
        assert response.json()['responseCode']=="00000000"
        #发送注册短信验证码
        response = self.send_register_verify(phone=data.mes_phone)
        assert response.json()['responseCode']=="00000000"
        # 从redis获取短信验证码
        verifyCode = RedisCmd().get_to_redis("MES_GATEWAY_SMS_%s_user_regist" % data.mes_phone)
        RedisCmd().del_to_redis("MES_GATEWAY_SMS_SEND_TIMES_%s_user_regist" % data.mes_phone)
        # 完善信息
        response = self.improve_account_info(moblileVerifCode=verifyCode,signType=2,verfyToken=verfyToken)
        assert response.text=='{"responseCode":"00000000","responseMsg":"成功响应","responseStatus":"1","responseObj":True}'
        # 登录
        response = self.login()

    def upload_imgFile(self,filePath):
        '''上传图片至OSS'''

        path = "/mes-gateway/api/file/upload.json"
        url = gc.get_host('test1') + path
        fileName = os.path.split(filePath)[1]
        # datas = {"subPath": "auth"}
        # with open(filePath, 'rb') as f:
        #     files = {'file': (filePath,f , "img/jpeg"),
        #     'subPath': (None, 'auth')}
        #     self.r.pop_headers()
        #     # self.r.set_headers({"Content-Type": "text/plain; charset=UTF-8"})
        #     response = self.r.send(url,files=files)
        #     #data = datas,
        files = {'file':(fileName,open(filePath,'rb'),'image/jpeg'),'subPath':(None,'auth')}    # 根据抓包获取，注意文件不要中文名
        self.r.pop_headers() #  headers请求头里面的content-type属性注释了，如果加上了，则会报错
        response = self.r.send(url,files=files)
        return response

    def land_person_realNameAuth(self,personIdPath,personIdBackPath,personIdHoldPath):
        '''大陆个人实名认证接口'''
        path = '/mes-gateway/api/modifyLoginPwd/doPersonAuthentication.json'
        url =gc.get_host('test1') + path
        datas = {"country":"中国大陆","name":data.mes_individualName,"cerCode":data.mes_idCardNo,"address":data.mes_address,"personIdPath":personIdPath,"personIdBackPath":personIdBackPath,"cerStartExpDate":data.mes_idCardStartExpDate,"cerExpDate":data.mes_idCardEndExpDate,"individualEnName":data.mes_individualEnName,"personIdHoldPath":personIdHoldPath,"bankCardNo":data.mes_bankCardNo,"mobile":data.mes_phone,"passport":"","city":data.mes_city,"province":data.mes_province,"birthDate":""}
        self.r.set_headers()
        # print(datas)
        resposne = self.r.send(url,json=datas) # json参数字典中包含中文发送乱码。修改源码requests/models文件中461下  body = complexjson.dumps(json)修改为: body = complexjson.dumps(json, separators=(',', ':'), ensure_ascii=False)即可。
        return resposne


    def download_table(self):
        '''下载交易报表'''
        path= '/mes-gateway/api/transaction/exportWalletInfo.json'
        url = gc.get_host('test1') + path
        datas = {"month":"2019-07","currency":""}
        self.r.set_headers()
        response = self.r.send(url,json=datas)
        return response


if __name__ == '__main__':
    m = Mes_Gateway_Api()
    # m.register_login_flow_toPhone(data.mes_email,data.mes_phone)
    # m.login("yu3@qq.com","123456yu")
    # m.upload_imgFile(gc.mes_idCardImg03)
    # a = "1"
    # print(datas)
    m.login("1532837150@qq.com","12345abc")
    response = m.download_table()
    print(response)
    with open("x.pdf",'wb') as f:
        f.write(response.content)


