package com.wl.msnotify.init;

import com.alibaba.fastjson.JSONObject;
import com.wl.msnotify.entity.JobDetails;
import com.wl.msnotify.enums.CommonEnum;
import com.wl.msnotify.mapper.JobDetailsMapper;
import com.wl.msnotify.quartzconfig.QuartzManager;
import com.wl.msnotify.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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


    @Override
    public void run(ApplicationArguments args) {
//        log.info("系统初始化执行：加载任务详情信息到redis中...");
//        initJobDetails();
        log.info("系统初始化执行：删除触发器中所有任务....");
        deleteJobs();
        log.info("系统初始化执行：多线程执行job....");
        jobExecute();
    }

    /**
     * 初始化时将任务详情信息放进redis中
     */
    private void initJobDetails() {
        List<JobDetails> list = jobDetailsMapper.findAllJobs();
        if (null != list && !list.isEmpty()) {
            //过滤掉禁用的任务信息
            list.stream().filter(job -> job.getStatus().equals(CommonEnum.TRUE.getValue()))
                    .forEach(job -> {
                        redisUtil.set(job.getId().toString(), JSONObject.toJSON(job));
                    });
        }
    }

    /**
     * 删除jobs
     */
    private void deleteJobs() {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
            //暂停所有JOB
            scheduler.pauseJobs(GroupMatcher.anyGroup());
            if (null != set && !set.isEmpty()) {
                for (JobKey jobKey : set) {
                    scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                    scheduler.deleteJob(jobKey);
                }
            }
        } catch (SchedulerException e) {
            log.error("初始化删除job失败。。。。");
            e.printStackTrace();
        }
    }

    private void jobExecute() {
        List<JobDetails> list = jobDetailsMapper.findAllJobs();
        //自定义线程池
        Executor poolExecutor = new ThreadPoolExecutor(5, 15, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.AbortPolicy());
        if (null != list && !list.isEmpty()) {
            list.stream().filter(job -> job.getStatus().equals(CommonEnum.TRUE.getValue()))
                    .forEach(jobDetails -> {
                        poolExecutor.execute(new JobExecute(jobDetails, schedulerFactoryBean, quartzManager));
                    });
        }
    }
}
