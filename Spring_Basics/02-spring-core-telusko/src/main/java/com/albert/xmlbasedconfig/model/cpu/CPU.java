package com.albert.xmlbasedconfig.model.cpu;

public abstract class CPU {
    private String name;

    public CPU(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CPU{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
