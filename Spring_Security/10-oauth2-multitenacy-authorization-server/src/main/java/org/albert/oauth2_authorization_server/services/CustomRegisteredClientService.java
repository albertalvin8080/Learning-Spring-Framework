package org.albert.oauth2_authorization_server.services;

import lombok.RequiredArgsConstructor;
import org.albert.oauth2_authorization_server.mappers.RegisteredClientMapper;
import org.albert.oauth2_authorization_server.repositories.AppRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomRegisteredClientService implements RegisteredClientRepository {

    private final AppRegisteredClientRepository repository;

    @Override
    public void save(RegisteredClient registeredClient) {
        repository.save(RegisteredClientMapper.from(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        return repository.findById(id)
                .map(RegisteredClientMapper::from)
                .get();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return repository.findByClientId(clientId)
                .map(RegisteredClientMapper::from)
                .get();
    }
}
