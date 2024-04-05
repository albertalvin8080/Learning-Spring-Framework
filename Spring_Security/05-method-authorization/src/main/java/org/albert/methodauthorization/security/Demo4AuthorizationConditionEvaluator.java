package org.albert.methodauthorization.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Demo4AuthorizationConditionEvaluator {

    public boolean usernameOrWriteAuthority(String username) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(username) ||
                authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("write"));
    }
}
