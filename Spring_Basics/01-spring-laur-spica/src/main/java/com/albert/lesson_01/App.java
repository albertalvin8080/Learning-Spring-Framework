package com.albert.lesson_01;

import com.albert.lesson_01.beans.MyBean;
import com.albert.lesson_01.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class App {
    public static void main(String[] args) {
        try(var context = new AnnotationConfigApplicationContext(App.class)) {
//            System.out.println(context.getBean(MyBean.class).getText());
            System.out.println(context.getBean("firstBean", MyBean.class).getText());
            System.out.println(context.getBean("secondBean", MyBean.class).getText());
            context.getBean(ProductService.class).callRepository();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean(name = "secondBean")
    public MyBean myBean() {
        final MyBean myBean = new MyBean();
        myBean.setText("My second bean");
        return myBean;
    }
}
