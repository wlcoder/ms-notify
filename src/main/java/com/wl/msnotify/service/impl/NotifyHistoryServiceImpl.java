package com.wl.msnotify.service.impl;

import com.wl.msnotify.entity.NotifyHistory;
import com.wl.msnotify.mapper.NotifyHistoryMapper;
import com.wl.msnotify.service.NotifyHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotifyHistoryServiceImpl implements NotifyHistoryService {
    @Autowired
    private NotifyHistoryMapper notifyHistoryMapper;

    @Override
    public NotifyHistory findNotifyById(String nid) {
        return notifyHistoryMapper.findNotifyById(nid);
    }

    @Override
    public List<NotifyHistory> findAllNotifyHistory(String nname) {
        return notifyHistoryMapper.findAllNotifyHistory(nname);
    }

    @Override
    public void insertNotifyHistory(NotifyHistory notifyHistory) {
        notifyHistoryMapper.insertNotifyHistory(notifyHistory);
    }
}
