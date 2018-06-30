# sa2018-mdfs
基于微服务架构，设计一个分布式文件系统。

## 使用说明
- NameNode直接使用mvn spring-boot:run启动，块大小、副本数和端口号都在application.properties文件中配置。DataNode使用mvn spring-boot:run -Dserver.port=xxxx启动，多个DataNode需要使用不同的端口号。
不提供新建和删除文件夹功能

添加文件请用fileContent，只支持字符串


### 感谢罗雯波的倾情指导，让我得以在DDL之前勉强完成这个大作业
