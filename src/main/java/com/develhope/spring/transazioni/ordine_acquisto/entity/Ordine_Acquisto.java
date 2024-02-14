package com.develhope.spring.transazioni.ordine_acquisto.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Ordine_Acquisto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal anticipo;
    private boolean pagato;
    //private Utente customer;
    //private Utente vendor;
    @Enumerated (EnumType.STRING)
    private StatoOrdine statoOrdine;

    @Enumerated (EnumType.STRING)
    private StatoVeicolo statoVeicolo;
}
