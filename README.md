# sa2018-mdfs
基于微服务架构，设计一个分布式文件系统。

## 使用说明
- NameNode
    - 在/namenode目录下，执行`mvn spring-boot:run`，启动NameNode
    - /namenode/src/main/resources/application.properties可以配置
    块大小、副本数和端口号。（但INodeFile中的块大小需用进行手动更改）
    - 用POSTMAN对NameNode进行测试
    - 目前版本不支持新建文件夹功能，默认有根目录以及根目录下有一个/usr；目前版本只支持String文件
    - 上传内容为`zxcvbndfgbh`的"文件"x.zip
    ![][https://github.com/151220134/sa2018-mdfs/blob/master/image/%E4%B8%8A%E4%BC%A0%E6%96%87%E4%BB%B6.png]
    - 同一路径中不能有文件名相同的文件
    - 查看指定路径下的文件（不支持查看根目录）
    - 查看所有文件
    - "下载"文件
    - 删除文件
    - 删除文件夹
- DataNode
    - 在/datanode目录下，执行`mvn spring-boot:run -Dserver.port=xxxx`（多个DataNode的端口号可自行配置）启动DataNode
不提供新建和删除文件夹功能

添加文件请用fileContent，只支持字符串


#### 最后，感谢罗雯波的倾情指导，让我得以在DDL之前勉强完成这个大作业
