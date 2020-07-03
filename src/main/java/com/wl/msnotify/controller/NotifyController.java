package com.wl.msnotify.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wl.msnotify.aop.NeedToken;
import com.wl.msnotify.aop.SkipToken;
import com.wl.msnotify.aop.SysLogAnnotation;
import com.wl.msnotify.aop.TimeConsumeAnnotation;
import com.wl.msnotify.entity.NotifyConfig;
import com.wl.msnotify.entity.NotifyHistory;
import com.wl.msnotify.service.NotifyConfigService;
import com.wl.msnotify.service.NotifyHistoryService;
import com.wl.msnotify.util.BaseException;
import com.wl.msnotify.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/notify")
public class NotifyController {
    @Autowired
    private NotifyConfigService notifyConfigService;
    @Autowired
    private NotifyHistoryService notifyHistoryService;

    /**
     * 查询通知配置
     */
    @SkipToken
    @SysLogAnnotation("查询通知配置")
    @TimeConsumeAnnotation
    @RequestMapping(value = "/queryNotifyConfig")
    public String queryNotifyConfig(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, String nname) {
        Page page = PageHelper.startPage(pageNum, 5, "id desc");
        List<NotifyConfig> list = notifyConfigService.findAllNotify(nname);
        PageInfo pageInfo = new PageInfo<>(page.getResult());
        model.addAttribute("pageInfo", pageInfo);
        return "notifyConfig/list";
    }

    /**
     * 新增配置
     */
    @ResponseBody
    @NeedToken
    @SysLogAnnotation("新增配置")
    @RequestMapping(value = "/addNotifyConfig")
    public ResultUtil addNotifyConfig(@RequestBody NotifyConfig notifyConfig) {
        try {
            notifyConfigService.insertNotify(notifyConfig);
        } catch (BaseException e) {
            return ResultUtil.error().data("msg", e.getMessage()).message("新增配置失败");
        }
        return ResultUtil.ok().data("msg", "success").message("新增配置成功");
    }

    /**
     * 跳转到修改页面
     */
    @NeedToken
    @ResponseBody
    @SysLogAnnotation("跳转到修改页面")
    @RequestMapping("/toUpdateNotify")
    public ResultUtil toUpdateNotify(@RequestParam("nid") String nid) {
        NotifyConfig notifyConfig = notifyConfigService.findNotifyById(nid);
        return ResultUtil.ok().data("data", notifyConfig).message("跳转到修改页面");
    }

    /**
     * 修改通知配置
     */
    @NeedToken
    @ResponseBody
    @SysLogAnnotation("修改通知配置")
    @RequestMapping(value = "/updateNotifyConfig")
    public ResultUtil updateNotifyConfig(@RequestBody NotifyConfig notifyConfig) {
        try {
            notifyConfigService.updateNotify(notifyConfig);
        } catch (BaseException e) {
            return ResultUtil.error().data("msg", e.getMessage()).message("修改配置失败");
        }
        return ResultUtil.ok().data("msg", "success").message("修改配置成功");
    }

    /**
     * 删除通知配置
     */
    @NeedToken
    @ResponseBody
    @SysLogAnnotation("删除通知配置")
    @RequestMapping(value = "/deleteNotifyConfig")
    public ResultUtil deleteNotifyConfig(String nid) {
        try {
            notifyConfigService.deleteNotify(nid);
        } catch (BaseException e) {
            return ResultUtil.error().data("msg", e.getMessage()).message("删除配置失败");
        }
        return ResultUtil.ok().data("msg", "success").message("删除配置成功");
    }

    /**
     * 禁用 、启用
     */
    @NeedToken
    @ResponseBody
    @SysLogAnnotation("禁用 、启用 配置")
    @RequestMapping(value = "/updateStatus")
    public ResultUtil updateStatus(HttpServletRequest request, String nid, int status) {
        String config_status = (status == 1 ? "启用" : "禁用");
        try {
            notifyConfigService.updateStatus(nid, status);
        } catch (BaseException e) {
            return ResultUtil.error().data("msg", e.getMessage()).message(config_status + "配置失败");
        }
        return ResultUtil.ok().data("msg", "success").message(config_status + "配置成功");
    }

    /**
     * 查询通知历史
     */
    @SysLogAnnotation("查询通知历史")
    @TimeConsumeAnnotation
    @RequestMapping(value = "/queryNotifyHistory")
    public String queryNotifyHistory(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, String nname) {
        Page page = PageHelper.startPage(pageNum, 5, "id desc");
        List<NotifyHistory> list = notifyHistoryService.findAllNotifyHistory(nname);
        PageInfo pageInfo = new PageInfo<>(page.getResult());
        model.addAttribute("pageInfo", pageInfo);
        return "notifyHistory/list";
    }

}
