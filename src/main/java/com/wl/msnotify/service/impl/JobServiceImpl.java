package com.wl.msnotify.service.impl;

import com.wl.msnotify.entity.JobDetails;
import com.wl.msnotify.enums.CommonEnum;
import com.wl.msnotify.mapper.JobDetailsMapper;
import com.wl.msnotify.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobDetailsMapper jobDetailsMapper;

    @Override
    public void insertJob(JobDetails jobDetails) {
        jobDetails.setStatus(CommonEnum.TRUE.getValue());
        jobDetailsMapper.insertJob(jobDetails);

    }

    @Override
    public void deleteJob(Integer id) {
        jobDetailsMapper.deleteJob(id);
    }

    @Override
    public void updateJobCron(Integer id, String cron) {
        jobDetailsMapper.updateJobCron(id, cron);
    }

    @Override
    public JobDetails findJobById(Integer id) {
        return jobDetailsMapper.findJobById(id);
    }

    @Override
    public List<JobDetails> findAllJobs() {
        return jobDetailsMapper.findAllJobs();
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        jobDetailsMapper.updateStatus(id, status);
    }
}
