package com.wl.msnotify.job;

import com.wl.msnotify.entity.NotifyConfig;
import com.wl.msnotify.enums.CommonEnum;
import com.wl.msnotify.factory.NotifyStrategyFactory;
import com.wl.msnotify.service.NotifyConfigService;
import com.wl.msnotify.service.NotifySendService;
import com.wl.msnotify.util.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DisallowConcurrentExecution
public class EmailJob implements Job {
    @Autowired
    private NotifyConfigService notifyConfigService;
    @Autowired
    private NotifyStrategyFactory notifyStrategyFactory;


    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        String jobName = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("name");
        log.info("job运行打印。。。。。。。。。。" + jobName);
        //现在默认处理为jobName 和消息通知Id 一致
        try {
            log.info("开始发送email。。。");
            NotifyConfig notifyConfig = notifyConfigService.findNotifyById(jobName);
            if (null != notifyConfig && notifyConfig.getStatus().equals(CommonEnum.TRUE.getValue())) {
                //获取通知类型
                NotifySendService notifySendService = notifyStrategyFactory.buildService(notifyConfig.getType());
                //发送信息
                notifySendService.send(notifyConfig);
            }
        } catch (BaseException e) {
            log.info("发送消息失败。。。");
        }
    }

}
