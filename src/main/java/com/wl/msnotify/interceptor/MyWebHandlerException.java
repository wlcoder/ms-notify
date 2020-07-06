package com.wl.msnotify.interceptor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author : wl
 * @Description : 拦截异常处理
 * @date : 2020/7/5 15:30
 */
@Slf4j
public class MyWebHandlerException implements HandlerExceptionResolver {
    @SneakyThrows
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.info("请求出现异常：" + e.getMessage());
        e.printStackTrace();
//        return new ModelAndView("redirect:/login");
        ModelAndView modelAndView = new ModelAndView();
        String type = httpServletRequest.getHeader("X-Requested-With");
        if (Objects.equals(type, "XMLHttpRequest")) {
            //是ajax请求
            httpServletResponse.setStatus(666);
            httpServletResponse.setContentType("text/javascript; charset=utf-8");
            httpServletResponse.getWriter().write(e.getMessage());
            return modelAndView;
        } else {
            modelAndView.setViewName("/login");
            return modelAndView;
        }
    }

}
