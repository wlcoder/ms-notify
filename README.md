# ms-notify
定时发送email： springboot + bootstrap + thymeleaf    
运行环境： idea ,springboot （2.2.5）, redis（密码：root123456）,mysql  
实现效果：通过配置定时发送邮件,同时记录操作日志信息。    
注：为了方便默认处理任务调度页面中的任务名称为通知配置页面的通知ID   
![image](https://github.com/wlonghui/ms-notify/blob/master/src/main/resources/img/msn01.png)  
![image](https://github.com/wlonghui/ms-notify/blob/master/src/main/resources/img/msn02.png)  
![image](https://github.com/wlonghui/ms-notify/blob/master/src/main/resources/img/msn03.png)  
![image](https://github.com/wlonghui/ms-notify/blob/master/src/main/resources/img/msn04.png)  

项目结构介绍：  
>src  
>>mian  
>>>java  
>>>>com.wl.msnotify  
>>>>>aop ：注解+aop  打印方法运行时间+记录操作日志   
>>>>>controller： 控制层  
>>>>>entify： 实体类  
>>>>>enums：枚举类（通用枚举，通知类型枚举，统一返回模板枚举）  
>>>>>factory：通知类型策略工厂：消息通知类型    
>>>>>init：项目启动初始化加载通知信息到job中更新redis，执行定时任务    
>>>>>job：具体执行定时任务的方法  
>>>>>mapper：持久化层  
>>>>>quartzConfig：quartz配置信息  
>>>>>service：服务层  
>>>>>util：工具类（异常处理，redis连接，统一返回模板，email发送）   
>>>>>MsNotifyApplication:项目启动类    
>resources：   
>>>>generator:mybatis 反向映射    
>>>>mappper: mappper.xml文件信息   
>>>>sql: mysql数据库表信息   
>>>>static: 静态文件信息  
>>>>templates: 页面信息  
>>>>application.yml:项目配置信息  
>>>>logback.xml:日志信息    
>>>>quartz.properties: 定时任务相关配置  


