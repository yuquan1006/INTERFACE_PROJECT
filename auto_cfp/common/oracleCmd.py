#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/11/23 17:38
# @Author  : Yuquan
# @Site    : 
# @File    : oracleCmd.py
# @Software: PyCharm

# coding:utf-8
import cx_Oracle
import os
from common.logCmd import LogHandler
import config.globals  as gc
import inspect
os.environ['NLS_LANG'] = 'SIMPLIFIED CHINESE_CHINA.UTF8'
u'''Oracle数据库相关操作
连接数据库名称如：xxx
查询一组数据：oracle_getrows
查询某个字段对应的字符串：oracle_getstring
执行sql语句：oracle_sql
关闭oracle连接：oracle_close
'''

log = LogHandler("main")


class OracleUtil():
    __instance = None
    def __new__(cls, *args, **kwargs):
        '''单例模式'''
        if not cls.__instance:
            cls.__instance = object.__new__(cls)
        return cls.__instance

    def __init__(self):
        ''' 连接池方式'''
        self.db_info = gc.oracle_info
        self.con = OracleUtil.__getConnect(self.db_info)
        self.cur = self.con.cursor()
        # 所有的查询，都在连接 con 的一个模块 cursor 上面运行的

    def __getConnect(db_info):
        ''' 静态方法，从连接池中取出连接'''
        try:
            con = cx_Oracle.connect(db_info['user'], db_info['pwd'], db_info['dsn'])
            log.info(" 数据库连接建立成功")
            return con
        except Exception as a:
            # print(" 数据库连接异常：%s" % cfp_mesgateway)
            log.error(" 数据库连接异常：%s" % a)

    # 获取上个查询的结果
    def getOneData(self):
        # 取得上个查询的结果，是单个结果
        data = self.cur.fetchone()
        log.info(" 取得上个查询的结果：%s" %data)
        return data

    def orcle_close(self):
        ''' 关闭orcle连接'''
        try:
            self.con.close()
            log.info(" 关闭当前数据库连接")
        except Exception as a:
            # print("数据库关闭时异常：%s" % cfp_mesgateway)
            log.error("数据库关闭时异常：%s" % a)

    def executeSql(self, sql=''):
        """执行sql语句，针对读操作返回结果集
            args：sql  ：sql语句
            返回：一条结果：(一行数据)
                多条结果：[{第一行数据},{第二行数据}]

        """
        try:
            log.info("开始执行:[{}]语句".format(sql))
            self.cur.execute(sql)
            records = self.cur.fetchall()  # [{第一行数据},{第二行数据}]
            if len(records) == 1:
                log.info("执行完成，结果为:{}".format(records[0]))
                return records[0]
            log.info("执行完成，结果为:{}".format(records))
            return records
        except BaseException as e:
            error = 'MySQL execute failed! ERROR (%s): %s' % (e.args[0], e.args[1])
            # print(error)
            log.error(error)


    def executeCommit(self, sql=''):
        """执行数据库sql语句，针对更新,删除,事务等操作失败时回滚

        """
        try:
            log.info("开始执行:[{}]语句".format(sql))
            self.cur.execute(sql)
            self.con.commit()
            log.info("执行完成，影响行数结果结果为:{}".format(self.cur.rowcount))
            return self.cur.rowcount
        except BaseException as e:
            log.info(" 执行DDL语句出错，开始回滚事务")
            self.con.rollback()
            error = 'MySQL execute failed! ERROR (%s): %s' % (e.args[0], e.args[1])
            # print("error:", error)
            log.error(error)
            return error

    def select(self, tablename, cond_dict='', order='', fields='*'):
        """查询数据
            args：
                tablename  ：表名字
                cond_dict  ：查询条件
                order      ：排序条件
                fields     :查询结果字段
            example：
                print mydb.select(table)
                print mydb.select(table, fields=["name"])
                print mydb.select(table, fields=["name", "age"])
                print mydb.select(table, cond_dict={"name":"羽泉"}，order="create_time",cfields=["age", "name"])
        """
        consql = ' '
        if cond_dict != '':
            for k, v in cond_dict.items():
                consql = consql + k + '=' + "'" + v + "'" + ' and'
        consql = consql + ' 1=1 '
        if fields == "*":
            sql = 'select * from %s where ' % tablename
        else:
            if isinstance(fields, list):
                fields = ",".join(fields)
                sql = 'select %s from %s where ' % (fields, tablename)
            else:
                print("fields input error, please input list fields.")
        if order != '':
            order = ' order by %s desc' % order
        sql = sql + consql + order
        log.info('select:' + sql)
        return self.executeSql(sql)

    def insert(self, tablename, params):
        """
            args：
                tablename  ：表名字
                key        ：属性键
                value      ：属性值
       insert("app_01_event",{"name":'小米2发布会',"`limit`":"10","status":"0","address":"测试部","start_time":"2019-06-30","create_time":str(nows)})
        """
        key = []
        value = []
        for tmpkey, tmpvalue in params.items():
            key.append(tmpkey)
            if isinstance(tmpvalue, str):
                value.append("\'" + tmpvalue + "\'")
            else:
                value.append(tmpvalue)
        attrs_sql = '(' + ','.join(key) + ')'
        values_sql = ' values(' + ','.join(value) + ')'
        sql = 'insert into %s' % tablename
        sql = sql + attrs_sql + values_sql
        log.info('_insert:' + sql)
        # print('_insert:' + sql)
        self.executeCommit(sql)
        # print('影响行数:%s' % )

    def insertMany(self, table, attrs, values):
        """插入多条数据
            args：
                tablename  ：表名字
                attrs        ：属性键[]
                values      ：属性值[]

            example：
                table='test_mysqldb'
                key = ["id" ,"name", "age"]
                value = [[101, "liuqiao", "25"], [102,"liuqiao1", "26"], [103 ,"liuqiao2", "27"], [104 ,"liuqiao3", "28"]]
                mydb.insertMany(table, key, value)
               #  executemany示例：
                    sql = "INSERT INTO table(key) VALUES(%s,%s,%s,%s,%s)"
                    cursor.executemany(sql,value)
        """
        values_sql = ['%s' for v in attrs]
        attrs_sql = '(' + ','.join(attrs) + ')'
        values_sql = ' values(' + ','.join(values_sql) + ')'
        sql = 'insert into %s' % table
        sql = sql + attrs_sql + values_sql
        # print('insertMany:' + sql)
        log.info('insertMany:' + sql)
        try:
            # print(sql)
            log.info("开始执行:[{}]语句".format(sql))
            rows = self.cur.executemany(sql, values)  #
            self.con.commit()
            log.info("执行完成，影响行数结果结果为:{}".format(rows))

        except BaseException as e:
            log.info(" 执行DDL语句出错，开始回滚事务")
            self.con.rollback()
            error = 'insertMany executemany failed! ERROR (%s): %s' % (e.args[0], e.args[1])
            print(error)
            log.error(error)

    def delete(self, tablename, cond_dict=''):
        """删除数据

            args：
                tablename  ：表名字
                cond_dict  ：删除条件字典

            example：
                params = {"name" : "caixinglong", "age" : "38"}
                mydb.delete(table, params)

        """
        consql = ' '
        if cond_dict != '':
            for k, v in cond_dict.items():
                if isinstance(v, str):
                    v = "\'" + v + "\'"
                consql = consql + tablename + "." + k + '=' + v + ' and '
        consql = consql + ' 1=1 '
        sql = "DELETE FROM %s where%s" % (tablename, consql)
        log.info("delete: {}".format(sql))
        return self.executeCommit(sql)

    def update(self, tablename, attrs_dict, cond_dict):
        """更新数据

            args：
                tablename  ：表名字
                attrs_dict  ：更新属性键值对字典
                cond_dict  ：更新条件字典

            example：
                params = {"name" : "caixinglong", "age" : "38"}
                cond_dict = {"name" : "liuqiao", "age" : "18"}
                mydb.update(table, params, cond_dict)

        """
        attrs_list = []
        consql = ' '
        for tmpkey, tmpvalue in attrs_dict.items():
            attrs_list.append(tmpkey + "=" + "\'" + tmpvalue + "\'")
        attrs_sql = ",".join(attrs_list)
        # print("attrs_sql:", attrs_sql)
        if cond_dict != '':
            for k, v in cond_dict.items():
                if isinstance(v, str):
                    v = "\'" + v + "\'"
                consql = consql + k + '=' + v + ' and '
        consql = consql + ' 1=1 '
        sql = "UPDATE %s SET %s where%s" % (tablename, attrs_sql, consql)
        log.info("update: {}".format(sql))
        return self.executeCommit(sql)



if __name__ == "__main__":
    oracl = OracleUtil()
    sql = "select * from FE.T_SHOP where id = '6010'"
    s = oracl.select("FE.T_MEMBER", cond_dict={"login_name": 'qiuhua.yang@ipaylinks.com'},fields=["member_code"])
    # s = oracl.select("FE.T_SHOP", cond_dict={"id": '6010'}, fields=["id", "name", "member_code"])
    # oracl.delete("FE.T_BULLETIN",{"id": "144"})
    # oracl.update("FE.T_BULLETIN", attrs_dict={"title": "yuquan"}, cond_dict={"id": "145"})
    # print(s[0][0])
    a = '0'
    b = "10"
    # oracl.update("FE.T_MEMBER", {"audi_status": "0"}, {"member_code": "%s"%a})
    # sql ="delete from FE.T_INDIVIDUAL_INFO t where t.member_code ='{0}' or t.cer_code ='{1}'".format(a,b)
    # sql = "INSERT INTO FE.T_MEMBER (MEMBER_CODE, TYPE, SERVICE_LEVEL_CODE, GREETING, STATUS, SECURITY_QUESTION, SECURITY_ANSWER, SSO_USER_ID, LOGIN_TYPE, LOGIN_NAME, LOGIN_PWD, CREATE_DATE, UPDATE_DATE, WITHDRAW_CYCLE, IS_GAME, ACTIVE_TIME, LOCK_STATUS, BUSINESS_TYPE, LOGIN_PHONE, NATION_REGION, AUDI_STATUS, APPLICANT_TYPE, RESIDENT_REGION, NICKNAME, AUDI_REMARK, ACCT_STATUS, MEMBER_LEVEL, TRACE_STATUS, CUST_MANAGER_EMAIL, CUST_MANAGER_NAME, IP, RECOMMEND_CODE, REGISTER_SOURCE, SHOP_AUTH, PRODUCT_LIST, LIGHTNING_RECEIPT_ACTIVE, USER_ID, SUPPLY_USER_ID, SUPPLY_NAME, AUDITOR, REFUSE_REASON, ENTERPRISE_UPGRADE_STATUS, PAY_PWD, RISK_LEVEL) VALUES (60000000, 0, null, null, 1, null, null, null, null, '60000000qq.com', '09a7132957b49afda6e7e2bf4dedd3b950bca173', TO_DATE('2019-06-27 20:49:43', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2019-06-27 20:49:43', 'YYYY-MM-DD HH24:MI:SS'), 0, null, null, null, 1, '18900000001', null, 0, null, null, '18900000001', null, 1, 'V', 0, null, null, '116.247.106.162', null, null, 1, null, null, null, null, null, null, null, 0, 'ef08563d0a4d8c5855e6071dc8bef3a30b5ed4a3', null)"

    # oracl.executeCommit(sql)  # 删除 FE.T_INDIVIDUAL_INFO 根据member_code
    # OracleUtil().update("FE.T_MEMBER", {"audi_status": "0"}, {"member_code": "%s" % a})
    # OracleUtil().executeCommit("delete from FE.T_INDIVIDUAL_INFO t where t.member_code ='{0}' or t.cer_code = '{1}'".format(a, b))
    oracl.orcle_close()

    oracle = OracleUtil()
    sql= "select FE.SEQ_MEMBER_MEMBER_CODE.nextval from dual"  # 获得序列your_sequence的下一个值

    member_code = oracle.executeSql(sql)
    print(member_code)