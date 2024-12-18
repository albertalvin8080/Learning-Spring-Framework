package org.albert.redisdatabase.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.albert.redisdatabase.entity.Product;
import org.albert.redisdatabase.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController
{
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> findAll()
    {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") String id)
    {
        return ResponseEntity.ok(productRepository.get(id));
    }

    @PostMapping("/")
    public ResponseEntity<Void> save(@RequestBody Product product)
    {
        productRepository.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id)
    {
        productRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
