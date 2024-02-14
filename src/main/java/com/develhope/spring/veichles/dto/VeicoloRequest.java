package com.develhope.spring.veichles.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VeicoloRequest {


    private String colore;

    private String tipoDiCambio;

    private OffsetDateTime annoImmatricolazione;

    private String alimentazione;

    private BigDecimal prezzo;

    private Double sconto;

    private String accessori;
}
