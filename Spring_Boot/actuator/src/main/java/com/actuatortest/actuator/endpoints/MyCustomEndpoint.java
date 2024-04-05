package com.actuatortest.actuator.endpoints;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
/*
* The @Endpoint annotation registers the MyCustomEndpoint
* class as a Spring bean specifically for actuator.
* The id attribute within @Endpoint defines the unique
* identifier for the endpoint. This ID is used in the URL path.
* */
@Endpoint(id = "myendpoint")
public class MyCustomEndpoint {

    /*
    * @ReadOperation Annotates a method within an @Endpoint-annotated class to
    * expose it as a read-only operation of the endpoint. This method can be
    * invoked via HTTP GET requests.
    * */
    @ReadOperation
    public String test() {
        return ":)";
    }
}
