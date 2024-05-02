package com.albert.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "product", schema = "microservices")
public class Product implements AbstractEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull(message = "Entity Product must have a name.")
    @Column(nullable = false)
    private String name;
    @NotNull(message = "Entity Product must have a price.")
    @Column(nullable = false)
    private BigDecimal price;
}
