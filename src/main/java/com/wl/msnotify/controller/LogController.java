package com.wl.msnotify.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wl.msnotify.aop.SysLogAnnotation;
import com.wl.msnotify.aop.TimeConsumeAnnotation;
import com.wl.msnotify.entity.SysLog;
import com.wl.msnotify.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author : wl
 * @Description :
 * @date : 2020/4/29 17:38
 */

@Controller
@RequestMapping("/log")
public class LogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 查询任务详情
     */
    @SysLogAnnotation("查询日志")
    @TimeConsumeAnnotation
    @RequestMapping(value = "/queryLog")
    public String queryJob(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        Page page = PageHelper.startPage(pageNum, 5, "id desc");
        List<SysLog> list = sysLogService.findAllLog();
        PageInfo pageInfo = new PageInfo<>(page.getResult());
        model.addAttribute("pageInfo", pageInfo);
        return "sysLog/list";
    }
}
