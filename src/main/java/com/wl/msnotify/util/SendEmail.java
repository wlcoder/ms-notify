package com.wl.msnotify.util;

import com.sun.mail.util.MailSSLSocketFactory;
import com.wl.msnotify.entity.NotifyConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail extends Thread {

    //邮箱
    private String from = "1608550717@qq.com";
    //用户名
    private String username = "1608550717@qq.com";
    //密码(授权码)
    private String password = "cctitlbsnklffjhd";
    //服务器地址
    private String host = "smtp.qq.com";

    private NotifyConfig notifyConfig;

    public SendEmail(NotifyConfig notifyConfig) {
        this.notifyConfig = notifyConfig;
    }

    @Override
    public void run() {
        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host", host);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");

            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
            //创建定义整个应用程序所需的环境信息的 Session 对象
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            //   session.setDebug(true);
            //通过session得到transport对象
            Transport ts = session.getTransport();
            //使用邮箱的用户名和授权码连上邮件服务器
            ts.connect(host, username, password);
            //创建邮件
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(notifyConfig.getEmail()));
            message.setSubject(notifyConfig.getSubject());
            message.setContent(notifyConfig.getContent(), "text/html;charset=UTF-8");
            message.saveChanges();
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
