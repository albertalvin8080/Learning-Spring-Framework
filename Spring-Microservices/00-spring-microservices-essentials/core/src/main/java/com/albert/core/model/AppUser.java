package com.albert.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "app_user", schema = "microservices")
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
    @Builder.Default
    @Column(nullable = false)
    private String roles = "USER";
}
