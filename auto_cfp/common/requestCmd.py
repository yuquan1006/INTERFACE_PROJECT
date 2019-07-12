import requests,os
from common.logCmd import LogHandler
import json,re
log = LogHandler("main")
METHODS = ['GET', 'POST', 'HEAD', 'TRACE', 'PUT', 'DELETE', 'OPTIONS', 'CONNECT']
import config.globals  as gc



def retry(func):
    '''请求返回500,重试机制'''
    def __wrapper(me_instance,*args, **kwargs):
        MAX_TRIES = 3
        tries = 1
        while True:
            response = func(me_instance,*args, **kwargs)
            if response.status_code == 500 and tries <= 3:
                log.info('请求状态码返回{}，开始重试第{}!'.format(response.status_code,tries))
                tries += 1
                continue
            break
        return response
    return __wrapper


class UnSupportMethodException(Exception):
    """当传入的method的参数不是支持的类型时抛出此异常。"""
    pass

headers_01 = {"Content-Type": "application/json;charset=UTF-8"}
headers ={"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36","Content-Type": "application/json;charset=UTF-8"}
class RequestCmd(object):
    """
    http请求的client。初始化时传入url、method等，可以添加headers和cookies，但没有auth、proxy。示例：RequestCmd('http://www.baidu.com').send()
    """
    __instance = None
    def __new__(cls, *args, **kwargs):
        '''单例模式'''
        if not cls.__instance:
            cls.__instance = object.__new__(cls)
        return cls.__instance

    def __init__(self,headers=headers, cookies=None):
        """headers: 字典。 例：headers={'Content_Type':'text/html'}，cookies也是字典。"""
        self.session = requests.session()
        self.set_headers(headers)
        self.set_cookies(cookies)

    def set_headers(self, headers=headers_01):
        '''设置头部，默认更新Content-Type=application/json;charset=UTF-8'''
        if headers:
            self.session.headers.update(headers)

    def pop_headers(self,key="Content-Type"):
        '''请求头部中去掉Content-Type.比如上传图片接口如果头部中有Content-Type=json那种，就会请求失败。这样的话我就可以去掉这个key。'''
        if self.session.headers.get(key,default=None) != None:
            self.session.headers.pop(key)

    def set_cookies(self, cookies):
        '''更新会话中缓存'''
        if cookies:
            self.session.cookies.update(cookies)
    def clear_cookies(self):
        '''清除会话中缓存'''
        self.session.cookies.clear()

    def send(self,url,method='post',params=None, data=None,verify=False,charset='utf8',**kwargs):
        '''发送请求'''
        method = method.upper()
        if method not in METHODS:
            raise UnSupportMethodException('不支持的method:{0}，请检查传入参数！'.format(self.method))
        if data:
            data = self._handle_data(data) # 处理传送中文编码不对
        # 获取请求参数
        requests_data = [] # 定义请求参数变量
        if params:
            requests_data.append("Params")
            requests_data.append(params)
        elif data:
            requests_data.append("Data")
            requests_data.append(data)
        elif kwargs.get('json'):
            requests_data.append("Json")
            requests_data.append(json.dumps(kwargs.get('json'),ensure_ascii=False)) # ensure_ascii=False
        else:
            requests_data=[None,None]
        response = self.session.request(method=method, url=url, params=params, data=data,verify=verify,**kwargs)
        response.encoding = 'utf-8'

        log.info('请求方法:{0}'.format(method))
        log.info('请求接口:{0}'.format(url))
        log.info('请求参数类型:{}     参数值:{}'.format(requests_data[0],requests_data[1]))
        log.info('请求头部:{}'.format( self.session.headers))
        result = response.text
        if len(response.text)>=1000:
            result = response.text[:1000]
        log.info('请求成功:{0} 请求返回数据(字符串）:{1}'.format(response, result))
        # print(response.json())
        return response

    def _handle_data(self, data):
        # 将请求携带参数里面的中文进行处理,data为字典格式

        if not isinstance(data,dict):
            data = json.loads(data)
        for k, v in data.items():
            try:
                # 匹配出带有汉字的value
                hanzi = re.search(r'[\u4E00-\u9FA5]*', v).group()
                #  匹配到，则替换；未匹配到，不做任何处理
                if hanzi:
                    data[k] = hanzi.encode('utf-8').decode('latin1')
            except Exception as f:
                print("my:",f)
                pass
        # print(data)
        # print(str(data))
        return data

    def close(self):
        '''关闭会话'''
        self.session.close()




if __name__ == '__main__':
    print(os.path.join(gc.projectPath,'data','IdCardUp.jpg'))
    url = "http://139.196.75.96:8054/mes-gateway/api/security/login.json"
    datas = {"requestObj": {"loginName": "余泉@qq.com", "password": "123456yu"}}
    r = RequestCmd()
    r.set_headers()
    response = r.send(url,json=datas)
    r.set_headers({"AUTH-TOKEN": response.json()['responseObj']['token']})
    files = {'file': (os.path.join(gc.projectPath,'data','IdCardUp.jpg'),open(os.path.join(gc.projectPath,'data','IdCardUp.jpg'), 'rb'), "img/jpeg")}
    # files = {'file': (os.path.join(os.path.dirname(os.path.realpath(__file__)), "1.jpg"), open(os.path.join(os.path.dirname(os.path.realpath(__file__)), "1.jpg"), 'rb'), "img/jpeg")}
    data = {"subPath": "auth"}
    r.pop_headers()
    response = r.send("http://139.196.75.96:8054/mes-gateway/api/file/upload.json", data=data, files=files)
    # r.post(url,data=datas)
    r.close()
