package com.albert.h2jpamvc.controllers;

import lombok.RequiredArgsConstructor;
import com.albert.h2jpamvc.entities.Product;
import com.albert.h2jpamvc.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product")
    public ModelAndView findAll() {
        final List<Product> productList = productService.findAll();

        final ModelAndView mv = new ModelAndView();
        mv.setViewName("products");
        mv.addObject(productList);

        return mv;
    }

    @ResponseBody
    @GetMapping("/t")
    public List<Product> test() {
        final List<Product> productList = productService.findAll();

//        final ModelAndView mv = new ModelAndView();
//        mv.setViewName("products");
//        mv.addObject(productList);

        return productList;
    }
}
