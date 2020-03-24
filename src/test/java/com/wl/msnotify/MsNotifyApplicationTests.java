package com.wl.msnotify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;

@SpringBootTest
class MsNotifyApplicationTests {

    @Test
    void contextLoads() {
        String version = SpringVersion.getVersion();
        String version1 = SpringBootVersion.getVersion();
        System.out.println("spring版本：" + version + "springboot版本：" + version1);
    }

}
