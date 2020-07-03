package com.wl.msnotify.init;

import com.wl.msnotify.entity.JobDetails;
import com.wl.msnotify.config.QuartzManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;

/**
 * @author : wl
 * @Description : 多线程启动job
 * @date : 2020/7/1 14:33
 */
@Slf4j
public class JobExecute implements Runnable {

    private SchedulerFactoryBean schedulerFactoryBean;
    private QuartzManager quartzManager;
    private JobDetails jobDetails;

    public JobExecute(JobDetails jobDetails, SchedulerFactoryBean schedulerFactoryBean, QuartzManager quartzManager) {
        this.jobDetails = jobDetails;
        this.schedulerFactoryBean = schedulerFactoryBean;
        this.quartzManager = quartzManager;
    }

    @Override
    public synchronized void run() {
        log.info("当前线程为：{},运行开始时间：{}", Thread.currentThread().getName(), new Date());
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobDataMap map = quartzManager.getJobDataMap(jobDetails);
            JobKey jobKey = quartzManager.getJobKey(jobDetails);
            //通过反射获取对应的类
            Class jobClass = Class.forName(jobDetails.getJobClassName());
            JobDetail jobDetail = quartzManager.getJobDetail(jobKey, jobClass, jobDetails.getDescription(), map);
            scheduler.scheduleJob(jobDetail, quartzManager.getTrigger(jobDetails));
            scheduler.start();
        } catch (ClassNotFoundException | SchedulerException e) {
            log.error("多线程执行job失败：" + Thread.currentThread().getName());
            e.printStackTrace();
        }
        log.info("当前线程为：{},运行结束时间：{}", Thread.currentThread().getName(), new Date());
    }
}
