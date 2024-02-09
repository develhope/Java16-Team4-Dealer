package com.develhope.spring.transazioni.noleggio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "dataInizio", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime dataInizio;

    @Column(name = "dataFine", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime dataFine;

    @Column(name = "costoGiornaliero", nullable = false)
    private BigDecimal costoGiornaliero;

    @Column(name = "costoTotale", nullable = false)
    private BigDecimal costoTotale;

    @Column(name = "pagato", nullable = false)
    private boolean pagato;

    @Column(name = "noleggiato", nullable = false)
    private boolean noleggiato;

}
