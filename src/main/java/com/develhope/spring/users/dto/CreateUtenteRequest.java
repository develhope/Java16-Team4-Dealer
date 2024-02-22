package com.develhope.spring.users.dto;

import com.develhope.spring.users.entity.TipoUtente;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateUtenteRequest {
    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private String email;
    private String password;
    private TipoUtente tipoUtente;
    
}
