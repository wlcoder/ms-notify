package com.wl.msnotify.enums;

public enum CommonEnum {
    FALSE(0),
    TRUE(1);

    private Integer value;

    CommonEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
