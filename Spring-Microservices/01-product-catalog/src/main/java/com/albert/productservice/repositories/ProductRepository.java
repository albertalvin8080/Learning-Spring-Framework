package com.albert.productservice.repositories;

import com.albert.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>
{
}
