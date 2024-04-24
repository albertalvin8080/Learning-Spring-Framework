package org.albert.oauth2_authorization_server.entities.user_details;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "authority")
public class AppUserAuthority {
    @Id
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "userAuthoritySet")
    private Set<AppUserDetails> userDetailsSet;
}
