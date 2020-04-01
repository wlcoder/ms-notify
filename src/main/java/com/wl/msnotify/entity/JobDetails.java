package com.wl.msnotify.entity;

import lombok.Data;

@Data
public class JobDetails {
    private Integer id;

    private String jobName;

    private String jobGroup;

    private String jobClassName;

    private String jobCron;

    private String description;

    private Integer status;

}