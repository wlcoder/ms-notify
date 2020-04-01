package com.wl.msnotify.mapper;


import com.wl.msnotify.entity.JobDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobDetailsMapper {

    //新增任务
    void insertJob(JobDetails jobDetails);

    //删除任务
    void deleteJob(Integer id);

    //修改任务
    void updateJobCron(@Param("id") Integer id, @Param("cron") String cron);

    //通过id查询任务
    JobDetails findJobById(Integer id);

    //查询所有任务
    List<JobDetails> findAllJobs();

    //禁用，启用任务
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);

}