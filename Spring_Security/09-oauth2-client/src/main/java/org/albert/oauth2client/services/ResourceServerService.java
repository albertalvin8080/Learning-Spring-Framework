package org.albert.oauth2client.services;

import lombok.RequiredArgsConstructor;
import org.albert.oauth2client.proxies.ResourceServerProxy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceServerService {

    private final ResourceServerProxy proxy;

    public String getData() {
        return proxy.getData();
    }
}
