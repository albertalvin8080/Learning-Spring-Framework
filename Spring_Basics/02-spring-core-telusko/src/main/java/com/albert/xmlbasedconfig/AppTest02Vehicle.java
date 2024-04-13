package com.albert.xmlbasedconfig;

import com.albert.xmlbasedconfig.model.vehicle.Car;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest02Vehicle {
    public static void main(String[] args) {
        final ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");

        final Car car = context.getBean("car", Car.class);
        System.out.println(car);
    }
}
