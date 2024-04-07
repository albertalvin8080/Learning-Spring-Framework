package org.albert.integrationtestscustommockuser.controllers;

import org.albert.integrationtestscustommockuser.security.CustomAuthentication;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @PreAuthorize("hasAuthority('read')")
    @GetMapping("/data")
    public ResponseEntity<String> data()
    {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());

        if(authentication instanceof CustomAuthentication x)
            System.out.println(x.getPriority());

        return ResponseEntity.ok("Data: 01000001");
    }

    @PreAuthorize("@priorityChecker.check(authentication, 'HIGH')")
    @GetMapping("/dataPriority")
    public ResponseEntity<String> dataPriority()
    {
        return ResponseEntity.ok("Data from dataPriority.");
    }
}
