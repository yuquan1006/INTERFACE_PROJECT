# jmeter项目结构
```
apache-ant-1.9.14 【是ant的Binaries 构建后代码】
apache-jmeter-5.0 【为jmeter的Source 源代码】
properties 【是jmeter项目的properties配置】
testplan 【是jmeter项目的jmx脚本 测试计划】
testlib 【是jmeter项目依赖的jar包】
testdata 【是jmeter项目的测试数据 一般是csv文件】
testreport 【是jmeter项目的测试报告】
build.xml 【是ant的构建xml文件】
```

## 运行测试计划并输出测试报告
进入jmeter项目build.xml文件所在目录，cmd模式下，执行ant命令