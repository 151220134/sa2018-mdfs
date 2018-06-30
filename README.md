# sa2018-mdfs
基于微服务架构，设计一个分布式文件系统。

## 实现功能
- 基于Spring Boot实现NameNode和DataNode两个服务，在Spring Cloud微服务平台上运行一个NameNode实例和多个DataNode实例（无需考虑NameNode单点失效问题）
- NameNode提供REST风格接口与用户交互，实现用户文件上传、下载、删除，DataNode不与用户直接交互（无需考虑NameNode的IO瓶颈问题）
- NameNode将用户上传文件文件拆为固定大小的存储块，分散存储在各个DataNode上，每个块保存若干副本。块大小和副本数可通过系统参数配置。
- DataNode服务可弹性扩展，每次启动一个DataNode服务NameNode可发现并将其纳入整个系统

## 使用说明
- NameNode
    - 在/namenode目录下，执行`mvn spring-boot:run`，启动NameNode
    - /namenode/src/main/resources/application.properties可以配置
    块大小、副本数和端口号。（但INodeFile中的块大小需用进行手动更改）
    - 用POSTMAN对NameNode进行测试
    - 目前版本不支持新建文件夹功能，默认有根目录以及根目录下有一个/usr；目前版本只支持String文件
    - 上传内容为`zxcvbndfgbh`的"文件"x.zip
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/%E4%B8%8A%E4%BC%A0%E6%96%87%E4%BB%B6.png)
    - 同一路径中不能有文件名相同的文件
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/%E5%90%8C%E5%90%8D%E6%96%87%E4%BB%B6.png)
    - 查看指定路径下的文件（不支持查看根目录）
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/%E6%9F%A5%E7%9C%8B%E6%8C%87%E5%AE%9A%E8%B7%AF%E5%BE%84%E4%B8%8B%E7%9A%84%E6%96%87%E4%BB%B6.png)
    - 查看所有文件
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/%E6%9F%A5%E7%9C%8B%E6%89%80%E6%9C%89%E6%96%87%E4%BB%B6.png)
    - "下载"文件
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/%E4%B8%8B%E8%BD%BD%E6%96%87%E4%BB%B6.png)
    - 删除文件
    
    删除前
    
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/%E5%88%A0%E9%99%A4%E6%96%87%E4%BB%B6.png)
    
    删除后
    
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/%E5%88%A0%E9%99%A4%E6%96%87%E4%BB%B6%E5%90%8E.png)
    - 删除文件夹
    
    删除前
    
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/%E5%88%A0%E9%99%A4%E6%96%87%E4%BB%B6%E5%A4%B9.png)
    
    删除后
    
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/%E5%88%A0%E9%99%A4%E6%96%87%E4%BB%B6%E5%A4%B9%E5%90%8E.png)
- DataNode
    - 在/datanode目录下，执行`mvn spring-boot:run -Dserver.port=xxxx`（多个DataNode的端口号可自行配置）启动DataNode
不提供新建和删除文件夹功能
    - 分布式
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/datanode1.0.png)
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/datanode1.1.png)
    ![](https://github.com/151220134/sa2018-mdfs/blob/master/image/datanode1.2.png)

#### 最后，感谢罗雯波的倾情指导，让我得以在DDL之前勉强完成这个大作业
