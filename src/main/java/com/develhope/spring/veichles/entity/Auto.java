package com.develhope.spring.veichles.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;
    private String modello;
    private int cilindrata;
    private String colore;
    private int potenza;
    private String tipoDiCambio;
    private OffsetDateTime annoImmatricolazione;
    private String alimentazione;
    private BigDecimal prezzo;
    private BigDecimal sconto;

}
