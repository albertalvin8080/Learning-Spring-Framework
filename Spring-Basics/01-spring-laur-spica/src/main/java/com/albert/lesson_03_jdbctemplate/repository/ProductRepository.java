package com.albert.lesson_03_jdbctemplate.repository;

import com.albert.lesson_03_jdbctemplate.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository implements IRepository<Product> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO tbl_product VALUES (NULL, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice());
    }

    @Override
    public List<Product> listAll() {
        return null;
    }
}
