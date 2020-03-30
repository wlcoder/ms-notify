package com.wl.msnotify.quartzConfig;

import com.alibaba.fastjson.JSONObject;
import com.wl.msnotify.entity.NotifyConfig;
import com.wl.msnotify.factory.NotifyStrategyFactory;
import com.wl.msnotify.service.NotifyConfigService;
import com.wl.msnotify.service.NotifySendService;
import com.wl.msnotify.util.RedisUtil;
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
    @Autowired
    private RedisUtil redisUtil;

    private synchronized void autoSendEmail() {
        log.info("quartz定时任务执行发送邮件.........");
        String configList = (String) redisUtil.get("notify-config");
        //json 数据转换为list对象
        List<NotifyConfig> list = JSONObject.parseArray(configList, NotifyConfig.class);
        for (NotifyConfig notifyConfig : list) {
            log.info("list 对象信息：" + notifyConfig.toString());
            //获取通知类型
            NotifySendService notifySendService = notifyStrategyFactory.getNotifyService(notifyConfig.getType());
            //发送信息
            notifySendService.send(notifyConfig);
        }
    }
}
