package org.albert.caching_test.controller;

import org.albert.caching_test.entity.Company;
import org.albert.caching_test.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
//@RequiredArgsConstructor
public class CompanyController
{
    // NOTE: This class needs the default constructor because we're using @EnableCaching
    @Autowired
    private CompanyService service;

    @GetMapping("/all")
    public ResponseEntity<List<Company>> findAll()
    {
//        return ResponseEntity.ok(service.findAll());
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id)
    {
        var c = service.findById(id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<Company> save(@RequestBody Company company)
    {
        var created = service.save(company);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id)
    {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
