package org.albert.oauth2client.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "resourceServerProxy", url = "http://localhost:9090/demo")
public interface ResourceServerFeignClient {

    @GetMapping("/clientTest")
    String getData(@RequestHeader("Authorization") String authorizationToken);
}
