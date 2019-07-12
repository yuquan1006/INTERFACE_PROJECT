【环境模块说明】
python3.6 + requests + unittest + cx_oracle + HTMLTestRunner + os + + json + time + redis
1.Python3.6，解释器环境
2.requests，模拟用户发送http协议get或者post类型请求
3.unittest，组织运行测试用例
4.cx_oracle，操作数据库，每次请求前，连接数据库，清除垃圾数据，初始化测试数据（如果是mysql，一样的思路，模块是pymysql）
5.HTMLTestRunner，生成html格式报告模板
6.os，获取路径
8.json，对服务器返回的字符串转换为字典，方便做断言
9.time，时间戳，生成的报告名称是：time_report.html
10.redis，操作redis增加短信验证码，模拟获取短信验证码

【框架目录结构介绍】
run_all_case: 主程序入口
config: 配置文件
common: 公共方法
reprot: 测试报告
mockserver：测试用例需要用到的mock服务
case: 测试用例（数据文件），
README.txt: 说明文件