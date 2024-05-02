package com.albert.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "app_user")
public class AppUser implements AbstractEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull(message = "Entity AppUser must have a username.")
    @Column(nullable = false)
    private String username;
    @NotNull(message = "Entity AppUser must have a password.")
    @Column(nullable = false)
    private String password;
    @NotNull(message = "Entity AppUser must have at least one role.")
    @Column(nullable = false)
    private String roles = "USER";
}
