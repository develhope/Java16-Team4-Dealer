package com.develhope.spring.transazioni.ordine_acquisto.entity;

import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.veichles.entity.Veicolo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ordine_Acquisto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal anticipo;
    private boolean pagato;

    @OneToOne
    private Utente customer;

    @OneToOne
    private Utente vendor;

    @OneToOne
    private Veicolo veicolo;

    @Enumerated (EnumType.STRING)
    private StatoOrdine statoOrdine;

    @Enumerated (EnumType.STRING)
    private StatoVeicolo statoVeicolo;
}
