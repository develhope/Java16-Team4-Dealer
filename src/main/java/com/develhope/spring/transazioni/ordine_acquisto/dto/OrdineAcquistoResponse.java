package com.develhope.spring.transazioni.ordine_acquisto.dto;

import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.entity.TipoTransazione;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.veichles.entity.Veicolo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrdineAcquistoResponse {

    private Long id;
    private BigDecimal anticipo;
    private Boolean pagato;
    private Utente customer;
    private Utente vendor;
    private Veicolo veicolo;
    private StatoOrdine statoOrdine;
    private TipoTransazione tipoTransazione;
}
