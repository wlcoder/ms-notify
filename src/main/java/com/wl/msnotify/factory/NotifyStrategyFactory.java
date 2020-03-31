package com.wl.msnotify.factory;

import com.wl.msnotify.service.NotifySendService;
import com.wl.msnotify.util.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通知类型策略工厂
 **/
@Service
public class NotifyStrategyFactory {

    @Autowired
    Map<String, NotifySendService> notifySendServices = new ConcurrentHashMap<>();

    public NotifySendService getNotifyService(String component) {
        NotifySendService notifySendService = notifySendServices.get(component);
        if (notifySendService == null) {
            throw new BaseException("策略模式没找到对应实现类");
        }
        return notifySendService;
    }
}
