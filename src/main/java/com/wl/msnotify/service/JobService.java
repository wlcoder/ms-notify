package com.wl.msnotify.service;

import com.wl.msnotify.entity.JobDetails;

import java.util.List;

public interface JobService {
    //新增任务
    void insertJob(JobDetails jobDetails);

    //删除任务
    void deleteJob(Integer id);

    //修改任务
    void updateJobCron(Integer id, String cron);

    //通过id查询任务
    JobDetails findJobById(Integer id);

    //查询所有任务
    List<JobDetails> findAllJobs();

    //禁用，启用任务
    void updateStatus(Integer id, Integer status);

    //重启任务
    void restartJobs();

}
