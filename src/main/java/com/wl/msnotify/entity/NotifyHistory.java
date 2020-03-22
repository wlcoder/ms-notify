package com.wl.msnotify.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NotifyHistory {
    private Integer id;

    private String nid;

    private String nname;

    private String type;

    private Integer status;

    private String subject;

    private String content;

    private String sender;

    private String receiver;

    private Date senddate;

}