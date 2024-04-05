package org.albert.oauth2_authorization_server.entities.registered_client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "redirect_uri")
public class AppRedirectUri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uri;
    @ManyToOne
    @JoinColumn(name = "registered_client_id")
    private AppRegisteredClient appRegisteredClient;
}
