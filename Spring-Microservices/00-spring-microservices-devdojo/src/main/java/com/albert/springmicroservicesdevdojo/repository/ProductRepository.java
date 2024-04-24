package com.albert.springmicroservicesdevdojo.repository;

import com.albert.springmicroservicesdevdojo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>
{
}
