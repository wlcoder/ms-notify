package com.wl.msnotify.service;

import com.wl.msnotify.entity.SysLog;

import java.util.List;

/**
 * @author : wl
 * @Description : 操作日志 服务
 * @date : 2020/4/29 17:32
 */
public interface SysLogService {

    /* 新增日志*/
    void insertLog(SysLog sysLog);

    /*查询日志*/
    List<SysLog> findAllLog();


}
