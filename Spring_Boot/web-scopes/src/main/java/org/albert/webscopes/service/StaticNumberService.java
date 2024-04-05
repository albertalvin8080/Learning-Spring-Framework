package org.albert.webscopes.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Qualifier("staticNumberService")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class StaticNumberService implements NumberService {
    private final int number;

    public StaticNumberService() {
        this.number = 1890;
    }

    @Override
    public int getNumber() {
        return number;
    }
}
