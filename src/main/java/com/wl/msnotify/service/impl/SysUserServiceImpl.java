package com.wl.msnotify.service.impl;

import com.wl.msnotify.entity.SysUser;
import com.wl.msnotify.mapper.SysUserMapper;
import com.wl.msnotify.service.SysUserService;
import com.wl.msnotify.util.BaseException;
import com.wl.msnotify.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author : wl
 * @Description :
 * @date : 2020/7/2 17:45
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUser(String username, String password) {
        if (Objects.isNull(username) || Objects.isNull(password)) {
            throw new BaseException("用户信息为空！");
        }
        String pwd = Md5Util.getMD5(password);
        return sysUserMapper.findUser(username, pwd);
    }
}
