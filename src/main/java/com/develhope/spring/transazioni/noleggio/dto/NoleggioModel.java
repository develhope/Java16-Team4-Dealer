package com.develhope.spring.transazioni.noleggio.dto;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UtenteRepo utenteRepo;
    @Autowired
    VeicoloRepo veicoloRepo;

    private Long id;
    private OffsetDateTime dataInizio;

    private OffsetDateTime dataFine;

    private BigDecimal costoGiornaliero;

    private BigDecimal costoTotale;

    private Boolean pagato;

    private Boolean noleggiato;

    private Long idVendor;

    private Long idCustomer;

    private Long idVeicolo;

    public NoleggioModel convertRequestToModel(NoleggioRequest request) {
        return NoleggioModel.builder()
                .id(request.getId())
                .dataInizio(request.getDataInizio())
                .dataFine(request.getDataFine())
                .costoGiornaliero(request.getCostoGiornaliero())
                .costoTotale(request.getCostoTotale())
                .pagato(request.getPagato())
                .noleggiato(request.getNoleggiato())
                .idCustomer(request.getIdcustomer())
                .idVendor(request.getIdvendor())
                .idVeicolo(request.getIdveicolo())
                .build();

    }

    public Noleggio convertModelToEntity(NoleggioModel model) {
        Utente customer = utenteRepo.findById(model.getIdCustomer()).orElse(null);
        Utente vendor = utenteRepo.findById(model.getIdVendor()).orElse(null);
        Veicolo veicolo = veicoloRepo.findById(model.getIdVeicolo()).orElse(null);
        return Noleggio.builder()
                .id(model.getId())
                .dataInizio(model.getDataInizio())
                .dataFine(model.getDataFine())
                .costoGiornaliero(model.getCostoGiornaliero())
                .costoTotale(model.getCostoTotale())
                .pagato(model.getPagato())
                .noleggiato(model.getNoleggiato())
                .vendor(vendor)
                .customer(customer)
                .veicolo(veicolo)
                .build();
    }

    public NoleggioModel convertEntityToModel(Noleggio noleggio) {

        NoleggioModel.NoleggioModelBuilder builder = NoleggioModel.builder()
                .id(noleggio.getId())
                .dataInizio(noleggio.getDataInizio())
                .dataFine(noleggio.getDataFine())
                .costoGiornaliero(noleggio.getCostoGiornaliero())
                .costoTotale(noleggio.getCostoTotale())
                .pagato(noleggio.isPagato())
                .noleggiato(noleggio.isNoleggiato());
        if (noleggio.getVendor() != null) {
            builder().idVendor(noleggio.getVendor().getId());
        }
        if (noleggio.getCustomer() != null){
            builder().idCustomer(noleggio.getCustomer().getId());
        }
        if (noleggio.getVeicolo() != null){
            builder().idVeicolo(noleggio.getVeicolo().getId());
        }
        return builder().build();
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
                .idVendor(model.getIdVendor())
                .idCustomer(model.getIdCustomer())
                .idVeicolo(model.getIdVeicolo())
                .build();
    }

}
