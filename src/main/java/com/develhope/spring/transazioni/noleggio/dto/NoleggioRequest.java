package com.develhope.spring.transazioni.noleggio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class NoleggioRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime dataInizio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime dataFine;

    private BigDecimal costoGiornaliero;

    private BigDecimal costoTotale;

    private boolean pagato;

    private boolean noleggiato;
}
