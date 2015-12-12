package com.letionik.matinee;

import com.letionik.matinee.config.DataConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.letionik.matinee")
@PropertySource("classpath:application.properties")
@Import({DataConfig.class})
public class MatineeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatineeApplication.class, args);
    }
}
