package com.develhope.spring.users.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateUtenteRequest {
    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private String email;
    private String password;
}
