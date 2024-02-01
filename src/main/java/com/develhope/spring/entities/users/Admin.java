package com.develhope.spring.entities.users;

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

public class Admin {

    private String firstName;
    private String lastName;

    private String email;
    private String password;
}
