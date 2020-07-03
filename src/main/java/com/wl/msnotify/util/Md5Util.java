package com.wl.msnotify.util;

import org.springframework.util.DigestUtils;

public class Md5Util {

    private static final String slat = "wl*";

    public static String getMD5(String str) {
        String base = str + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    public static void main(String[] args) {
        String pwd = getMD5("admin");
        System.out.println(pwd);
    }

}
