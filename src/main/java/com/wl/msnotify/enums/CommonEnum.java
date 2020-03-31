package com.wl.msnotify.enums;

import lombok.Getter;

@Getter
public enum CommonEnum {
    FALSE(0),
    TRUE(1);

    private Integer value;

    CommonEnum(Integer value) {
        this.value = value;
    }

}
