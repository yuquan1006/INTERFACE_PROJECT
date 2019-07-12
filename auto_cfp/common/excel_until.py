#!/usr/bin/python
# -*- coding: utf-8 -*-
# Version : py3.6
import xlrd,xlwt
# 获取excel中测试数据，结合ddt使用

class ExcelUntil:
    def __init__(self,excelPath, sheetName='Sheet1',type='r'):
        self.excelPath = excelPath
        self.sheetName = sheetName
        if type == 'r':
            self.work = xlrd.open_workbook(self.excelPath)
            self.sheet = self.work.sheet_by_name(self.sheetName)
            # 获取第一行的值作为key
            self.keys = self.sheet.row_values(0)
            # 获取总行数
            self.rowNum = self.sheet.nrows
            # 获取总列数
            self.colNum = self.sheet.ncols
        elif type == 'w':
            self.work = xlwt.Workbook('utf-8')


        else:
            print('类型错误！')

    # 获取第一列全部数据
    def getFirstData(self):
        row_data = self.sheet.get_rows()
        data_list = []
        # for num,data in enumerate(row_data):
        #     if num == 0:
        #         continue
        #     yield (data[0].value)
        for i in range(1,self.rowNum):
            yield self.sheet.cell_value(i,0)
        #
            # data_list = self.sheet.cell_value(i,0)
            # print(data_list)
            # return data_list

    def writeTwoData(self,tups,sheetname='Sheet2'):
        '''tups = [(cfp_mesgateway,b)]'''
        self.sheet = self.work.add_sheet(sheetname)
        for num,i in enumerate(tups):
            print(i,num)
            self.sheet.write(num,0,i[0])
            self.sheet.write(num,1,i[1])
        self.work.save(self.excelPath)
        print('写入完成')


    def dict_data(self):
        if self.rowNum <= 1:
            print("总行数小于或等于1")
        else:
            r = []
            j = 1
            for i in range(self.rowNum - 1):
                s = {}
                value = self.sheet.row_values(j)
                for k in range(self.colNum):
                    s[self.keys[k]] = value[k]
                r.append(s)
                j += 1
        return r

if __name__ == '__main__':
    # filepath = r"C:\Users\Administrator\Desktop\1.xls"
    # sheetname = "Sheet1"
    # cfp_mesgateway = ExcelUntil(filepath, sheetname)
    # print(cfp_mesgateway.dict_data())
    # b = ExcelUntil(r"C:\Users\A\wechat_downloadfile\WeChat Files\yu18973019192\Files\test.xlsx",'Sheet1')
    # for i in b.getFirstData():
    #     print(i)
    # # print(b.getFirstData())

    tups = [("不记得账号密码了怎么办","不知道同程账号密码怎么办"),
            ("为什么不能分期。", "程程白条/同同宝无法支付怎么办"),
            ("你好。我订沈阳回鄂尔多斯机票，怎么不可以用白条呢", "程程白条/同同宝无法支付怎么办"),
            ]
    c = ExcelUntil(r"C:\Users\A\Desktop\test.xls",type='w')
    c.writeTwoData(tups)
    #
    # b = ExcelUntil(r"C:\Users\A\Desktop\test.xls",'Sheet1')
    # for i in b.getFirstData():
    #     print(i)
    # print(b.getFirstData())
