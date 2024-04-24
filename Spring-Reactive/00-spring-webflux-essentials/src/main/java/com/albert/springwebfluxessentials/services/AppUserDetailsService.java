package com.albert.springwebfluxessentials.services;

import com.albert.springwebfluxessentials.adapters.UserDetailsAdapter;
import com.albert.springwebfluxessentials.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements ReactiveUserDetailsService
{
    private final AppUserRepository repository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(UserDetailsAdapter::new);
    }
}
