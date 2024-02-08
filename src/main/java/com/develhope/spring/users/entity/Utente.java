package com.develhope.spring.users.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor


public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstName",nullable = false)
    private String firstName;
    @Column(name = "lastName",nullable = false)
    private String lastName;
    @Column(name = "phoneNumber",nullable = false)
    private int phoneNumber;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
}

