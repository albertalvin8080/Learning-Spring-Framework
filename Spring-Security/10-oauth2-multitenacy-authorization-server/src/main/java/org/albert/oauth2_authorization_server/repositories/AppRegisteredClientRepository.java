package org.albert.oauth2_authorization_server.repositories;

import org.albert.oauth2_authorization_server.entities.registered_client.AppRegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppRegisteredClientRepository extends JpaRepository<AppRegisteredClient, String> {

    @Query("SELECT rca FROM AppRegisteredClient rca WHERE rca.clientId = :clientId")
    Optional<AppRegisteredClient> findByClientId(String clientId);
}
