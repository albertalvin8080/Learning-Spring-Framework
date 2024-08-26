package org.albert.standardexpressions.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book
{
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
}
