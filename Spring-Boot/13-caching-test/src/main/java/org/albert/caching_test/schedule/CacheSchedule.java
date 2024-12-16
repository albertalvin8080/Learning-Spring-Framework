package org.albert.caching_test.schedule;

import org.albert.caching_test.entity.Company;
import org.albert.caching_test.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CacheSchedule
{
    @Autowired
    private CompanyService service;

    // NOTE: Even if intellisense complains about returning something, it's necessary to update the cache.
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    @CachePut("companies")
    public List<Company> cachePut()
    {
//        insert into company (company_name) values ('company 000');
        System.out.println("Inside cachePut()");
        return service.findAllNoCache();
    }

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    @CacheEvict("companies")
    public void cacheEvict()
    {
        System.out.println("Inside cacheEvict()");
    }
}
