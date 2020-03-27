package com.wl.msnotify.quartzConfig;

import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {
    @Bean(name = "jobDetail")
    public MethodInvokingJobDetailFactoryBean createJobDetail(ScheduleTask task) {
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setName("sendEmail");
        jobDetailFactoryBean.setGroup("sendEmail");
        jobDetailFactoryBean.setConcurrent(false);
        jobDetailFactoryBean.setTargetObject(task);
        jobDetailFactoryBean.setTargetMethod("autoSendEmail");
        return jobDetailFactoryBean;
    }

    @Bean(name = "jobTrigger")
    public CronTriggerFactoryBean createExportDailyTrigger(MethodInvokingJobDetailFactoryBean jobDetail) {
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(jobDetail.getObject());
        tigger.setCronExpression("0/5 * * * * ? ");
        tigger.setName("sendEmail");
        return tigger;
    }

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger cronJobTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        bean.setOverwriteExistingJobs(true);
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        bean.setTriggers(cronJobTrigger);
        return bean;
    }
}
