package com.albert.learningmore.repository;

import com.albert.learningmore.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProductRepositoryJDBC {
    private JdbcTemplate jdbcTemplate;

    public void save(Product product) {
        String sql = "insert into product values (null, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice());
    }
}
