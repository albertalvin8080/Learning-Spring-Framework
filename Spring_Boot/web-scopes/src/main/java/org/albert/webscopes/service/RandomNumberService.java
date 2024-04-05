package org.albert.webscopes.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Qualifier(value = "randomNumberService")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class RandomNumberService implements NumberService {
    private final int number;

    public RandomNumberService() {
        this.number = ThreadLocalRandom.current().nextInt(100, 1000);
    }

    @Override
    public int getNumber() {
//        new RuntimeException().printStackTrace();
        return number;
    }
}
