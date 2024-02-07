package com.develhope.spring.transazioni.noleggio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Noleggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime dataInizio;
    private OffsetDateTime dataFine;
    private BigDecimal costoGiornaliero;
    private BigDecimal costoTotale;
    private boolean pagato;
    private boolean noleggiato;

}
