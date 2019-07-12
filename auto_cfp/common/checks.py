#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2019/1/31 14:47
# @Author  : Yuquan
# @Site    : 
# @File    : checks.py
# @Software: PyCharm
import json,jsonpath,sys
from json.decoder import JSONDecodeError
from common.logCmd import LogHandler
log = LogHandler("main")


class CheckResult(object):
    '''校验请求结果 主要是针对返回json数据'''
    def check_JsonPath_equals(self,response,exprs,expectedresult):
        '''jsonpath 检查点  cfp_mesgateway == b
        '''

        try:
            actualresult =  self.check_JsonPath_exits(response,exprs=exprs,flag=False)
            actualresult = actualresult[0]
            f_str = self.__strs('检查预期结果和实际结果是否一致', actualresult, expectedresult, result='Fail')
            p_str = self.__strs('检查预期结果和实际结果是否一致', actualresult, expectedresult)
            assert actualresult == expectedresult
            log.info('check success!')
            print('check success!')
            log.info('【%s】的解析结果为:%s'%(exprs,actualresult))
            print('【%s】的解析结果为:%s'%(exprs,actualresult))
            log.info(p_str)
            print(p_str)
            return  True
        except AssertionError:
            log.info(' -> check Fail！')
            print(' -> check Fail！')
            log.info(f_str)
            print(f_str)
            return  False
        except:
            log.fatal('Error! Program exception - > exit()')
            print('Error! Program exception - > exit()')
            raise

    def check_JsonPath_exits(self, response, exprs,flag=True):
        '''jsonpath 检查点  cfp_mesgateway 存在
        '''
        try:
            # response——>python 数据类型
            result = response.json()
            # jsonpath表达式
            actualresult = jsonpath.jsonpath(result, expr=exprs)
            # actul and expect assert
            f_str = self.__strs('检查预期结果是否存在', actualresult, None, result='Fail')
            p_str = self.__strs('检查预期结果是否存在', actualresult, None)
            assert actualresult != False
            # log.info('check success!')
            if flag == True:
                log.info('【%s】的解析结果为:%s' % (exprs, actualresult[0]))
                print('【%s】的解析结果为:%s' % (exprs, actualresult[0]))
                log.info(p_str)
                print(p_str)
            return actualresult

        except AssertionError:
            log.info(' check_JsonPath_exits -> check Fail！')
            print(' check_JsonPath_exits -> check Fail！')
            log.info(f_str)
            print(f_str)
            return False
        except:
            log.fatal('Error! Program exception - > exit()')
            print('Error! Program exception - > exit()')
            raise

    def check_Contains(self,actualresult,expectedresult):
        '''包含检查点 - > cfp_mesgateway 包含e'''
        try:
            if not isinstance(actualresult,str):
                actualresult = str(actualresult)
            result = None
            if expectedresult in actualresult:
                result = True

            f_str = self.__strs('检查实际结果等于预期结果', actualresult, expectedresult, result='Fail')
            p_str = self.__strs('检查实际结果等于预期结果', actualresult, expectedresult)
            assert result==True
            log.info('contains  check Successful!')
            print('contains  check Successful!')
            log.info(p_str)
            print(p_str)
            return True

        except AssertionError:
            log.info('%s not contains  -> check Fail！')
            print('%s not contains  -> check Fail！')
            log.info(f_str)
            print(f_str)
            return False
        except:
            log.fatal('Error! Program exception - > exit()')
            print('Error! Program exception - > exit()')
            raise

    def check_NotContains(self,actualresult,expectedresult):
        '''不包含检查点 - > cfp_mesgateway 包含e'''

        try:
            if not isinstance(actualresult,str):
                actualresult = str(actualresult)
            result = None
            if expectedresult not in actualresult:
                result = True
            f_str = self.__strs('检查实际结果不包含预期结果', actualresult, expectedresult, result='Fail')
            p_str = self.__strs('检查实际结果不包含预期结果', actualresult, expectedresult)
            assert result==True
            log.info('not contains check Successful!')
            print('not contains check Successful!')
            log.info(p_str)
            print(p_str)
            return True

        except AssertionError:
            log.info('%s not contains -> check Fail！')
            print('%s not contains -> check Fail！')
            log.info(f_str)
            print(f_str)
            return False
        except:
            log.fatal('Error! Program exception - > exit()')
            raise


    def check_Equals(self,actualresult,expectedresult):
        '''相等检查点'''
        log.info('start enter 【check_Equals】')

        try:
            if not isinstance(actualresult, str):
                actualresult = str(actualresult)
                f_str = self.__strs('检查实际结果等于预期结果', actualresult, expectedresult, result='Fail')
                p_str = self.__strs('检查实际结果等于预期结果', actualresult, expectedresult)
                assert actualresult==expectedresult
                log.info('String equality assertion successful!')
                print('String equality assertion successful!')
                log.info(p_str)
                print(p_str)
                return True

        except AssertionError :
            # print('字符串相等断言失败！可能原因:%s'%e)
            log.info('String equality assertion Fail!！')
            print('String equality assertion Fail!！')
            log.info(f_str)
            print(f_str)
            return False

        except:
            log.fatal('Error! Program exception - > exit()')
            raise


    def check_NotEquals(self,expectedresult,actualresult):
        '''不相等检查点'''
        log.info('start enter 【check_NotEquals】')

        try:
            if not isinstance(actualresult,str):
                actualresult = str(actualresult)

            f_str = self.__strs('检查实际结果不等于预期结果', actualresult, expectedresult, result='Fail')
            p_str = self.__strs('检查实际结果不等于预期结果', actualresult, expectedresult)
            assert actualresult != expectedresult
            log.info('String not equality assertion successful')
            print('String not equality assertion successful')
            log.info(p_str)
            print(p_str)
            return True

        except AssertionError :
            log.info('String not equality assertion failed！')
            print('String not equality assertion failed！')
            log.info(f_str)
            print(f_str)
            return False
        except:
            log.fatal('Error! Program exception - > exit()')
            print('Error! Program exception - > exit()')
            raise

    def check_NotNone(self,actual,type='json'):
        '''不为空检查点'''
        if type == "json":
            try:
                # json 数据转python数据类型

                result = json.loads(actual)  # JSON数据转化python数据异常
                f_str = self.__strs('检查返回数据不为空', actual, None, result='Fail')
                p_str = self.__strs('检查返回数据不为空', actual, None)
                assert len(result)>0
                log.info(' Not null assertion success！')
                print(' Not null assertion success！')
                log.info(p_str)
                print(p_str)
                return True
            except JSONDecodeError:
                # print('返回数据不为json数据，转化失败,程序异常')
                log.info('Response data no Json，Conversion failed, program exception')
                print('Response data no Json，Conversion failed, program exception')
                raise
            except AssertionError:
                log.info('Not null assertion failed')
                print('Not null assertion failed')
                print(f_str)
                log.info(f_str)
                return False
            except BaseException as e:
                log.fatal('Error! Program exception - > exit()%s'%e)
                print('Error! Program exception - > exit()%s'%e)
                raise

        else:
            try:
                f_str = self.__strs('检查返回数据不为空', actual, None, result='Fail')
                p_str = self.__strs('检查返回数据不为空', actual, None)
                assert len(actual) > 0
                log.info('Not null assertion success！')
                print('Not null assertion success！')
                log.info(p_str)
                print(p_str)
                return True
            except AssertionError:
                log.info("Not null assertion failed！")
                print("Not null assertion failed！")
                print(f_str)
                log.info(f_str)
                return False
            except:
                log.fatal('Error! Program exception - > exit()')
                print('Error! Program exception - > exit()')
                raise


    def check_status_code(self,actualcode,expectioncode):
        '''返回状态码校验 入参为String'''
        try:
            if not isinstance(expectioncode,int):
                log.info('expectioncode data type error！请检查！')
                return False
            # print(type(actualcode),type(expectioncode))
            f_str = self.__strs('检查返回状态码和预期状态码是否一致', actualcode, expectioncode, result='Fail')
            p_str = self.__strs('检查返回状态码和预期状态码是否一致', actualcode, expectioncode)
            assert actualcode == expectioncode
            log.info('status code assert success! ')
            print('status code assert success! ')
            log.info(p_str)
            print(p_str)
            return True
        except AssertionError:
            log.info("status code assert failed!")
            print("status code assert failed!")
            print(f_str)
            log.info(f_str)
            return False
        except:
            log.fatal('Error! Program exception - > exit()')
            print('Error! Program exception - > exit()')


    def __strs(self,target,actualresult,expectedresult,result='Pass'):
        '''字符串模版'''
        strs = '''
-------------------| %s |---------------------
【目标】:%s
【实际】:%s    【预期】:%s
【结果】:%s
''' % (sys._getframe().f_back.f_code.co_name,target,actualresult, expectedresult,result)
        return strs

if __name__ == '__main__':
    c = CheckResult()
    # result = c.check_Contains('1211234322222','1234')
    # print(result)
    # result = c.check_NotContains('{"customCode":1001,"message":"手机格式错误"}','{"customCode":1002,"message":"xxx"}')
    # print(result)
    # result = c.check_NotNone('120',type=2)
    # print(result)
    c.check_status_code('200','400')
    c.check_JsonPath_equals('{"active":true,"token":"904C8BC55B5E4063955A938BE53D8CA7","type":1}',exprs='$.type',expectedresult=1)


