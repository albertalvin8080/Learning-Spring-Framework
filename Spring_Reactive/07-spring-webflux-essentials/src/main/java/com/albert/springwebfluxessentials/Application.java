package com.albert.springwebfluxessentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class Application {
    static { // adds lag, uncomment only when necessary
        BlockHound.install();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
