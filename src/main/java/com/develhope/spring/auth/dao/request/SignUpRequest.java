package com.develhope.spring.auth.dao.request;

import com.develhope.spring.users.entity.TipoUtente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private Integer phoneNumber;
    private String email;
    private String password;
    private TipoUtente tipoUtente;
}
