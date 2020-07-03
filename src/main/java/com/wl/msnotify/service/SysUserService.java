package com.wl.msnotify.service;

import com.wl.msnotify.entity.SysUser;

/**
 * @author : wl
 * @Description :
 * @date : 2020/7/2 17:44
 */
public interface SysUserService {

    SysUser findUser(String username, String password);
}
