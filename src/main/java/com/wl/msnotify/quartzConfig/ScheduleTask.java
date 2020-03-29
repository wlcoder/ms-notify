package com.wl.msnotify.quartzConfig;

import com.wl.msnotify.entity.NotifyConfig;
import com.wl.msnotify.factory.NotifyStrategyFactory;
import com.wl.msnotify.service.NotifyConfigService;
import com.wl.msnotify.service.NotifySendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@Component
@EnableScheduling
@Slf4j
public class ScheduleTask {
    @Autowired
    private NotifySendService notifySendService;
    @Autowired
    private NotifyStrategyFactory notifyStrategyFactory;
    @Autowired
    private NotifyConfigService notifyConfigService;

    private synchronized void autoSendEmail() {
        log.info("quartz定时任务执行发送邮件.........");
        List<NotifyConfig> list = notifyConfigService.findAllNotify(null);
        for (NotifyConfig notifyConfig : list) {
            //获取通知类型
            NotifySendService notifySendService = notifyStrategyFactory.getNotifyService(notifyConfig.getType());
            //发送信息
            notifySendService.send(notifyConfig);
        }
    }
}
