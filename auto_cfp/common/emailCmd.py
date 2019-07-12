#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/11/29 15:19
# @Author  : Yuquan
# @Site    : 
# @File    : emailCmd.py
# @Software: PyCharm
import yagmail
import config.globals as gc
from common.logCmd import LogHandler
log = LogHandler("main")

class EmailUtil(object):
    __instance = None
    def __new__(cls, *args, **kwargs):
        '''单例模式'''
        if not cls.__instance:
            cls.__instance = object.__new__(cls)
        return cls.__instance

    def __init__(self, reportPath = gc.reportPath):
        self.sender = gc.sender
        self.passwd = gc.passwd
        self.host = gc.host
        self.port = gc.port
        self.receiver = gc.receiver
        self.cc = gc.cc
        self.subject = gc.subject
        self.contents = gc.contents
        self.attachments = r'%s' % reportPath
        self.off = gc.off        # 发送开关
        log.debug('_/_/_/_/_/_/   初始化邮箱参数')

    def sendEmail(self):
        if self.off == 1:
            try:
                yag = yagmail.SMTP(self.sender, self.passwd, self.host, self.port)
                yag.send(to=self.receiver, subject=self.subject, contents=self.contents, cc=self.cc, attachments=self.attachments)
                yag.close()
                # print("发送邮件成功！")
                log.info("发送测试结果邮件到{}成功！".format(self.receiver))
            except BaseException as e:
                # print("发送邮件失败！可能出现错误的原因：%s" % e)
                log.error("发送邮件失败！可能出现错误的原因：%s" % e)

        else:
            # print("当前选择不发送邮件！")
            log.info("当前选择不发送邮件！")
if __name__ == '__main__':
    a = EmailUtil()
    a.sendEmail()
