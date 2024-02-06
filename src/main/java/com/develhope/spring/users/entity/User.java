package com.develhope.spring.users.entity;

import jakarta.persistence.Entity;
import jdk.jfr.DataAmount;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class User {

    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private String password;
}


// mannaggia a pietro