package com.albert.lywaytesting.repository;

import com.albert.lywaytesting.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>
{
}
