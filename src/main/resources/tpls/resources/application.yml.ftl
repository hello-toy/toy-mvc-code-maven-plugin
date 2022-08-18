# 具体密码用户名建议写在nacos的配置项，而不是提交在代码中
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://rm-bp187550968d1v97oso.mysql.rds.aliyuncs.com:3306/intelligent_assistant?useUnicode=true&useSSL=false&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true
    username: root_bg
    password: root999OK
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: Asia/Shanghai
    default-property-inclusion: non_null
logging:
  level:
    org.springframework.web: INFO
    ${basePackage}: DEBUG
  file: ./logs/${projectName}.log
  pattern:
    console: "%d{yyyy-MM/dd-HH:mm:ss.SSS} [%thread] %-5level %logger- %msg%n"
    file: "%d{yyyy/MM/dd-HH:mm:ss.SSS} [%thread] %-5level %logger- %msg%n"




