package com.albert.lesson_01.repository;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    public void message() {
        System.out.println("Inside Product Repository");
    }
}
