package com.albert.xmlbasedconfig;

import com.albert.xmlbasedconfig.model.tyre.Tyre;
import com.albert.xmlbasedconfig.model.vehicle.Bike;
import com.albert.xmlbasedconfig.model.vehicle.Car;
import com.albert.xmlbasedconfig.model.vehicle.Vehicle;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest01Vehicle
{
    public static void main( String[] args )
    {
        final ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");

        final Vehicle vehicle = context.getBean("car", Car.class);
        final Vehicle vehicle2 = context.getBean("bike", Bike.class);

        vehicle.drive();
        vehicle2.drive();

        final Tyre tyre = context.getBean("tyre", Tyre.class);
        System.out.println(tyre);
    }
}
