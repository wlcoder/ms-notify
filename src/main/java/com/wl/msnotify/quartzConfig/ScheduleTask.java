package com.wl.msnotify.quartzConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Configuration
@Component
@EnableScheduling
@Slf4j
public class ScheduleTask {

    private synchronized void autoSendEmail() {
        log.info("测试quartz.........");
    }

}
