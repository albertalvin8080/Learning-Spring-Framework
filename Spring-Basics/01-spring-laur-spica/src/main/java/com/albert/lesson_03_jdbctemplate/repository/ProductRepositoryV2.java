package com.albert.lesson_03_jdbctemplate.repository;

import com.albert.lesson_03_jdbctemplate.domain.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("prV2")
public class ProductRepositoryV2 implements IRepository<Product> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepositoryV2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(@NotNull Product product) {
        String sql = "INSERT INTO tbl_product VALUES (NULL, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice());
    }

    @Override
    public List<Product> listAll() {
        final String sql = "SELECT * FROM tbl_product";
        return jdbcTemplate.query(sql,
                (resultSet, rowNumber) -> {
                    final String name = resultSet.getString("name");
                    final double price = resultSet.getDouble("price");
                    return new Product(resultSet.getLong("id"), name, price);
                });
    }
}
