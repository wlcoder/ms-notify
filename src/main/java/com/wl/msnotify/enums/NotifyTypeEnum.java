package com.wl.msnotify.enums;

import lombok.Getter;

@Getter
public enum NotifyTypeEnum {
    EMAIL("email"),
    SMS("sms");

    private String value;

    NotifyTypeEnum(String value) {
        this.value = value;
    }

}
