package com.develhope.spring.transazioni.ordine_acquisto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import java.math.BigDecimal;

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
    private StatoOrdine statoOrdine;
    private StatoVeicolo statoVeicolo;
}
