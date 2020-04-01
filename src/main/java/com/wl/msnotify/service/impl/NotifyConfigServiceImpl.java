package com.wl.msnotify.service.impl;

import com.wl.msnotify.entity.NotifyConfig;
import com.wl.msnotify.enums.CommonEnum;
import com.wl.msnotify.mapper.NotifyConfigMapper;
import com.wl.msnotify.service.NotifyConfigService;
import com.wl.msnotify.util.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class NotifyConfigServiceImpl implements NotifyConfigService {
    @Autowired
    private NotifyConfigMapper notifyConfigMapper;

    @Override
    public void insertNotify(NotifyConfig notifyConfig) {
        if (null == notifyConfig) {
            throw new BaseException("通知配置信息为空！");
        }
        NotifyConfig nc = notifyConfigMapper.findNotifyById(notifyConfig.getNid());
        if (null != nc) {
            throw new BaseException("已存在通知配置信息，请检查通知ID");
        }
        notifyConfig.setStatus(CommonEnum.TRUE.getValue());
        notifyConfigMapper.insertNotify(notifyConfig);
    }

    @Override
    public void deleteNotify(String nid) {
        if (StringUtils.isEmpty(nid)) {
            throw new BaseException("删除失败：id为空！");
        }
        notifyConfigMapper.deleteNotify(nid);
    }

    @Override
    public void updateNotify(NotifyConfig notifyConfig) {
        if (null == notifyConfig) {
            throw new BaseException("通知配置信息为空！");
        }
        notifyConfigMapper.updateNotify(notifyConfig);
    }

    @Override
    public NotifyConfig findNotifyById(String nid) {
        return notifyConfigMapper.findNotifyById(nid);
    }

    @Override
    public List<NotifyConfig> findAllNotify(String nname) {
        return notifyConfigMapper.findAllNotify(nname);
    }

    @Override
    public void updateStatus(String nid, Integer status) {
        if (StringUtils.isEmpty(nid) || null == status) {
            throw new BaseException("修改状态失败：id或者状态为空！");
        }
        notifyConfigMapper.updateStatus(nid, status);
    }


}
