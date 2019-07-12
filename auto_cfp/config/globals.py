#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/12/14 15:52
# @Author  : Yuquan
# @Site    : 
# @File    : globals.py
# @Software: PyCharm
import os, pymysql

# 获取项目路径
projectPath = os.path.dirname(os.path.dirname(os.path.realpath(__file__)))
# 定义测试用例路径
testCase_PATH =  os.path.join(projectPath,'case')
# 定义测报告的路径
reportPath =  os.path.join(projectPath,'report/')
# 定义日志文件的路径
logPath = os.path.join(projectPath,'report/log/')
# 请求公用头部
headers = {"Content-Type": "application/json;charset=UTF-8"}



# redis连接信息
redis_host="139.196.75.96"
redis_port=3333
redis_password="AdeTTT32312ttedsdcaHUHU"

# 定义Mysql数据库连接信息
mysql_info = {
    'host': '127.0.0.1',
    'port': 3306,
    'user': 'root',
    'passwd': 'password',
    'charset': 'utf8',
    'cursorclass': pymysql.cursors.DictCursor
}

# 定义oracle数据库连接信息
oracle_info = {"user": "testquery",
          "pwd": "d8InpLfgCEVdJUeu",
          "dsn": "106.15.186.65:2523/cfpdbt"}

# 定义email邮箱参数
sender = '1251523660@qq.com'
passwd = "dogstzejeuwugfdb"
host = "smtp.qq.com"
port = 465
receiver = ["1251523660@qq.com", "1311766437@qq.com"]
cc = "985687042@qq.com"
subject = "邮件主题"
contents = "接口自动化测试报告邮件已发送，详情请查看附件。"
# attachments = r'%s' % reportPath
reportPath = os.path.join(projectPath, "report")
off = 1  # 发送开关


# 主机地址
def get_host(hostName):
    if hostName == 'test1':
        return "http://139.196.75.96:8054"
        # return "https://mesnew.test3.ipaylinks.com"
    elif hostName == 'test2':
        return "http://47.101.70.242"
    else:
        return None


# data目录路径
dataPath = os.path.join(projectPath,"data")


mes_idCardImg01=os.path.join(dataPath,'IdCardUp.jpg')
mes_idCardImg02=os.path.join(dataPath,'IdCardLoad.jpg')
mes_idCardImg03=os.path.join(dataPath,'IdCardHold.jpg')


if __name__ == '__main__':
    print(mes_idCardImg01)
