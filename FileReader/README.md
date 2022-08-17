
+ SpringBoot 项目结构
  + `config`  配置层
  + `controller`控制层，设置页面功能
  + `dao`
  + `enums`存储一些枚举类
  + `model`模型层，存储一些模型类（与数据库连接）
  + `service`服务层，提供服务接口函数
  + `utils`工具层
---
# 文件解析项目：`fa-auto-index`
## api

## config

## controller
`FaAutoCombinedBalanceController`：获取、插入CombinedBalance信息
`FaAutoExceptionController`：管控指标监控
`FaAutoIndexConfigController`：前端控制
`FaAutoIndexController`：......
`FaAutoIndexPageController`：......

## dao

## enums

## jobs

## kafka
`KafkaConsumer`：通过`Kafka`接收`JSON`形式的消息，识别对应的银行并使用对应工具类解析。
`KafkaProducer`：利用`Kafka`推送消息。

## model

## service

## utils
`CombinedBalanceExelAnalysis`：对不同银行的Excel表格进行不同的解析、处理，存入一个模型中，并进行数据入库、增删改查。
`FaAutoIndexParamUtil`：从`JSON`中读取参数并加载入一个`Map`中。
``：

---
注解
+ @RequestParam @RequestBody
+ @GetMapping @PostMapping @PutMapping
+ @Autowired
+ @XmlElement
+ @Bean

+ bootstrap.yml和application.yml区别
+ Oracle数据库

+ Java模板
+ 接口及实现
+ Kafka MQ
+ 多线程
+ ArrayList和List

---

  + Exchange接收邮件
  + 实现多发件用户
  + Excel写入

+ 文件读取
  + 类型
    + txt
    + pdf
    + excel
    + csv
    + dbf
  + 导入数据库
  + 工具类