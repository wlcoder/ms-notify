package com.wl.msnotify.init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wl.msnotify.entity.JobDetails;
import com.wl.msnotify.enums.CommonEnum;
import com.wl.msnotify.mapper.JobDetailsMapper;
import com.wl.msnotify.quartzConfig.QuartzManager;
import com.wl.msnotify.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * 项目启动初始化
 */
@Component
@Slf4j
public class InitServer implements ApplicationRunner {
    @Autowired
    private JobDetailsMapper jobDetailsMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private QuartzManager quartzManager;

    private static final String JOB_DETAILS = "job-details";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("系统初始化执行：加载任务详情信息到redis中...");
        redisUtil.delete(JOB_DETAILS);
        if (null == redisUtil.get(JOB_DETAILS)) {
            initJobDetails();
        }
        log.info("系统初始化执行：启动所有配置的任务Job....");
        startAllJobs();
    }

    /**
     * 启动所有的job
     */
    private void startAllJobs() throws SchedulerException {
        //只允许一个线程进入操作
        synchronized (log) {
            try {
                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
                //暂停所有JOB
                scheduler.pauseJobs(GroupMatcher.anyGroup());
                if (null != set && set.size() > 0) {
                    log.info("删除从数据库中注册的所有JOB...");
                    for (JobKey jobKey : set) {
                        scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                        scheduler.deleteJob(jobKey);
                    }
                }
                if (null != redisUtil.get(JOB_DETAILS)) {
                    String jobs = redisUtil.get(JOB_DETAILS).toString();
                    List<JobDetails> joblist = JSONObject.parseArray(jobs, JobDetails.class);
                    List<JobDetails> newJoblist = new ArrayList<>();
                    if (null != joblist && joblist.size() > 0) {
                        //过滤掉禁用的任务信息
                        newJoblist.addAll(joblist.stream().filter(w -> w.getStatus() == CommonEnum.TRUE.getValue()).collect(Collectors.toList()));
                    }
                    if (null != newJoblist && newJoblist.size() > 0) {
                        log.info("从数据库中注册启用的JOB...");
                        for (JobDetails job : newJoblist) {
                            JobDataMap map = quartzManager.getJobDataMap(job);
                            JobKey jobKey = quartzManager.getJobKey(job);
                            //通过反射获取对应的类
                            Class jobClass = Class.forName(job.getJobClassName());
                            JobDetail jobDetail = quartzManager.getJobDetail(jobKey, jobClass, job.getDescription(), map);
                            if (job.getStatus() == CommonEnum.TRUE.getValue()) {
                                scheduler.scheduleJob(jobDetail, quartzManager.getTrigger(job));
                            }
                        }
                    }
                }
            } catch (SecurityException | ClassNotFoundException e) {
                log.error("初始化启动job失败。。。。");
                e.printStackTrace();
            }
        }
    }

    /*
     * 初始化时将任务详情信息放进redis中
     */
    private void initJobDetails() {
        // redisUtil.delete(JOB_DETAILS);
        List<JobDetails> list = jobDetailsMapper.findAllJobs();
        if (null != list && list.size() > 0) {
            //list对象转换为json
            String jobList = JSON.toJSON(list).toString();
            redisUtil.set(JOB_DETAILS, jobList);
        } else {
            redisUtil.set(JOB_DETAILS, null);
        }
    }
}
