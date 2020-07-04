package com.wl.msnotify.controller;

import com.wl.msnotify.aop.SkipToken;
import com.wl.msnotify.entity.SysUser;
import com.wl.msnotify.service.SysUserService;
import com.wl.msnotify.util.BaseException;
import com.wl.msnotify.util.JwtUtil;
import com.wl.msnotify.util.RedisUtil;
import com.wl.msnotify.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : wl
 * @Description :
 * @date : 2020/7/2 16:46
 */
@Controller
public class Login {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    @SkipToken
    @RequestMapping("/login")
    public String toLogin() {
        System.out.println("跳转到登录页面");
        return "login";
    }

    @SkipToken
    @RequestMapping("/index")
    public String toIndex() {
        System.out.println("跳转到主页面");
        return "index";
    }

    @SkipToken
    @ResponseBody
    @RequestMapping("/loginIn")
    public ResultUtil loginIn(String username, String password) {
        try {
            SysUser user = sysUserService.findUser(username, password);
            if (null != user) {
                user.setPassword(password);
                String token = JwtUtil.generatorToken(user, 60*60);
                //token 保存在redis中
                redisUtil.set("ms_notify_token", token);
                return ResultUtil.ok().data("msg", token).message("登录成功");
            } else {
                return ResultUtil.error().data("msg", "error").message("用户不存在");
            }
        } catch (BaseException e) {
            return ResultUtil.error().data("msg", e.getMessage()).message("登陆失败");
        }
    }

}
