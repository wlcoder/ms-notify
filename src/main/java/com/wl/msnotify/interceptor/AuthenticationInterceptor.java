package com.wl.msnotify.interceptor;

import com.wl.msnotify.aop.NeedToken;
import com.wl.msnotify.aop.SkipToken;
import com.wl.msnotify.entity.SysUser;
import com.wl.msnotify.service.SysUserService;
import com.wl.msnotify.util.BaseException;
import com.wl.msnotify.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author : wl
 * @Description :方法请求拦截
 * @date : 2020/7/3 11:47
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private SysUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("token");
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查有没有跳过token的注解
        if (method.isAnnotationPresent(SkipToken.class)) {
            SkipToken skipToken = method.getAnnotation(SkipToken.class);
            if (skipToken.required()) {
                log.info("该请求无须token验证。。。");
                return true;
            }
        }
        //检查有没有需要token的注解
        try {
            if (method.isAnnotationPresent(NeedToken.class)) {
                NeedToken needToken = method.getAnnotation(NeedToken.class);
                if (needToken.required()) {
                    log.info("该请求需要token验证。。。");
                    if (Objects.isNull(token)) {
                        throw new BaseException("无token，请重新登录");
                    }
                    SysUser sysUser = JwtUtil.getTokenInfo(token);
                    SysUser user = userService.findUser(sysUser.getUsername(), sysUser.getPassword());
                    if (Objects.isNull(user)) {
                        throw new BaseException("用户不存在，请重新登录");
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            httpServletResponse.sendRedirect("/login");
        } catch (BaseException e) {
            e.printStackTrace();
            //token发生异常 返回登录页面
            httpServletResponse.sendRedirect("/login");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
