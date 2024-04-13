package com.albert.xmlbasedconfig;

import com.albert.xmlbasedconfig.model.computer.Computer;
import com.albert.xmlbasedconfig.model.cpu.CPU;
import com.albert.xmlbasedconfig.model.vehicle.Car;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest03Computer {
    public static void main(String[] args) {
        final ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");

        final Computer computer = context.getBean(Computer.class);
        System.out.println(computer);

        final CPU cpu = context.getBean(CPU.class);
        System.out.println(cpu);
    }
}
