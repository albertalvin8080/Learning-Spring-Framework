package com.albert.springwebfluxessentials.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

// This replaces the 'DefaultErrorAttributes' bean inside the Spring Context.
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes
{
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        final Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        final Throwable error = super.getError(request);

        if (error instanceof ResponseStatusException x) {
            errorAttributes.put("message", x.getMessage().replaceAll("\"", "'"));
            errorAttributes.put("developerMessage", "Check the documentation");
        }

        return errorAttributes;
    }
}
