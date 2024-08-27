package org.albert.standardexpressions.controller;

import org.albert.standardexpressions.entity.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController
{
    @GetMapping(path = "/switch-case")
    public String switchCase(ModelMap modelMap)
    {
        User user = User.builder().name("Mohg").email("mohg@gmail.com").password("1234").role("USER").build();
        modelMap.addAttribute("user", user);
        return "switch-case";
    }

    @GetMapping(path = "/register")
    public String registerUser(Model model)
    {
        // Empty user
        model.addAttribute("user", new User());
        model.addAttribute("genders", new String[]{"MALE", "FEMALE"});
        return "register";
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String showUser(
            Model model,
            @ModelAttribute("user") User user
    )
    {
        model.addAttribute("user", user);
        return "switch-case";
    }
}
