package com.wl.msnotify.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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