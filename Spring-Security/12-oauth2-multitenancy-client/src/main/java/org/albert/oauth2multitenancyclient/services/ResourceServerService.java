package org.albert.oauth2multitenancyclient.services;

import lombok.RequiredArgsConstructor;
import org.albert.oauth2multitenancyclient.proxies.ResourceServerProxy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceServerService {

    private final ResourceServerProxy proxy;

    public String getDataUsingAuthorizationServer1() {
        return proxy.getDataUsingAuthorizationServer1();
    }

    public String getDataUsingAuthorizationServer2() {
        return proxy.getDataUsingAuthorizationServer2();
    }
}
