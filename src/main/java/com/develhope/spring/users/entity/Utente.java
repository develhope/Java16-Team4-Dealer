package com.develhope.spring.users.entity;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.users.dto.CreateUtenteRequest;
import com.develhope.spring.users.dto.UtenteResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "phoneNumber", nullable = false)
    private Integer phoneNumber
            ;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "TipoUtente", nullable = false)
    @Enumerated (EnumType.STRING)
    private TipoUtente tipoUtente;


//    @OneToMany(mappedBy = "customer")
//    @JsonIgnore
//    private List<Noleggio> customerStoricoNoleggi;
//
//    @OneToMany(mappedBy = "vendor")
//    @JsonIgnore
//    private List<Noleggio> vendorStoricoNoleggi;
//
//    @OneToMany(mappedBy = "customer")
//    @JsonIgnore
//    private List<Ordine_Acquisto> customerStoricoOrdiniAcquisti;
//
//
//    @OneToMany(mappedBy = "vendor")
//    @JsonIgnore
//    private List<Ordine_Acquisto> vendorStoricoVendite;

    public static Utente convertRequest(CreateUtenteRequest request) {
        return Utente.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public static UtenteResponse convertEntityInResponse(Utente entity) {
        return UtenteResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }
}

