package com.albert.lesson_03_jdbctemplate.repository;

import java.util.List;

public interface IRepository<T> {
    void save(T t);
    List<T> listAll();
}
