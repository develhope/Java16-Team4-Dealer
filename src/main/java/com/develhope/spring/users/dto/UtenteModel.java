package com.develhope.spring.users.dto;

import com.develhope.spring.transazioni.noleggio.dto.NoleggioModel;
import com.develhope.spring.transazioni.noleggio.dto.NoleggioRequest;
import com.develhope.spring.transazioni.noleggio.dto.NoleggioResponse;
import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.users.entity.TipoUtente;
import com.develhope.spring.users.entity.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtenteModel {

    private Long id;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private String password;
    private TipoUtente tipoUtente;

    public UtenteModel convertRequestToModel(CreateUtenteRequest request) {
        return UtenteModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(request.getPassword())
                .tipoUtente(request.getTipoUtente())
                .build();

    }

    public Utente convertModelToEntity(UtenteModel model) {
        return Utente.builder()
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .phoneNumber(model.getPhoneNumber())
                .email(model.getEmail())
                .password(model.getPassword())
                .tipoUtente(model.getTipoUtente())
                .build();
    }

    public UtenteModel convertEntityToModel(Utente utente) {
        return UtenteModel.builder()
                .firstName(utente.getFirstName())
                .lastName(utente.getLastName())
                .phoneNumber(utente.getPhoneNumber())
                .email(utente.getEmail())
                .password(utente.getPassword())
                .tipoUtente(utente.getTipoUtente())
                .build();
    }

    public UtenteResponse convertModelToResponse(UtenteModel model) {
        return UtenteResponse.builder()
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .phoneNumber(model.getPhoneNumber())
                .email(model.getEmail())
                .password(model.getPassword())
                .tipoUtente(model.getTipoUtente())
                .build();
    }
}
