package org.albert.caching_test.service;

import org.albert.caching_test.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CacheService
{
    @Autowired
    private CacheManager cacheManager;

    public void evict(String cacheName)
    {
        Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
    }
}
