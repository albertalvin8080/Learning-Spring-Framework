package com.albert;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class AppTest
{
    @Test
    public void bcrypt() {
        final String encode = new BCryptPasswordEncoder().encode("1234");
        log.info(encode);
    }
}
