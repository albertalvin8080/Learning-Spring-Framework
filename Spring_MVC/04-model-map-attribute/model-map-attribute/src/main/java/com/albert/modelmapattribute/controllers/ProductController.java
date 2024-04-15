package com.albert.modelmapattribute.controllers;

import com.albert.modelmapattribute.entities.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/product")
public class ProductController {

    @ModelAttribute
    public void keyword(Model model) {
        model.addAttribute("keyword", "Gena");
    }

    @GetMapping("/")
    public String form() {
        return "form";
    }

    @PostMapping("/test1")
    public String test1(@ModelAttribute("product") Product product) {
        return "result";
    }

    @PostMapping("/test2")
    public String test2(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("price") BigDecimal price,
//            Model model,
            ModelMap modelMap
    ) {
//        model.addAttribute("product", new Product(id, name, price));
        modelMap.addAttribute("product", new Product(id, name, price));
        return "result";
    }
}
