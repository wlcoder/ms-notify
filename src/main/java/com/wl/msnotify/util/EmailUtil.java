package com.wl.msnotify.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
public class EmailUtil {

    @Resource
    private JavaMailSender javaMailSender;

    public void send(String emailfrom, String emailTo, String emailSubject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailfrom); // 邮件发送者
        message.setTo(emailTo);           // 邮件接受者
        message.setSubject(emailSubject); // 主题
        message.setText(text);            // 内容
        javaMailSender.send(message);
    }
}
