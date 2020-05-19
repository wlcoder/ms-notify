package com.wl.msnotify.factory;

import com.wl.msnotify.service.NotifySendService;
import com.wl.msnotify.util.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知类型策略工厂
 **/
@Service
public class NotifyStrategyFactory {

    private Map<String, NotifySendService> notifySendServiceMap;
    @Autowired
    private List<NotifySendService> notifySendServices;

    @PostConstruct
    private void init() {
        if (CollectionUtils.isEmpty(notifySendServices)) {
            return;
        }
        notifySendServiceMap = new HashMap<String, NotifySendService>(notifySendServices.size());
        for (NotifySendService ns : notifySendServices) {
            String nstype = ns.getNotifyType();
            if (notifySendServiceMap.get(nstype) != null) {
                throw new BaseException("同一个消息渠道只能有一个实现方式");
            }
            notifySendServiceMap.put(nstype, ns);
        }
    }

    public NotifySendService buildService(String type) {
        return notifySendServiceMap.get(type);
    }
}
