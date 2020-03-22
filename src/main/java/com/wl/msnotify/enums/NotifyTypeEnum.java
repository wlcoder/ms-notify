package com.wl.msnotify.enums;

public enum NotifyTypeEnum {
    EMAIL("email"),
    SMS("sms");

    private String value;

    NotifyTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
