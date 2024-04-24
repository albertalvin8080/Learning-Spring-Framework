package org.albert.oauth2_authorization_server.services;

import lombok.RequiredArgsConstructor;
import org.albert.oauth2_authorization_server.adapters.UserDetailsAdapter;
import org.albert.oauth2_authorization_server.repositories.AppUserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserDetailsRepository appUserDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserDetailsRepository
                .findByUsername(username)
                .map(UserDetailsAdapter::new)
                .orElseThrow(() -> new UsernameNotFoundException("It's exactly what it says."));
    }
}
