package org.albert.integrationtestscustommockuser.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomAuthentication implements Authentication {

    private final String priority;

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public String getName() {
        return "albert";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "read", () -> "write");
    }

    @Override
    // there is no UserDetails here because the @WithMockUser creates an Authentication directly in the test.
    public Object getPrincipal() {
        return null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

}
