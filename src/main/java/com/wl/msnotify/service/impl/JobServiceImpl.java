package com.wl.msnotify.service.impl;

import com.alibaba.fastjson.JSON;
import com.wl.msnotify.entity.JobDetails;
import com.wl.msnotify.enums.CommonEnum;
import com.wl.msnotify.mapper.JobDetailsMapper;
import com.wl.msnotify.config.QuartzManager;
import com.wl.msnotify.service.JobService;
import com.wl.msnotify.util.BaseException;
import com.wl.msnotify.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobDetailsMapper jobDetailsMapper;
    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private RedisUtil redisUtil;
    private static final String JOB_DETAILS = "job-details";

    @Override
    public void insertJob(JobDetails jobDetails) {
        if (null == jobDetails) {
            throw new BaseException("信息为空。。。");
        }
        try {
            log.info("新增job信息到jobDetails表中。。。");
            jobDetails.setStatus(CommonEnum.TRUE.getValue());
            jobDetailsMapper.insertJob(jobDetails);
            log.info("将job信息添加到定时任务中。。。");
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobDataMap map = quartzManager.getJobDataMap(jobDetails);
            JobKey jobKey = quartzManager.getJobKey(jobDetails);
            Class jobClass = Class.forName(jobDetails.getJobClassName());
            JobDetail jobDetail = quartzManager.getJobDetail(jobKey, jobClass, jobDetails.getDescription(), map);
            scheduler.scheduleJob(jobDetail, quartzManager.getTrigger(jobDetails));
            scheduler.start();
            log.info("新增定时任务成功。。。");
//            log.info("更新缓存。。。。");
//            setJobsToRedis();
        } catch (SchedulerException e) {
            log.error("新增任务:job运行失败");
        } catch (ClassNotFoundException e) {
            log.error("新增任务失败：没有找到job运行的类");
        }
    }

    @Override
    public void deleteJob(Integer id) {
        if (null == id) {
            throw new BaseException("id为空。。。");
        }
        try {
            log.info("删除定时任务中的job信息。。。");
            JobDetails jobDetails = jobDetailsMapper.findJobById(id);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = quartzManager.getJobKey(jobDetails);
            log.info("暂停job触发器运行。。。");
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.deleteJob(jobKey);
            log.info("删除jobDetails表开始。。。。");
            jobDetailsMapper.deleteJob(id);
//            log.info("更新缓存。。。。");
//            setJobsToRedis();
        } catch (SchedulerException e) {
            log.error("删除任务job失败");
        } catch (BaseException e) {
            log.error("删除jobDetails表失败");
        }
    }

    @Override
    public void updateJobCron(Integer id, String cron) {
        if (null == id || StringUtils.isEmpty(cron)) {
            throw new BaseException("id或者cron为空。。。");
        }
        try {
            log.info("修改jobDetails表数据。。。");
            jobDetailsMapper.updateJobCron(id, cron);
            log.info("修改job定时任务信息。。。");
            JobDetails job = jobDetailsMapper.findJobById(id);
            if (job.getStatus().equals(CommonEnum.TRUE.getValue())) {
                JobKey jobKey = quartzManager.getJobKey(job);
                TriggerKey triggerKey = new TriggerKey(jobKey.getName(), jobKey.getGroup());
                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                String oldCron = cronTrigger.getCronExpression();
                if (!oldCron.equalsIgnoreCase(cron)) {
                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
                    CronTrigger trigger = TriggerBuilder.newTrigger()
                            .withIdentity(jobKey.getName(), jobKey.getGroup())
                            .withSchedule(cronScheduleBuilder)
                            .usingJobData(quartzManager.getJobDataMap(job))
                            .build();
                    scheduler.rescheduleJob(triggerKey, trigger);
                }
            }
            log.info("修改cron成功：{},cron:{}", job.getJobName(), job.getJobCron());
//            log.info("更新缓存。。。。");
//            setJobsToRedis();
        } catch (SchedulerException e) {
            log.error("修改任务cron:job运行失败");
        } catch (BaseException e) {
            log.error("修改任务jobDetails表失败");
        }
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        if (null == id || null == status) {
            throw new BaseException("id或者status为空。。。");
        }
        try {
            log.info("jobDetails表修改任务详情状态。。。");
            jobDetailsMapper.updateStatus(id, status);
            log.info("修改job运行状态。。。。");
            JobDetails jobDetails = jobDetailsMapper.findJobById(id);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = quartzManager.getJobKey(jobDetails);
            if (status.equals(CommonEnum.FALSE.getValue())) {
                log.info("暂停job运行。。。");
                scheduler.pauseJob(jobKey);
                log.info("暂停job运行成功。。。");
            } else {
                log.info("恢复job运行。。。");
                scheduler.resumeJob(jobKey);
                log.info("恢复job运行成功。。。");
            }
//            log.info("更新缓存。。。。");
//            setJobsToRedis();
        } catch (BaseException | SchedulerException e) {
            log.error("job运行失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void restartJobs() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            //暂停所有JOB
            scheduler.pauseJobs(GroupMatcher.anyGroup());
            //启动所有Job
            scheduler.resumeJobs(GroupMatcher.anyGroup());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JobDetails findJobById(Integer id) {
        return jobDetailsMapper.findJobById(id);
    }

    @Override
    public List<JobDetails> findAllJobs() {
        return jobDetailsMapper.findAllJobs();
    }

    /*
     * 将job信息放入redis中
     */
    private void setJobsToRedis() {
        redisUtil.delete(JOB_DETAILS);
        List<JobDetails> list = findAllJobs();
        if (null != list && !list.isEmpty()) {
            //list对象转换为json
            String jobList = JSON.toJSON(list).toString();
            redisUtil.set(JOB_DETAILS, jobList);
        }
    }
}
