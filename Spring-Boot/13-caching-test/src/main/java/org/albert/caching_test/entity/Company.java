package org.albert.caching_test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
// NOTE: Lombok is not working properly with caching.
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class Company implements Serializable
{
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;
    @Column(name = "company_name")
    private String name;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
