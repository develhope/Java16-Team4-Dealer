package com.develhope.spring.entities.users;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Vendors {

    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private String password;
}
