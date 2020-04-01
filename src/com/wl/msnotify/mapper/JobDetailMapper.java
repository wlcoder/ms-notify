package com.wl.msnotify.mapper;

public interface JobDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobDetail record);

    int insertSelective(JobDetail record);

    JobDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JobDetail record);

    int updateByPrimaryKey(JobDetail record);
}