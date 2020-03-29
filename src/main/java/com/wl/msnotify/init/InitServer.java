package com.wl.msnotify.init;

import com.wl.msnotify.entity.NotifyConfig;
import com.wl.msnotify.mapper.NotifyConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * 项目启动初始化
 * */
@Component
public class InitServer implements ApplicationRunner {
    @Autowired
    private NotifyConfigMapper notifyConfigMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initEmailJob();
    }

    private void initEmailJob() {
        List<NotifyConfig> list = notifyConfigMapper.findAllNotify(null);
        for (NotifyConfig notifyConfig : list) {
            addJob(notifyConfig);
        }
    }

    /**
     * 添加到定时任务中
     *
     * @param notifyConfig
     */
    private void addJob(NotifyConfig notifyConfig) {

    }

}
