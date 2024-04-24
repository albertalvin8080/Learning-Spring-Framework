package com.albert.xmlbasedconfig.model.vehicle;

import com.albert.xmlbasedconfig.model.tyre.Tyre;

public class Car implements Vehicle {
    private Tyre tyre;

    public Car(Tyre tyreParameter) {
        this.tyre = tyreParameter;
    }

    @Override
    public String toString() {
        return "Car{" +
                "tyre=" + tyre +
                '}';
    }

    public Tyre getTyre() {
        return tyre;
    }

    public void setTyre(Tyre tyre) {
        this.tyre = tyre;
    }

    @Override
    public void drive() {
        System.out.println("Car driving...");
    }
}
