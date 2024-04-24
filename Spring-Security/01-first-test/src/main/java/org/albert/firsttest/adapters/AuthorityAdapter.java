package org.albert.firsttest.adapters;

import lombok.RequiredArgsConstructor;
import org.albert.firsttest.entities.Authority;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class AuthorityAdapter implements GrantedAuthority {

    private final Authority authority;

    @Override
    public String getAuthority() {
        return authority.getName();
    }
}
