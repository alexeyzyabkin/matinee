package com.letionik.matinee.config;

import com.letionik.matinee.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by Bohdan Pohotilyi on 18.12.2015.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class MailConfig {
    private static final Logger log = LoggerFactory.getLogger(MailConfig.class);

    public static final String MAIL_USERNAME = "mail.username";
    public static final String MAIL_PASSWORD = "mail.password";
    public static final String PROP_MAIL_SMPT_AUTH = "mail.smtp.auth";
    public static final String PROP_MAIL_SMPT_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String PROP_MAIL_SMPT_HOST = "mail.smtp.host";
    public static final String PROP_MAIL_SMPT_PORT = "mail.smtp.port";

    @Resource
    private Environment env;

    @Bean
    public JavaMailSenderImpl mailSender(){
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
    public MailService mail(){
        return new MailService();
    }
}
