package com.wl.msnotify.service;

import com.wl.msnotify.entity.NotifyHistory;

import java.util.List;

public interface NotifyHistoryService {
    //通过id查询通知历史
    NotifyHistory findNotifyById(String nid);

    //查询所有通知历史
    List<NotifyHistory> findAllNotifyHistory(String nname);

    //新增通知历史
    void insertNotifyHistory(NotifyHistory notifyHistory);
}
