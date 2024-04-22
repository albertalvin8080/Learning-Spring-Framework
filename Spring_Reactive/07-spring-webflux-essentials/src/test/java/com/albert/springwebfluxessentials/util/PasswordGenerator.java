package com.albert.springwebfluxessentials.util;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class PasswordGenerator
{
    public static void main(String[] args) {
        final String encode = PasswordEncoderFactories
                .createDelegatingPasswordEncoder().encode("1234");
        System.out.println(encode);
    }
}
