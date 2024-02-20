package com.develhope.spring.transazioni.noleggio.dto;

import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.veichles.entity.Veicolo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoleggioResponse {
    private Long id;

    private OffsetDateTime dataInizio;

    private OffsetDateTime dataFine;

    private BigDecimal costoGiornaliero;

    private BigDecimal costoTotale;

    private Boolean pagato;

    private Boolean noleggiato;
    private Utente utente;
    private Veicolo veicolo;
}
