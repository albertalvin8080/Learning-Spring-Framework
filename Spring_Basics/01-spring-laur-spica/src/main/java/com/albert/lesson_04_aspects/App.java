package com.albert.lesson_04_aspects;

import com.albert.lesson_04_aspects.config.ProjectConfig;
import com.albert.lesson_04_aspects.service.HelloService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        try(var context = new AnnotationConfigApplicationContext(ProjectConfig.class)) {
            final HelloService service = context.getBean(HelloService.class);
            final String returned = service.hello("Choso");
            System.out.printf("Returned string: %s%n", returned);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
