package com.wl.msnotify.mapper;

import com.wl.msnotify.entity.NotifyHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotifyHistoryMapper {
    //通过id查询通知历史
    NotifyHistory findNotifyById(String nid);

    //查询所有通知历史
    List<NotifyHistory> findAllNotifyHistory(@Param("nname") String nname);

    //新增通知历史
    void insertNotifyHistory(NotifyHistory notifyHistory);

}