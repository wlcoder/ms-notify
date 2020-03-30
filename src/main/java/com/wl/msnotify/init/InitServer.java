package com.wl.msnotify.init;

import com.alibaba.fastjson.JSON;
import com.wl.msnotify.entity.NotifyConfig;
import com.wl.msnotify.mapper.NotifyConfigMapper;
import com.wl.msnotify.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * 项目启动初始化
 */
@Component
@Slf4j
public class InitServer implements ApplicationRunner {
    @Autowired
    private NotifyConfigMapper notifyConfigMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("系统初始化开始：加载消息配置信息到redis中。。。。。。。。。");
        initNotifyConfig();
    }

    /*
     * 初始化时将消息配置信息放进redis中
     * */
    private void initNotifyConfig() {
        List<NotifyConfig> list = notifyConfigMapper.findAllNotify(null);
        if (null != list && list.size() > 0) {
            //list对象转换为json
            String configList = JSON.toJSON(list).toString();
            redisUtil.set("notify-config", configList);
        }
    }
}
