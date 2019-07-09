package com.qf.v1commonservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v1.api.IEmaiService;
import com.qf.v1.common.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/25
 */
@Service
public class EmailServiceImpl implements IEmaiService {

    @Value("${mail.fromAddr}")
    private String fromAddr;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public ResultBean send(String to, String subject, String text) {
        MimeMessage message=javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddr);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
            javaMailSender.send(message);
            System.out.println("发送成功");
            return new ResultBean("200","发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ResultBean("500","发送邮件失败");
    }
}
