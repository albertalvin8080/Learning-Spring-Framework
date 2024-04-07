package org.albert.integrationtestscustommockuser.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("priorityChecker")
public class AuthenticationPriorityChecker {

    public boolean check(Authentication authentication, String requiredPriority) {
        if(authentication instanceof CustomAuthentication x)
            return requiredPriority.equals(x.getPriority());

        throw new RuntimeException("The authorization provided is not of type CustomAuthorization.");
    }
}
