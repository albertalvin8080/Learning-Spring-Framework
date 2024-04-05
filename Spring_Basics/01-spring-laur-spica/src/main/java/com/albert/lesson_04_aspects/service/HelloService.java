package com.albert.lesson_04_aspects.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String hello(String name) {
        System.out.println("Inside Hello");
        if (false)
            throw new RuntimeException();
        return "Hello %s!!!".formatted(name);
    }
}
