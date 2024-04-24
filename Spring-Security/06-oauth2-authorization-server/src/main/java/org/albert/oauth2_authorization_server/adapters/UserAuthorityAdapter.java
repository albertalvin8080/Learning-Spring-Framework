package org.albert.oauth2_authorization_server.adapters;

import lombok.RequiredArgsConstructor;
import org.albert.oauth2_authorization_server.entities.user_details.AppUserAuthority;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class UserAuthorityAdapter implements GrantedAuthority {

    private final AppUserAuthority appUserAuthority;

    @Override
    public String getAuthority() {
        return appUserAuthority.getName();
    }
}
