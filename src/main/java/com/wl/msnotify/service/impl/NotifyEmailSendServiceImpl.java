package com.wl.msnotify.service.impl;

import com.wl.msnotify.aop.TimeConsumeAnnotation;
import com.wl.msnotify.entity.NotifyConfig;
import com.wl.msnotify.entity.NotifyHistory;
import com.wl.msnotify.enums.CommonEnum;
import com.wl.msnotify.mapper.NotifyHistoryMapper;
import com.wl.msnotify.service.NotifySendService;
import com.wl.msnotify.util.BaseException;
import com.wl.msnotify.util.SendEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
 * 邮件发送
 * */
@Service
@Slf4j
public class NotifyEmailSendServiceImpl implements NotifySendService {
    @Autowired
    private NotifyHistoryMapper notifyHistoryMapper;
    private String from = "1608550717@qq.com";

    @Override
    @TimeConsumeAnnotation
    public void send(NotifyConfig notifyConfig) {
        if (CommonEnum.FALSE.getValue().toString().equals(notifyConfig.getStatus())) {
            return;
        }
        //发送信息保存在历史表中
        NotifyHistory notifyHistory = NotifyHistory.builder()
                .nid(notifyConfig.getNid())
                .nname(notifyConfig.getNname())
                .sender(from)
                .receiver(notifyConfig.getEmail())
                .senddate(new Date())
                .subject(notifyConfig.getSubject())
                .status(CommonEnum.TRUE.getValue())
                .content(notifyConfig.getContent())
                .type(notifyConfig.getType())
                .build();
        try {
            log.info("开始发送消息：" + notifyConfig.getNid());
            SendEmail send = new SendEmail(notifyConfig);
            send.start();
            log.info("发送消息结束：" + notifyConfig.getNid());
            notifyHistoryMapper.insertNotifyHistory(notifyHistory);
            log.info("发送成功保存进消息历史表：" + notifyConfig.getNid());
        } catch (BaseException e) {
            notifyConfig.setStatus(CommonEnum.FALSE.getValue());
            notifyHistoryMapper.insertNotifyHistory(notifyHistory);
            log.info("发送失败保存进消息历史表:" + notifyConfig.getNid());
        }
    }

    @Override
    public String getNotifyType() {
        return "email";
    }

}
