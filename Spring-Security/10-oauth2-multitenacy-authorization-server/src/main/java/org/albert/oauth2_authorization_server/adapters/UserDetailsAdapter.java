package org.albert.oauth2_authorization_server.adapters;

import lombok.RequiredArgsConstructor;
import org.albert.oauth2_authorization_server.entities.user_details.AppUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserDetailsAdapter implements UserDetails {

    private final AppUserDetails appUserDetails;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return appUserDetails.getUserAuthoritySet()
                .stream()
                .map(UserAuthorityAdapter::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return appUserDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return appUserDetails.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
