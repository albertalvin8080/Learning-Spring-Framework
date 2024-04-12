package com.albert.firsttest.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddController {

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        final int a = Integer.parseInt(request.getParameter("a"));
        final int b = Integer.parseInt(request.getParameter("b"));

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result.jsp");
        modelAndView.addObject("sum", a + b);

        return modelAndView;
    }
}
