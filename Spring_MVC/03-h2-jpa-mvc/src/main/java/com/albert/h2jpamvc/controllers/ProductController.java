package com.albert.h2jpamvc.controllers;

import lombok.RequiredArgsConstructor;
import com.albert.h2jpamvc.entities.Product;
import com.albert.h2jpamvc.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ModelAndView findAll() {
        final List<Product> productList = productService.findAll();

        final ModelAndView mv = new ModelAndView();
        mv.setViewName("products");
        mv.addObject(productList);

        return mv;
    }

    @GetMapping("/update-product/{id}")
    public ModelAndView update(@PathVariable Integer id) {
        final Product product = productService.findById(id);

        final ModelAndView mv = new ModelAndView();
        mv.setViewName("update-product");
        mv.addObject(product);

        return mv;
    }

    // for some reason, the controller is not accepting DELETE requests from the JSP page.
    // Maybe because it's not a REST Controller.
    @PostMapping("/deleteById")
    public ModelAndView deleteById(@RequestBody Map<String, Integer> payload)
    {
        final Integer id = payload.get("id");
        productService.deleteById(id);
        return findAll();
    }
}
