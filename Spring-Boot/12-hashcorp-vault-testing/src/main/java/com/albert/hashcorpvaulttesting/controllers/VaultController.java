package com.albert.hashcorpvaulttesting.controllers;

import com.albert.hashcorpvaulttesting.config.VaultConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VaultController
{
    private final VaultConfig vault;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private String vault() {
        log.info(vault.getName());
        return vault.toString();
    }
}
