package com.develhope.spring.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtenteResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private String password;

}
