package com.albert.xmlbasedconfig.model.computer;

import com.albert.xmlbasedconfig.model.cpu.CPU;

public class Computer {
    private String name;
    private CPU cpu;

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "name='" + name + '\'' +
                ", cpu=" + cpu +
                '}';
    }

    public Computer(String name, CPU cpu) {
        this.name = name;
        this.cpu = cpu;
    }
}
