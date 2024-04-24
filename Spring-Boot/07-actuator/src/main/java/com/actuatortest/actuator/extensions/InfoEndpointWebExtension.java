package com.actuatortest.actuator.extensions;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Component;

@Component
/*
* @EndpointWebExtension()
* Declares a class as an extension to a web-based actuator endpoint.
* This allows you to customize the behavior of an existing endpoint.
* */
@EndpointWebExtension(endpoint = InfoEndpoint.class)
public class InfoEndpointWebExtension {

    @ReadOperation
    public WebEndpointResponse<String> info() {
        return new WebEndpointResponse<>("INFO", 200);
    }
}
