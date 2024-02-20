package com.develhope.spring.transazioni.noleggio.entity;

import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.veichles.entity.Veicolo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Column(name = "Utente", nullable = false)
    private Utente utente;
    @Column(name = "Veicolo", nullable = false)
    private Veicolo veicolo;

}
