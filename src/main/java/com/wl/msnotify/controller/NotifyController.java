package com.wl.msnotify.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wl.msnotify.entity.NotifyConfig;
import com.wl.msnotify.entity.NotifyHistory;
import com.wl.msnotify.enums.CommonEnum;
import com.wl.msnotify.service.NotifyConfigService;
import com.wl.msnotify.service.NotifyHistoryService;
import com.wl.msnotify.util.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notify")
public class NotifyController {
    @Autowired
    private NotifyConfigService notifyConfigService;
    @Autowired
    private NotifyHistoryService notifyHistoryService;

    /*
     * 查询通知配置
     */
    @RequestMapping(value = "/queryNotifyConfig")
    public String queryNotifyConfig(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, String nname) {
        Page page = PageHelper.startPage(pageNum, 5, "id desc");
        List<NotifyConfig> list = notifyConfigService.findAllNotify(nname);
        PageInfo pageInfo = new PageInfo<>(page.getResult());
        model.addAttribute("pageInfo", pageInfo);
        return "notifyConfig/list";
    }

    /*
     * 新增配置
     */
    @ResponseBody
    @RequestMapping(value = "/addNotifyConfig")
    public Map<String, Object> addNotifyConfig(@RequestBody NotifyConfig notifyConfig) {
        Map<String, Object> map = new HashMap<>();
        try {
            notifyConfig.setStatus(CommonEnum.TRUE.getValue());
            notifyConfigService.insertNotify(notifyConfig);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /*
     * 跳转到修改页面
     */
    @ResponseBody
    @RequestMapping("/toUpdateNotify")
    public Map toUpdateNotify(@RequestParam("nid") String nid) {
        NotifyConfig notifyConfig = notifyConfigService.findNotifyById(nid);
        Map<String, NotifyConfig> map = new HashMap<>();
        map.put("notifyConfig", notifyConfig);
        return map;
    }

    /*
     * 修改通知配置
     *
     */
    @ResponseBody
    @RequestMapping(value = "/updateNotifyConfig")
    public Map updateNotifyConfig(@RequestBody NotifyConfig notifyConfig) {
        Map<String, Object> map = new HashMap<>();
        try {
            notifyConfigService.updateNotify(notifyConfig);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
            return map;
        }
        return map;
    }

    /*
     * 删除通知配置
     */
    @ResponseBody
    @RequestMapping(value = "/deleteNotifyConfig")
    public Map<String, Object> deleteNotifyConfig(String nid) {
        Map<String, Object> map = new HashMap<>();
        try {
            notifyConfigService.deleteNotify(nid);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /*
     * 禁用 、启用
     */

    @ResponseBody
    @RequestMapping(value = "/updateStatus")
    public Map<String, Object> updateStatus(String nid, int status) {
        Map<String, Object> map = new HashMap<>();
        try {
            notifyConfigService.updateStatus(nid, status);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", "修改状态失败！");
            return map;
        }
        return map;
    }

    /*
     * 查询通知历史
     */
    @RequestMapping(value = "/queryNotifyHistory")
    public String queryNotifyHistory(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, String nname) {
        Page page = PageHelper.startPage(pageNum, 5, "id desc");
        List<NotifyHistory> list = notifyHistoryService.findAllNotifyHistory(nname);
        PageInfo pageInfo = new PageInfo<>(page.getResult());
        model.addAttribute("pageInfo", pageInfo);
        return "notifyHistory/list";
    }


}
