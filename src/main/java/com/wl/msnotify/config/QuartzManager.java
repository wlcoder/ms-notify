package com.wl.msnotify.config;

import com.wl.msnotify.entity.JobDetails;
import org.quartz.*;
import org.springframework.stereotype.Component;

@Component
public class QuartzManager {

    //获取JobDataMap.(Job参数对象)
    public JobDataMap getJobDataMap(JobDetails job) {
        JobDataMap map = new JobDataMap();
        map.put("name", job.getJobName());
        map.put("jobGroup", job.getJobGroup());
        map.put("cronExpression", job.getJobCron());
        map.put("jobDescription", job.getDescription());
        map.put("jobClassName", job.getJobClassName());
        map.put("status", job.getStatus());
        return map;
    }

    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    public JobDetail getJobDetail(JobKey jobKey, Class jobclass, String description, JobDataMap map) {
        return JobBuilder.newJob(jobclass)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    //获取Trigger (Job的触发器,执行规则)
    public Trigger getTrigger(JobDetails job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName(), job.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getJobCron()))
                .build();
    }

    //获取JobKey,包含Name和Group
    public JobKey getJobKey(JobDetails job) {
        return JobKey.jobKey(job.getJobName(), job.getJobGroup());
    }


}
