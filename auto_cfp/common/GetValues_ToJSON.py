import jsonpath,json

class GetValues_ToJSON(object):
    '''
    查询json字符串中字段值
    '''
    def __init__(self, datas, fieds,loc=0):
        '''
        初始化该查询类
        :param datas: 被查询主体：json字符串
        :param fieds: 某字段：字符串
        :param result:全局列表，用来存放__find方法查到的值。
        '''
        self.datas = datas
        self.fieds = fieds
        self.loc = loc
        self.result = []

    def __check_datas(self,datas):
        '''
        检查查询主体是否是为json数据类型，并判断该json转化成那种python数据类型
        '''
        try:
            types=''
            result_type = json.loads(datas)
            if isinstance(result_type,dict):
                types = 'dict'
                return result_type,types
            elif isinstance(result_type,list):
                types = 'list'
                return result_type, types
            else:
                return None
        except BaseException as e:
            print('数据异常，可能原因为:%s'%e)
            raise BaseException(str(e))

    def __find(self,check_data,fieds):
        '''
        查询方法，通过判断数据字段是否相等和是否可以向下继续迭代查询来进行递归，最后将找到的数据存在全局列表中
        '''
        for i, j in check_data.items():
            # print('start',t_data)

            if isinstance(j,dict):
                # print(type(j))
                self.__find(check_data[i],fieds)
                continue
            if isinstance(j,list):
                j_list = self.__to_dict(j)
                for k in j_list:
                    self.__find(k,fieds)
                    continue
            if i == fieds:
                # print(i,j)
                self.result.append(j)

    def __to_dict(self,da):
        '''返回列表中字典'''
        a = []
        for i in da:
            if  isinstance(i,dict):
                a.append(i)
        return a


    def getFieds_value(self):
        '''主入口，对整个业务进行组合'''
        # print('start check .....')
        try:
            if not isinstance(self.datas,str):
                print('The input data is not json Type')
                return None
            t = self.__check_datas(self.datas)
            t_data = t[0]
            t_type = t[1]
            if t_type=='list':
                t_data = self__to_dict(t_data)
            if t_type == 'dict':
                a = self.__find(t_data,self.fieds)

                return self.result[self.loc]
            return None
        except BaseException as e:
            print('查询{}该字段出现异常，异常原因可能为:{}'.format(self.fieds,e))

if __name__ == '__main__':
    data = '{"status":200,"message":"success","tspan":556,"info":{"text":"不想要了我的抢票月卡","module":"task_engine","source":"","textScore":100,"intent":"退订，抢票月卡","intentScore":100,"emotion":"中性","emotionScore":80,"tokens":["不想/vd","要/v","了/ule","我/rr","的/ude1","抢/v","票/n","月/n","卡/n"],"taskInfo":{"scenarioInfo":{"status":"TRIGGERED_THEN_LAST_NODE","triggered":true,"finished":true,"is_weak_end":false},"targetContext":{"orderSerialNo":"FT5C8F825D20002B2UF30851"}}},"extendData":{"taskEngine":{"orderSerialNo":"FT5C8F825D20002B2UF30851"}}}'
    datas=r'{"status":200,"message":"success","tspan":556,"data":[{"type":"text","subType":"text","value":"您当前订单已购抢票月卡信息如下：\n月卡购买金额：45.0\n月卡可使用次数固定次数？？？\n您当前的月卡未使用，点击下方退订按钮，进入月卡详情页，可进行退订【去退月卡】\n温馨提示：月卡享有以下权益：XXX。$cmd[满意度指令]","data":[]},{"type":"cmd","subType":"USEFUL_TAG","value":"你觉得此回答：","data":[{"type":"GET","text":"有用","url":"/v1/feedback/taskengine?appId=d77fca8d467f4baa91186418329701b1&taskengineSessionId=73861394-8834-4c02-b38d-3eb8788fea2a&feedback=helpful"},{"type":"GET","text":"无用","url":"/v1/feedback/taskengine?appId=d77fca8d467f4baa91186418329701b1&taskengineSessionId=73861394-8834-4c02-b38d-3eb8788fea2a&feedback=unhelpful"}]}],"info":{"text":"不想要了我的抢票月卡","module":"task_engine","source":"","textScore":100,"intent":"退订，抢票月卡","intentScore":100,"emotion":"中性","emotionScore":80,"tokens":["不想/vd","要/v","了/ule","我/rr","的/ude1","抢/v","票/n","月/n","卡/n"],"taskInfo":{"scenarioInfo":{"status":"TRIGGERED_THEN_LAST_NODE","triggered":"true","finished":"true","is_weak_end":"false"},"targetContext":{"orderSerialNo":"FT5C8F825D20002B2UF30851"}}},"extendData":{"taskEngine":{"orderSerialNo":"FT5C8F825D20002B2UF30851"}}}'
    # cfp_mesgateway = GetValues_ToJSON(data, 'orderSerialNo').getFieds_value()
    # b = GetValues_ToJSON(data, 'module').getFieds_value()
    # c = GetValues_ToJSON(data, 'token').getFieds_value()
    # print(cfp_mesgateway)
    # print(b)
    # print(c)
    d = GetValues_ToJSON(datas,'text',2).getFieds_value()
    print(d)
    # print(len(d))
    # for i in d:
    #     print(i)