# -*- coding: utf-8 -*-
# Author:Jacky Zhang
# Version:3.0
# Data: 2019-3-8

import random
import os
import time
import codecs
import hashlib
import itertools
from Crypto.PublicKey import RSA
from Crypto.Hash import SHA256
from Crypto.Signature import PKCS1_v1_5
from base64 import b64encode
from robot.api import logger
from robot.utils import (is_number, is_string, type_name, unic)

class utils:
    def __init__(self):
        #Current Log Path:
        path = os.path.abspath('.')
        self.curr_dir = path+'\\Logs'
        self.data_center = os.getenv('TEST_DATA', path+'\\TestData')
        self.script_dir = path+'\\Library\\'

    def Set_RandomId_To_Dictionary(self, dictionary, **items):
        """Replace the given ``items`` to the ``dictionary``.

        Giving items as ``items`` means giving ID keys and number of digits
        as separate arguments;

        !!!ID key must exist,number of digits must be 20 or 32!!!

        | Set RandomId To Dictionary | ${D1} | registerUserId=20 | transId=32 |

        """
        self._validate_dictionary(dictionary)

        for key in items:
            if key in dictionary:
                if items[key] == '20':
                    time_now = time.strftime("%Y%m%d%H%M%S", time.localtime())
                    random_nums = random.randint(100000, 999999)
                    dictionary[key] = time_now + str(random_nums)
                    return time_now + str(random_nums)
                elif items[key] == '32':
                    time_now = time.strftime("%Y%m%d%H%M%S", time.localtime())
                    random_nums = random.randint(100000000000000000, 999999999999999999)
                    dictionary[key] = time_now + str(random_nums)
                    return time_now + str(random_nums)
                else:
                    logger.info("Number of digits is wrong,it must be 20 or 32!!")
            else:
                logger.info("Key '%s' not found." % key)

    def _validate_dictionary(self, dictionary, position=1):
        if is_string(dictionary) or is_number(dictionary):
            raise TypeError("Expected argument %d to be a dictionary or dictionary-like, "
                            "got %s instead." % (position, type_name(dictionary)))

    def Assert_Respone(self, actual_data, expect_data):
        error = 0
        error_info = ''
        for key in expect_data:
            if key in actual_data:
                expect_str = unic(expect_data[key]).strip()
                actual_str = unic(actual_data[key]).strip()
                if expect_str == 'YES':
                    continue
                elif expect_str == actual_str:
                    continue
                else:
                    error = error+1
                    error_info = error_info + key + "--字段值错误！预期:" + expect_str + ",实际:" + actual_str + " | "
                    # logger.info(key + "--字段值错误！预期:" + expect_str + ",实际:" + actual_str)
            else:
                error = error+1
                error_info = error_info + "缺少字段:" + key + " | "
                # logger.info("缺少字段:" + key)
        if error > 0:
            error_info = "断言报错！" + error_info
            raise AssertionError(error_info)  # if error_info else AssertionError()

    def Remove_Repeat(Error_Task_List):
        Error_Task_List.sort()
        Task_List = []
        it = itertools.groupby(Error_Task_List)
        for k, g in it:
            Task_List = Task_List + [k]
        return Task_List

    def Stor_Dictionary(self, dic):
        """
        字典排序
        """
        return sorted(dic.items(), key=lambda dic:dic[0])

    def Md5(self, init_str):
        """
        md5加密
        """
        m = hashlib.md5()
        m.update(init_str.encode('UTF-8'))

        return m.hexdigest()

    def Rsa_Sign(self, message, key_file):
        try:
            with open(self.script_dir + key_file) as f:
                key = f.read()
                rsakey = RSA.importKey(key)
                signer = PKCS1_v1_5.new(rsakey)
                digest = SHA256.new()
                digest.update(message.encode('utf-8'))
                sign = signer.sign(digest)
                signature = b64encode(sign)
        except Exception as err:
            print('RSA签名失败', '', err)

        return signature.decode()

    def Join_Dictionary_To_String(self, dic):
        jString = ''
        dd = sorted(dic.items(), key=lambda dic:dic[0])
        for i in range(len(dd)):
            if dd[i][1] != '':
                jString = jString+dd[i][0]+'='+dd[i][1]+'&'
        return jString.strip('&')

    def Write_Log(self, pageSource):
        # reload(sys)
        # sys.setdefaultencoding('cp936')
        f=codecs.open(self.curr_dir+'\\f.txt','w','utf-8')
#       f=open('d:\\f.txt','w')
        f.write(pageSource)
#       self.writeline(pageSource,f)
        f.close()

    def replace_test_jsondata(self, model_data, test_data):
        self._validate_dictionary(model_data)
        self._validate_dictionary(test_data)

        for key in test_data:
            if key in model_data:
                model_data[key] = test_data[key]
            else:
                logger.info("Key '%s' not found." % key)
        return model_data

    def choose_case_data(self, case_list, case_type):
        """
        case_type:all, 正向, 反向
        :return:
        """
        if case_type == 'all':
            return case_list
        else:
            index_list = []
            for index in range(len(case_list)):
                if case_list[index][1] != case_type:
                    index_list.append(index)
            for i in reversed(index_list):
                # print("remove"+str(i))
                case_list.pop(i)
            return case_list
