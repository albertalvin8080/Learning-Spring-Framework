package com.albert.authenticationservice.security.user;

import com.albert.core.adapters.UserDetailsAdapter;
import com.albert.core.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService
{
    private final AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(UserDetailsAdapter::new)
                .orElseThrow(() -> {
                    final String formatted = "User '%s' not found.".formatted(username);
                    return new UsernameNotFoundException(formatted);
                });
    }
}
