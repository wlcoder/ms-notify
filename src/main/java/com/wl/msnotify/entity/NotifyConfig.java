package com.wl.msnotify.entity;

import lombok.Data;

@Data
public class NotifyConfig {
    private Integer id;

    private String nid;

    private String nname;

    private String type;

    private String subject;

    private String content;

    private Integer status;

    private String cron;

    private String email;

}