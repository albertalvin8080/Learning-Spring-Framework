package org.albert.firsttest.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    /*
    WARNING: if you use @Data instead of @Getter and @Setter, your authorities Set will be empty.
    Why the setter is problematic: JPA relies on a specific mechanism to manage relationships between entities.
    When you add or remove authorities from the authoritySet, JPA needs to be informed about these changes to
    update the database properly. However, the automatically generated setter by @Data might bypass JPA's change
    management. This could result in the authoritySet appearing empty even though it has elements.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authoritySet;
}
