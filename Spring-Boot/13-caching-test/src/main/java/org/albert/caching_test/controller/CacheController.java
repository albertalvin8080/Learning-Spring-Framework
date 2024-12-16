package org.albert.caching_test.controller;

import org.albert.caching_test.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class CacheController
{
    @Autowired
    private CacheService cacheService;

    @PostMapping("/evict")
    public ResponseEntity<Void> evict(@RequestParam("cacheName") String cacheName)
    {
        cacheService.evict(cacheName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
