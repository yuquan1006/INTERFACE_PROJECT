#!/usr/bin/python
# -*- coding: utf-8 -*-
# Version : py3.6
from BeautifulReport import BeautifulReport
import unittest, os
from common.emailCmd import EmailUtil
from common.logCmd import LogHandler
import config.globals as gc
log = LogHandler("main")

def all_tests(casename = "case" ,rule="test*.py"):
    curPath = os.path.realpath(__file__)
    parPath = os.path.dirname(curPath)
    if isinstance(casename,list):
        for i in range(0,len(casename)):
            parPath = os.path.join(parPath,casename[i])
        rePath = parPath
    else:
        rePath = os.path.join(parPath,casename)
    # print(rePath)
    log.info("执行{}下测试用例".format(rePath))
    discover = unittest.defaultTestLoader.discover(rePath, pattern=rule)
    return discover

def run(discover):
    reportPath = gc.reportPath
    if not os.path.exists(reportPath):
        os.mkdir(reportPath)
    filename = '接口自动化测试报告'
    BeautifulReport(discover).report(filename='接口自动化测试报告', description='一期接口测试', log_path=reportPath)
    report_abspath = os.path.join(reportPath, filename+".html")

    # 调用add_case函数返回值
    # runner.run(discover)
    # fp.close()
    log.info("测试报告生成成功在：%s" % report_abspath)
    # print("测试报告生成成功在：%s" % report_abspath)
    return report_abspath




if __name__ == '__main__':
    # a = all_tests(casename="case")
    a = all_tests(casename=["case","cfp_mesgateway"])
    print(a)
    path = run(a)
    e = EmailUtil(path).sendEmail()
    # e.sendEmail()

