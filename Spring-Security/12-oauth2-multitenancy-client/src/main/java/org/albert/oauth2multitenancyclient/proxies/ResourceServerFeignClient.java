package org.albert.oauth2multitenancyclient.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

// request to '11-oauth2-multitenancy-resource-server' project
@FeignClient(name = "resourceServerFeignClient", url = "http://localhost:2222/demo")
public interface ResourceServerFeignClient {
    @GetMapping
    String getData(
            @RequestHeader("Authorization") String authorizationToken,
            @RequestHeader("tokenType") String tokenType
    );
}
