package com.albert.lesson_01.config;

import com.albert.lesson_01.beans.MyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.albert")
public class TestConfig {

    @Bean(name = "firstBean")
    public MyBean myBean() {
        final MyBean myBean = new MyBean();
        myBean.setText("My first bean");
        return myBean;
    }
}
