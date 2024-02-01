package com.develhope.spring.entities.veichles;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Cars {
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
