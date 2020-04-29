package com.wl.msnotify.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wl.msnotify.aop.SysLogAnnotation;
import com.wl.msnotify.aop.TimeConsumeAnnotation;
import com.wl.msnotify.entity.JobDetails;
import com.wl.msnotify.enums.CommonEnum;
import com.wl.msnotify.service.JobService;
import com.wl.msnotify.util.BaseException;
import com.wl.msnotify.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private JobService jobService;

    /**
     * 查询任务详情
     */
    @SysLogAnnotation("查询任务详情")
    @TimeConsumeAnnotation
    @RequestMapping(value = "/queryJob")
    public String queryJob(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        Page page = PageHelper.startPage(pageNum, 5, "id desc");
        List<JobDetails> list = jobService.findAllJobs();
        PageInfo pageInfo = new PageInfo<>(page.getResult());
        model.addAttribute("pageInfo", pageInfo);
        return "jobDetail/list";
    }

    /**
     * 新增任务
     */
    @ResponseBody
    @SysLogAnnotation("新增任务")
    @TimeConsumeAnnotation
    @RequestMapping(value = "/addJob")
    public ResultUtil addJob(@RequestBody JobDetails jobDetails) {
        try {
            jobService.insertJob(jobDetails);
        } catch (BaseException e) {
            return ResultUtil.error().data("msg", e.getMessage()).message("新增任务失败");
        }
        return ResultUtil.ok().data("msg", "success").message("新增任务成功");
    }


    /**
     * 修改任务cron
     */
    @ResponseBody
    @SysLogAnnotation("修改任务")
    @TimeConsumeAnnotation
    @RequestMapping(value = "/updateJobCron")
    public ResultUtil updateJobCron(Integer id, String cron) {
        try {
            jobService.updateJobCron(id, cron);
        } catch (BaseException e) {
            return ResultUtil.error().data("msg", e.getMessage()).message("修改任务cron失败");
        }
        return ResultUtil.ok().data("msg", "success").message("修改任务cron成功");
    }

    /**
     * 删除任务
     */
    @ResponseBody
    @SysLogAnnotation("删除任务")
    @TimeConsumeAnnotation
    @RequestMapping(value = "/deleteJob")
    public ResultUtil deleteJob(Integer id) {
        try {
            jobService.deleteJob(id);
        } catch (BaseException e) {
            return ResultUtil.error().data("msg", e.getMessage()).message("删除任务失败");
        }
        return ResultUtil.ok().data("msg", "success").message("删除任务成功");
    }

    /**
     * 恢复or暂停 任务
     */
    @ResponseBody
    @SysLogAnnotation("恢复暂停任务")
    @TimeConsumeAnnotation
    @RequestMapping(value = "/updateJobStatus")
    public ResultUtil updateJobStatus(Integer id, Integer status) {
        String config_status = (status.equals(CommonEnum.TRUE.getValue()) ? "恢复" : "暂停");
        try {
            jobService.updateStatus(id, status);
        } catch (BaseException e) {
            log.error("job运行失败：" + e.getMessage());
            return ResultUtil.error().data("msg", e.getMessage()).message(config_status + "任务失败");
        }
        return ResultUtil.ok().data("msg", "success").message(config_status + "任务成功");
    }

    @ResponseBody
    @SysLogAnnotation("重启任务")
    @RequestMapping("/restartJobs")
    public ResultUtil restartJobs() {
        try {
            jobService.restartJobs();
        } catch (BaseException e) {
            log.error("job运行失败：" + e.getMessage());
            return ResultUtil.error().data("msg", e.getMessage()).message("重启任务失败");
        }
        return ResultUtil.ok().data("msg", "success").message("重启任务成功");
    }

}
