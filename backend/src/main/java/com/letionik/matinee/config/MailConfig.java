package com.letionik.matinee.config;

import com.letionik.matinee.service.MailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by Bohdan Pohotilyi on 18.12.2015.
 */
@Configuration
public class MailConfig {
    private static final String MAIL_USERNAME = "mail.username";
    private static final String MAIL_PASSWORD = "mail.password";
    private static final String PROP_MAIL_SMPT_AUTH = "mail.smtp.auth";
    private static final String PROP_MAIL_SMPT_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String PROP_MAIL_SMPT_HOST = "mail.smtp.host";
    private static final String PROP_MAIL_SMPT_PORT = "mail.smtp.port";

    @Resource
    private Environment env;

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(env.getRequiredProperty(PROP_MAIL_SMPT_HOST));
        javaMailSender.setUsername(env.getRequiredProperty(MAIL_USERNAME));
        javaMailSender.setPassword(env.getRequiredProperty(MAIL_PASSWORD));

        Properties props = new Properties();
        props.put(PROP_MAIL_SMPT_AUTH, env.getRequiredProperty(PROP_MAIL_SMPT_AUTH));
        props.put(PROP_MAIL_SMPT_STARTTLS_ENABLE, env.getRequiredProperty(PROP_MAIL_SMPT_STARTTLS_ENABLE));
        props.put(PROP_MAIL_SMPT_HOST, env.getRequiredProperty(PROP_MAIL_SMPT_HOST));
        props.put(PROP_MAIL_SMPT_PORT, env.getRequiredProperty(PROP_MAIL_SMPT_PORT));
        javaMailSender.setJavaMailProperties(props);

        return javaMailSender;
    }

    @Bean
    public MailService mail() {
        return new MailService();
    }
}