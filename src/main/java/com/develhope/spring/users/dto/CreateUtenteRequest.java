package com.develhope.spring.users.dto;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateUtenteRequest {
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private String password;
    
}
