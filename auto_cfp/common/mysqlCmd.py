#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/11/30 13:41
# @Author  : Yuquan
# @Site    : 
# @File    : mysqlCmd.py
# @Software: PyCharm
import pymysql
from config import globals
class MysqlCmd(object):
    __instance = None
    def __new__(cls, *args, **kwargs):
        '''单例模式'''
        if not cls.__instance:
            cls.__instance = object.__new__(cls)
        return cls.__instance

    def __init__(self,config,open_db=None):
        '''  config = {
        'host': '127.0.0.1',
        'port': 3306,
        'user': 'root',
        'passwd': 'root',
        'charset':'utf8',
        'cursorclass':pymysql.cursors.DictCursor
        }'''
        # self.connection  = pymysql.connect(host=host, port=port, user=user, password=pwd, database=db,charset='utf8')
        # self.cursor  = self.connection.cursorsor()
        self.host = config['host']
        self.username = config['user']
        self.password = config['passwd']
        self.port = config['port']
        self.con = None
        self.cur = None
        try:
            self.con = pymysql.connect(**config)
            self.con.autocommit(1)
            # 所有的查询，都在连接 con 的一个模块 cursor 上面运行的
            self.cur = self.con.cursor()
            if open_db!=None:
                self.selectDataBase(open_db)
        except BaseException as e:
            print("DataBase connect error,please check the db config.Reason:%s"%e)


    def close(self):
        '''关闭数据库连接'''
        if self.con:
            self.con.close()
            print('Close Dateconnect')
        else:
            print("DataBase doesn't connect,close connectiong error;please check the db config.")

    def selectDataBase(self,DB_NAME):
        '''选择数据库'''
        self.con.select_db(DB_NAME)


    # 获取上个查询的结果
    def getOneData(self):
        # 取得上个查询的结果，是单个结果
        data = self.cur.fetchone()
        return data

    def executeSql(self, sql=''):
        """执行sql语句，针对读操作返回结果集
            args：sql  ：sql语句
        """
        try:
            self.cur.execute(sql)
            records = self.cur.fetchall() # [{第一行数据},{第二行数据}]
            return records
        except pymysql.Error as e:
            error = 'MySQL execute failed! ERROR (%s): %s' % (e.args[0], e.args[1])
            print(error)

    def executeCommit(self, sql=''):
        """执行数据库sql语句，针对更新,删除,事务等操作失败时回滚

        """
        try:
            self.cur.execute(sql)
            self.con.commit()
            return self.cur.rowcount
        except pymysql.Error as e:
            self.con.rollback()
            error = 'MySQL execute failed! ERROR (%s): %s' % (e.args[0], e.args[1])
            print("error:", error)
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
                consql = consql + '`' + k + '`' + '=' + '"' + v + '"' + ' and'
        consql = consql + ' 1=1 '
        if fields == "*":
            sql = 'select * from %s where ' % tablename
        else:
            if isinstance(fields, list):
                fields = ",".join(fields)
                sql = 'select %s from %s where ' % (fields, tablename)
            else:
                print("fields input error, please input list fields.")
        if order!='':
            order = ' order by %s' % order
        sql = sql + consql + order
        print('select:' + sql)
        return self.executeSql(sql)

    def insert(self, tablename, params):
        """创建数据库表
            args：
                tablename  ：表名字
                key        ：属性键
                value      ：属性值
                mydb.insert("app_01_event",{"name":'小米2发布会',"`limit`":"10","status":"0","address":"测试部","start_time":"2019-06-30","create_time":str(nows)})
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
        print('_insert:' + sql)
        print('影响行数:%s'%self.executeCommit(sql))

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
        print('insertMany:' + sql)
        try:
            # print(sql)
            rows = self.cur.executemany(sql, values)  #
            self.con.commit()
            print("影响行数:%s"%rows)
        except pymysql.Error as e:
            self.con.rollback()
            error = 'insertMany executemany failed! ERROR (%s): %s' % (e.args[0], e.args[1])
            print(error)

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
        print(sql)
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
            attrs_list.append("`" + tmpkey + "`" + "=" + "\'" + tmpvalue + "\'")
        attrs_sql = ",".join(attrs_list)
        print("attrs_sql:", attrs_sql)
        if cond_dict != '':
            for k, v in cond_dict.items():
                if isinstance(v, str):
                    v = "\'" + v + "\'"
                consql = consql + "`" + tablename + "`." + "`" + k + "`" + '=' + v + ' and '
        consql = consql + ' 1=1 '
        sql = "UPDATE %s SET %s where%s" % (tablename, attrs_sql, consql)
        print(sql)
        return self.executeCommit(sql)



if __name__ == '__main__':
    import datetime
    # 定义数据库访问参数
    config = {
        'host': '127.0.0.1',
        'port': 3306,
        'user': 'root',
        'passwd': 'password',
        'charset': 'utf8',
        'cursorclass': pymysql.cursors.DictCursor
    }
    # 初始化打开数据库连接
    mydb = MysqlCmd(config,open_db="mybatis_02")
    # mydb.selectDataBase("mybatis_02")
    print(mydb.select("app_01_event",{"name":'小米1发布会'},"start_time",["`limit`","id","name","address"]))
    nows = datetime.datetime.now()
    print(str(nows))
    # mydb.insert("app_01_event",{"name":'小米2发布会',"`limit`":"10","status":"0","address":"测试部","start_time":"2019-06-30","create_time":str(nows)})
    # values = [["小米3发布会","12","1","xxx","2019-06-30",str(nows)],["小米4发布会","12","1","xxx","2019-06-30",str(nows)]]
    # mydb.insertMany("app_01_event",["name","`limit`","status","address","start_time","create_time"],values)
    # mydb.delete("t_student",{"id":"1"})
    # mydb.update("t_student",{"name":"余全"},{"id":"2"})
    sql = "SET FOREIGN_KEY_CHECKS = 0;"
    mydb.executeSql(sql)
    mydb.close()
