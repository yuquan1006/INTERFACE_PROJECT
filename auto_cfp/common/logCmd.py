#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/12/14 15:50
# @Author  : Yuquan
# @Site    : 
# @File    : logCmd.py
# @Software: PyCharm
#!/usr/bin/python
# -*- coding: utf-8 -*-
# Version : py3.6
import logging
import os,time
from logging.handlers import TimedRotatingFileHandler
import config.globals as gc

# 日志级别
CRITICAL = 50
FATAL = CRITICAL
ERROR = 40
WARNING = 30
WARN = WARNING
INFO = 20
DEBUG = 10
NOTSET = 0


# 日志路径
logPath = gc.logPath
if not os.path.exists(logPath):
    os.mkdir(logPath)


class LogHandler(logging.Logger):
    '''
    配置loggin
    '''
    def __init__(self, name, level=DEBUG, stream=True, file=True):
        self.name = name
        self.level = level
        logging.Logger.__init__(self, self.name, level=level)
        if stream:
            self.set_stream_handler()
        if file:
            self.set_file_handler()

    def set_file_handler(self, lever=None):
        file_name = os.path.join(logPath, '{name}.log'.format(name=self.name))
        # 设置日志回滚, 保存在log目录, 一天保存一个文件, 保留15天
        self.file_handler = TimedRotatingFileHandler(filename=file_name, when='D', interval=1,backupCount=15, encoding='utf-8')
        if not lever:
            self.file_handler.setLevel(self.level)
        else:
            self.file_handler.setLevel(lever)
        # 设置日志格式
        # formatter = logging.Formatter('%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s')
        formatter = logging.Formatter('[%(asctime)s][%(filename)s:Methond:%(funcName)s(%(lineno)d)][%(levelname)s]:  %(message)s')
        self.file_handler.setFormatter(formatter)

        #
        self.addHandler(self.file_handler)

    def set_stream_handler(self,lever=None):
        self.stream_handle = logging.StreamHandler()
        # formatter = logging.Formatter(
        #     '%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s')
        formatter = logging.Formatter('[%(asctime)s][%(filename)s:Methond:%(funcName)s(%(lineno)d)][%(levelname)s]:  %(message)s')
        self.stream_handle.setFormatter(formatter)
        if not lever:
            self.stream_handle.setLevel(self.level)
        else:
            self.stream_handle.setLevel(lever)
        self.addHandler(self.stream_handle)



if __name__ == '__main__':
    logger = LogHandler('main',10)
    logger.info("ddd")






    #
    #
    #     self.logger = logging.getLogger(logger)
    #     log_file_path = gc.logName
    #     #日志保留十天时间，一天保存一个日志
    #     self.handler1 = TimedRotatingFileHandler(log_file_path,"d",1,10)
    #
    #     self.handler1 = logging.FileHandler(log_file_path, mode="cfp_mesgateway", encoding="utf-8") # 打印到文件
    #     self.handler2 = logging.StreamHandler() # 打印到终端
    #
    #     # self.formmater1=logging.Formatter('%(asctime)s - %(name)s - 【%(levelname)s】 -%(module)s:  %(message)s', datefmt='%Y-%m-%d %H:%M:%S %p',)
    #     # self.formmater2=logging.Formatter('%(asctime)s - 【%(levelname)s】 -%(module)s:  %(message)s', datefmt='%Y-%m-%d %H:%M:%S %p',)
    #     self.formmater1=logging.Formatter('%(asctime)s - %(name)s - 【%(levelname)s】 -%(module)s:  %(message)s', datefmt='%Y-%m-%d %H:%M:%S %p',)
    #     self.formmater2=logging.Formatter('%(asctime)s - %(name)s - 【%(levelname)s】 -%(module)s:  %(message)s', datefmt='%Y-%m-%d %H:%M:%S %p',)
    #
    #     self.handler1.setFormatter(self.formmater1)
    #     self.handler2.setFormatter(self.formmater2)
    #
    #     self.logger.addHandler(self.handler1)
    #     self.logger.addHandler(self.handler2)
    #     self.logger.setLevel(20)
    #
    # def info(self, msg=None):
    #     self.logger.info(msg)
    #
    # def debug(self, msg=None):
    #     self.logger.debug(msg)
    #
    # def error(self, msg=None):
    #     self.logger.error(msg)
    #
    # def fatal(self, msg=None):
    #     self.logger.fatal(msg)
    #
    # def warn(self, msg=None):
    #     self.logger.warning(msg)
#
# if __name__ == '__main__':
#     log = Logger("main")
#     log.info("测试通过")
#     log.warn("测试通过")
#     log.debug("测试通过")