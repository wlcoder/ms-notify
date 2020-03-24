package com.wl.msnotify.service.impl;

import com.wl.msnotify.entity.NotifyConfig;
import com.wl.msnotify.service.NotifySendService;
import org.springframework.stereotype.Service;

/**
 * @desc 短信功能
 **/
@Service("sms")
public class NotifySmsSendServiceImpl implements NotifySendService {

    private final static String ACCOUNT_COLUMN = "FMOBILE";

    @Override
    public void send(NotifyConfig notifyConfig) {
        throw new RuntimeException("未提供短信功能");
    }
}
