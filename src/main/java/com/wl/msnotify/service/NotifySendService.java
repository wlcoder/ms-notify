package com.wl.msnotify.service;

import com.wl.msnotify.entity.NotifyConfig;

public interface NotifySendService {

    /**
     * 发送邮件
     */
    void send(NotifyConfig notifyConfig);
}
