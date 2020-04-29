package com.wl.msnotify.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author : wl
 * @Description :操作日志信息
 * @date : 2020/4/29 16:32
 */
@Data
public class SysLog {

    private Long id;

    private String userName;

    private String operationInfo;

    private String method;

    private String params;

    private String ip;

    private Date createDate;


}
