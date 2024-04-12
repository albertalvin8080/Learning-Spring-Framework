package com.albert.annotationbasedconfig.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CalculatorController {

    @RequestMapping(path = "/sum", method = RequestMethod.GET)
    public ModelAndView sum(
            @RequestParam("a") int a,
            @RequestParam("b") int b
    ) {
        final int sum = a + b;

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sum-result");
        modelAndView.addObject("sum", sum);

        return modelAndView;
    }
}
