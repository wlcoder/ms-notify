package com.wl.msnotify.service.impl;

import com.wl.msnotify.entity.SysLog;
import com.wl.msnotify.mapper.SysLogMapper;
import com.wl.msnotify.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : wl
 * @Description :
 * @date : 2020/4/29 17:36
 */
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void insertLog(SysLog sysLog) {
        sysLogMapper.insertLog(sysLog);

    }

    @Override
    public List<SysLog> findAllLog() {
        return sysLogMapper.findAllLog();
    }

}
