package com.develhope.spring.transazioni.noleggio.dto;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.veichles.entity.Veicolo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoleggioModel {

    private Long id;
    private OffsetDateTime dataInizio;

    private OffsetDateTime dataFine;

    private BigDecimal costoGiornaliero;

    private BigDecimal costoTotale;

    private Boolean pagato;

    private Boolean noleggiato;

    private Utente vendor;

    private Utente customer;

    private Veicolo veicolo;

    public NoleggioModel convertRequestToModel(NoleggioRequest request) {
        return NoleggioModel.builder()
                .id(request.getId())
                .dataInizio(request.getDataInizio())
                .dataFine(request.getDataFine())
                .costoGiornaliero(request.getCostoGiornaliero())
                .costoTotale(request.getCostoTotale())
                .pagato(request.getPagato())
                .noleggiato(request.getNoleggiato())
                .vendor(request.getVendor())
                .customer(request.getCustomer())
                .build();

    }

    public Noleggio convertModelToEntity(NoleggioModel model) {
        return Noleggio.builder()
                .id(model.getId())
                .dataInizio(model.getDataInizio())
                .dataFine(model.getDataFine())
                .costoGiornaliero(model.getCostoGiornaliero())
                .costoTotale(model.getCostoTotale())
                .pagato(model.getPagato())
                .noleggiato(model.getNoleggiato())
                .vendor(model.getVendor())
                .customer(model.getCustomer())
                .build();
    }

    public NoleggioModel convertEntityToModel(Noleggio noleggio) {
        return NoleggioModel.builder()
                .id(noleggio.getId())
                .dataInizio(noleggio.getDataInizio())
                .dataFine(noleggio.getDataFine())
                .costoGiornaliero(noleggio.getCostoGiornaliero())
                .costoTotale(noleggio.getCostoTotale())
                .pagato(noleggio.isPagato())
                .noleggiato(noleggio.isNoleggiato())
                .vendor(noleggio.getVendor())
                .customer(noleggio.getCustomer())
                .build();
    }

    public NoleggioResponse convertModelToResponse(NoleggioModel model) {
        return NoleggioResponse.builder()
                .id(model.getId())
                .dataInizio(model.getDataInizio())
                .dataFine(model.getDataFine())
                .costoGiornaliero(model.getCostoGiornaliero())
                .costoTotale(model.getCostoTotale())
                .pagato(model.getPagato())
                .noleggiato(model.getNoleggiato())
                .vendor(model.getVendor())
                .customer(model.getCustomer())
                .build();
    }

}
