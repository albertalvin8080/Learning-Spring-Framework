package org.albert.oauth2_authorization_server.entities.registered_client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "registered_client")
public class AppRegisteredClient {
    @Id
    private String id;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_secret")
    private String clientSecret;

    @OneToMany(mappedBy = "appRegisteredClient", fetch = FetchType.EAGER)
    private Set<AppRedirectUri> redirectUriSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "registered_client_scope",
            joinColumns = @JoinColumn(name = "registered_client_id"),
            inverseJoinColumns = @JoinColumn(name = "scope_id")
    )
    private Set<AppScope> scopeSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "registered_client_client_authentication_method",
            joinColumns = @JoinColumn(name = "registered_client_id"),
            inverseJoinColumns = @JoinColumn(name = "client_authentication_method_id")
    )
    private Set<AppClientAuthenticationMethod> clientAuthenticationMethodSet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "registered_client_authorization_grant_type",
            joinColumns = @JoinColumn(name = "registered_client_id"),
            inverseJoinColumns = @JoinColumn(name = "authorization_grant_type_id")
    )
    private Set<AppAuthorizationGrantType> authorizationGrantTypeSet;
}
