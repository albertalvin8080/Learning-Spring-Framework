package org.albert.caching_test.repository;

import org.albert.caching_test.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long>
{
    @Query("""
        SELECT c FROM Company c order by c.id desc
    """)
    List<Company> findAllOrderByIdDescending();
}
