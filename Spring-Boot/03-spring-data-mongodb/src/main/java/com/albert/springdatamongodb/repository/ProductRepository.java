package com.albert.springdatamongodb.repository;

import com.albert.springdatamongodb.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
