package com.albert.springwebfluxessentials.repositories;

import com.albert.springwebfluxessentials.model.AppUser;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AppUserRepository extends ReactiveCrudRepository<AppUser, Long>
{
    @Query("SELECT webflux.appUser.* FROM webflux.appUser WHERE webflux.appUser.username = :username")
    Mono<AppUser> findByUsername(String username);
}
