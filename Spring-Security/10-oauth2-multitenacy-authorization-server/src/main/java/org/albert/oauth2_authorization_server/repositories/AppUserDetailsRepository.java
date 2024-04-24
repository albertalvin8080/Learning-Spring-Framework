package org.albert.oauth2_authorization_server.repositories;

import org.albert.oauth2_authorization_server.entities.user_details.AppUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserDetailsRepository extends JpaRepository<AppUserDetails, Integer> {
    @Query("""
        SELECT u FROM AppUserDetails u WHERE u.username = :username
    """)
    Optional<AppUserDetails> findByUsername(String username);
}
