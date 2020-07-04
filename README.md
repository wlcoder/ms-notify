# ms-notify
定时发送email： springboot + bootstrap + thymeleaf    
运行环境： idea ,springboot(2.2.5), redis(密码：root123456),jdk(1.8), mysql     
实现效果：  
*实现JWT登录授权，通过注解自定义需要token的请求方法  
*定时任务自主控制启动禁止恢复  
*定时发送邮件，启动时多线程执行任务  
*通过注解记录用户操作信息   

注：登录用户名：admin  密码： admin ；    
为了方便默认处理任务调度页面中的任务名称为通知配置页面的通知ID   

![image](https://github.com/wlonghui/ms-notify/blob/master/src/main/resources/static/img/msn00.PNG) 
![image](https://github.com/wlonghui/ms-notify/blob/master/src/main/resources/static/img/msn01.png)  
![image](https://github.com/wlonghui/ms-notify/blob/master/src/main/resources/static/img/msn02.png)  
![image](https://github.com/wlonghui/ms-notify/blob/master/src/main/resources/static/img/msn03.png)  
![image](https://github.com/wlonghui/ms-notify/blob/master/src/main/resources/static/img/msn04.png)  

项目结构介绍：  

  ├─com.wl.msnotify    
  │      ├─aop ：注解+aop  打印方法运行时间+记录操作日志    
  │      ├─controller：控制层        
  │      ├─entify：实体类       
  │      ├─enums：枚举类（通用枚举，通知类型枚举，统一返回模板枚举）    
  │      ├─factory：通知类型策略工厂：消息通知类型     
  │      ├─init：项目启动初始化加载通知信息到job中更新redis，开启多线程执行任务      
  │      ├─job：具体执行定时任务的方法      
  │      ├─mapper：持久化层      
  │      ├─config：quartz配置信息,web配置信息  
  │      ├─interceptor：方法请求拦截器    
  │      ├─service：服务层     
  │      ├─util：工具类（异常处理，redis连接，统一返回模板，email发送）    
  │      ├─MsNotifyApplication:项目启动类     
  │
  ├─resources   
  │      ├─generator:mybatis 反向映射     
  │      ├─mappper: mappper.xml文件信息        
  │      ├─sql: mysql数据库表信息       
  │      ├─static: 静态文件信息         
  │      ├─templates: 页面信息      
  │      ├─application.yml:项目配置信息    
  │      ├─logback.xml:日志信息     
  │      ├─quartz.properties: 定时任务相关配置    
