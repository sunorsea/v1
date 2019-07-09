package com.qf.tempemail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TempEmailApplicationTests {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.fromAddr}")
    private String fromAddr;

    @Test
    public void contextLoads() {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(fromAddr);
        mail.setTo(fromAddr);
        mail.setSubject("突然想发个邮件");
        mail.setText("就是想发邮件了");
        javaMailSender.send(mail);
        System.out.println("发送邮件成功");

    }

    @Test
    public void htmlTest() {
        MimeMessage message=javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddr);
            helper.setTo(fromAddr);
            helper.setSubject("想你了");
            helper.setText("就是想你了<a href='https://www.baidu.com'>地址</a>",true);
            javaMailSender.send(message);
            System.out.println("发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void templateTest() {
        MimeMessage message=javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddr);
            helper.setTo(fromAddr);
            helper.setSubject("想你了");
            Context context = new Context();
            context.setVariable("username","嘻嘻");
            String text = templateEngine.process("jihuo.html", context);

//            helper.setText("就是想你了<a href='https://www.baidu.com'>地址</a>",true);
            helper.setText(text,true);
            javaMailSender.send(message);
            System.out.println("发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
