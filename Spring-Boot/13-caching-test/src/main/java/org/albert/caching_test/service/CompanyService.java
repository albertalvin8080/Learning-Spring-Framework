package org.albert.caching_test.service;

import org.albert.caching_test.entity.Company;
import org.albert.caching_test.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class CompanyService
{
    // NOTE: this class needs the default constructor because CompanyService has a cacheable method.
    @Autowired
    private CompanyRepository repository;

    @Cacheable("companies")
    public List<Company> findAll()
    {
//        return repository.findAll();
        System.out.println("Inside service.findAll");
        return findAllNoCache();
    }

    public List<Company> findAllNoCache()
    {
        System.out.println("Inside service.findAllNoCache");
        return repository.findAllOrderByIdDescending();
    }

    @Cacheable(value = "company", key = "#id")
    public Company findById(Long id)
    {
        return repository.findById(id).orElse(null);
    }

    // Updates/saves the cache entry with the same id as #company.id
    @CachePut(value = "company", key = "#company.id")
    public Company save(Company company)
    {
        return repository.save(company);
    }

    // Removes the cache entry with the id #id
    @CacheEvict(value = "company", key = "#id")
    public void delete(Long id)
    {
        repository.deleteById(id);
    }
}
