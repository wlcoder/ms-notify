package com.wl.msnotify.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("hello")
    public String hello(String name) {
        return name + ",欢迎你！";
    }
}
