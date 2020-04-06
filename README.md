# ms-notify
定时发送email： springboot + bootstrap   
运行环境： idea ,springboot 2.2.5, redis,mysql  
项目结构：  
>src  
>>mian  
>>>java  
>>>>com.wl.msnotify  
>>>>>aop ：注解+aop  打印方法运行时间  
>>>>>controller： 控制层  
>>>>>entify： 实体类  
>>>>>enums：枚举类（通用枚举，通知类型枚举，统一返回模板枚举）  
>>>>>factory：通知类型策略工厂  
>>>>>init：项目启动初始化加载通知信息到job中更新redis  
>>>>>job：具体执行定时任务的方法  
>>>>>mapper：持久化层  
>>>>>quartzConfig：quartz配置信息  
>>>>>service：服务层  
>>>>>util：工具类（异常处理，redis连接，统一返回模板，email发送） 
>>>>>MsNotifyApplication:项目启动类  
>>>resources  
>>>>generator:mybatis 反向映射  
>>>>mappper: mappper.xml文件信息  
>>>>sql: mysql数据库表信息   
>>>>static: 静态文件信息  
>>>>templates: 页面信息  
>>>>application.yml:项目配置信息  
>>>>logback.xml:日志信息    
>>>>quartz.properties: 定时任务相关配置  


