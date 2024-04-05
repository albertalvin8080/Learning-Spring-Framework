package org.albert.webscopes.controller;

import org.albert.webscopes.service.NumberService;
import org.albert.webscopes.service.RandomNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // here Spring is injecting a proxy based in a concrete type, so you cannot use ScopedProxyMode.INTERFACES.
//    @Autowired
//    private RandomNumberService randomNumberService;

    // it works because Spring is injecting a proxy bean based on its interface, and not in a concrete type
    @Autowired
    @Qualifier("randomNumberService")
//    @Qualifier("staticNumberService")
    private NumberService numberService;

    @GetMapping("/home")
    public String indexAction(Model model) {
//        model.addAttribute("message", randomNumberService.getNumber());
        model.addAttribute("message", numberService.getNumber());
        return "index";
    }
}
