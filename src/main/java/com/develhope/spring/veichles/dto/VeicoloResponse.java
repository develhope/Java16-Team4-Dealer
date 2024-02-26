package com.develhope.spring.veichles.dto;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.entity.TipoVeicolo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VeicoloResponse {


    private Long id;
    private String marca, modello, colore, tipoDiCambio, alimentazione, accessori;
    private TipoVeicolo tipoVeicolo;
    private Integer cilindrata, potenza;
    private OffsetDateTime annoImmatricolazione;
    private BigDecimal prezzo;
    private Boolean usato;
    private Double sconto;
    private StatoVendita statoVendita;
    private List<Noleggio> noleggioList;
    private Ordine_Acquisto ordineAcquisto;
}
