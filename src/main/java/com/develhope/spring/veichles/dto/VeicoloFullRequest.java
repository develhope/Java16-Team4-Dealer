package com.develhope.spring.veichles.dto;

import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.entity.TipoVeicolo;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VeicoloFullRequest {


    private Long id;
    private String marca, modello, colore, tipoDiCambio, alimentazione, accessori;
    private TipoVeicolo tipoVeicolo;
    private Integer cilindrata, potenza;
    private OffsetDateTime annoImmatricolazione;
    private BigDecimal prezzo;
    private Boolean usato;
    private Double sconto;
    private StatoVendita statoVendita;
}
