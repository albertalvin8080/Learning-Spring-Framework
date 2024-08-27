package org.albert.standardexpressions.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User
{
    private String name;
    private String email;
    private String role;
    private String gender;
    private String password;
}
