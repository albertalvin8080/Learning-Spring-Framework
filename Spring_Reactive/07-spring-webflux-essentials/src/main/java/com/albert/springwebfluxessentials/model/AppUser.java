package com.albert.springwebfluxessentials.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "appUser", schema = "webflux")
public class AppUser
{
    @Id
    private Long id;
    private String username;
    private String password;
    private String roles;
}
