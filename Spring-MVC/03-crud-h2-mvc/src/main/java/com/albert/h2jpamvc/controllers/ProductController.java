package com.albert.h2jpamvc.controllers;

import lombok.RequiredArgsConstructor;
import com.albert.h2jpamvc.entities.Product;
import com.albert.h2jpamvc.services.ProductService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    /*
    * WARNING: you can use two approaches for redirecting inside the same controller:
    *   1. using the full path           -> "redirect:/product/all"
    *   2. using the inside mapping only -> "redirect:all"
    *
    * Note that when using the full path, you must provide the first forward slash '/'.
    * */

    private final ProductService productService;

    @GetMapping("/all")
    public ModelAndView findAll() {
        final List<Product> productList = productService.findAll();

        final ModelAndView mv = new ModelAndView();
        mv.setViewName("products");
        mv.addObject(productList);

        return mv;
    }

    @GetMapping("/insert-form")
    public String toInsertForm() {
        return "insert-form";
    }

    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String insert(Product product) {
        productService.save(product);
        return "redirect:all";
    }

    @GetMapping("/update-form/{id}")
    public ModelAndView toUpdateForm(@PathVariable Integer id) {
        final Product product = productService.findById(id);

        final ModelAndView mv = new ModelAndView();
        mv.setViewName("update-form");
        mv.addObject(product);

        return mv;
    }

    // NOTE: Spring doesn't understand 'application/x-www-form-urlencoded' as @RequestBody
    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String update(Product product) {
        productService.save(product);
        return "redirect:all"; // DO NOT call findAll() directly here because it will cause some weird stuff to occur, like the same product being inserted with each reload due to caching and forwarding data.
    }

    @DeleteMapping("/deleteById")
    public String deleteById(@RequestBody Map<String, Integer> payload)
    {
        final Integer id = payload.get("id");
        productService.deleteById(id);
        return "redirect:all";
    }
}
