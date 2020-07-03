package com.wl.msnotify.mapper;

import com.wl.msnotify.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper {

    SysUser findUser(@Param("username") String username, @Param("password") String password);

}