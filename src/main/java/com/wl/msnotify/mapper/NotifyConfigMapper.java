package com.wl.msnotify.mapper;

import com.wl.msnotify.entity.NotifyConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotifyConfigMapper {
    //新增通知
    void insertNotify(NotifyConfig notifyConfig);

    //删除通知
    void deleteNotify(String nid);

    //修改通知
    void updateNotify(NotifyConfig notifyConfig);

    //通过id查询
    NotifyConfig findNotifyById(String nid);

    //查询所有通知
    List<NotifyConfig> findAllNotify(@Param("nname") String nname);

    //禁用，启用
    void updateStatus(@Param("nid") String nid, @Param("status") Integer status);

}