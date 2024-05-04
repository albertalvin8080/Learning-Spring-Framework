package com.albert.authenticationservice.endpoint.controllers;

import com.albert.core.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController
{
    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUser> userInfo(Principal principal) {
//        AppUser appUser = (AppUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        final AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(appUser);
    }
}
