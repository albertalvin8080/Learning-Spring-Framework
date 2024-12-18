package org.albert.redisdatabase.repository;

import lombok.RequiredArgsConstructor;
import org.albert.redisdatabase.entity.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepository
{
    /*
     * 127.0.0.1:6379> KEYS *
     * 127.0.0.1:6379> HGETALL Product
     * 127.0.0.1:6379> HGET Product <product_id>
     * */
    // This serves as a logical grouping key for storing and retrieving products in Redis.
    // Products are stored as a hash (hashmap-like structure) under the key "Product".
    public static final String HASH_KEY = "Product";

    private final RedisTemplate<String, Object> redisTemplate;

    public ProductRepository(RedisTemplate<String, Object> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    public List<Product> findAll()
    {
        /*
         * The redisTemplate.opsForHash() method in Spring Data Redis provides a set of operations
         * for interacting with Redis hashes. It allows you to perform various operations on Redis
         * hash data structures, such as storing, retrieving, and manipulating individual fields
         * within a hash.
         * */
        return redisTemplate.opsForHash().values(HASH_KEY)
                .stream()
                .filter(obj -> obj instanceof Product)
                .map(obj -> (Product) obj)
                .collect(Collectors.toList());
    }

    public Product get(String id)
    {
        return (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    public void save(Product product)
    {
        // Generating ID
        Long productId = redisTemplate.opsForValue().increment("Product:ID");
        product.setId(productId.toString());
        redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
    }

    public void delete(String id)
    {
        redisTemplate.opsForHash().delete(HASH_KEY, id);
    }
}
