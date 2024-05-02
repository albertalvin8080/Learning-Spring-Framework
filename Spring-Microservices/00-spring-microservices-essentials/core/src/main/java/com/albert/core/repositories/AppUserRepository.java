package com.albert.core.repositories;

import com.albert.core.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long>
{
    @Query("SELECT au FROM AppUser au WHERE au.username = :username")
    Optional<AppUser> findByUsername(String username);
}
