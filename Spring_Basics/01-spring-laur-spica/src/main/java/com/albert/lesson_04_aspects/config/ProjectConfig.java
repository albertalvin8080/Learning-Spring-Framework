package com.albert.lesson_04_aspects.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.albert"})
@EnableAspectJAutoProxy
public class ProjectConfig {

}
