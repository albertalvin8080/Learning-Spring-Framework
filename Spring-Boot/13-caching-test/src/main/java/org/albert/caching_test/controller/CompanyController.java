package org.albert.caching_test.controller;

import org.albert.caching_test.entity.Company;
import org.albert.caching_test.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
//@RequiredArgsConstructor
public class CompanyController
{
    // NOTE: this class needs the default constructor because CompanyService has a cacheable method.
    @Autowired
    private CompanyService service;

    @GetMapping("/all")
    public ResponseEntity<List<Company>> findAll()
    {
//        return ResponseEntity.ok(service.findAll());
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
}
