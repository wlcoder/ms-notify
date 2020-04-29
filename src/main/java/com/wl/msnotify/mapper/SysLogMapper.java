package com.wl.msnotify.mapper;


import com.wl.msnotify.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysLogMapper {

    //新增日志
    void insertLog(SysLog sysLog);
     /*查询日志*/
    List<SysLog> findAllLog();

}