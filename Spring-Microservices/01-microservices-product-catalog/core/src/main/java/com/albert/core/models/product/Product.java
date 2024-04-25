package com.albert.core.models.product;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(value = "product")
public class Product
{
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private BigDecimal price;
}
