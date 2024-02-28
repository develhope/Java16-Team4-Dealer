package com.develhope.spring.transazioni.ordine_acquisto.dto;

import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.entity.TipoTransazione;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.veichles.entity.Veicolo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrdineAcquistoModel {

    private Long id;
    private BigDecimal anticipo;
    private Boolean pagato;
    private Utente customer;
    private Utente vendor;
    private Veicolo veicolo;
    private StatoOrdine statoOrdine;
    private TipoTransazione tipoTransazione;


    // REQUEST -> MODEL
    public OrdineAcquistoModel convertRequestToMod(OrdineAcquistoRequest request){
        return OrdineAcquistoModel.builder()
                .id(request.getId())
                .anticipo(request.getAnticipo())
                .pagato(request.getPagato())
                .customer(request.getCustomer())
                .vendor(request.getVendor())
                .veicolo(request.getVeicolo())
                .statoOrdine(request.getStatoOrdine())
                .tipoTransazione(request.getTipoTransazione())
                .build();
    }


    //  MODEL -> ENT
    public Ordine_Acquisto convertModelToEnt(OrdineAcquistoModel model){
        return Ordine_Acquisto.builder()
                .id(model.getId())
                .anticipo(model.getAnticipo())
                .pagato(model.getPagato())
                .customer(model.getCustomer())
                .vendor(model.getVendor())
                .veicolo(model.getVeicolo())
                .statoOrdine(model.getStatoOrdine())
                .tipoTransazione(model.getTipoTransazione())
                .build();
    }


    //  ENT -> MODEL
    public OrdineAcquistoModel convertEntToModel(Ordine_Acquisto ent){
        return OrdineAcquistoModel.builder()
                .id(ent.getId())
                .anticipo(ent.getAnticipo())
                .pagato(ent.isPagato())
                .customer(ent.getCustomer())
                .vendor(ent.getVendor())
                .veicolo(ent.getVeicolo())
                .statoOrdine(ent.getStatoOrdine())
                .tipoTransazione(ent.getTipoTransazione())
                .build();
    }

    //  MODEL -> RESPONSE
    public OrdineAcquistoResponse convertModelToResponse(OrdineAcquistoModel model){
        return OrdineAcquistoResponse.builder()
                .id(model.getId())
                .anticipo(model.getAnticipo())
                .pagato(model.getPagato())
                .customer(model.getCustomer())
                .vendor(model.getVendor())
                .veicolo(model.getVeicolo())
                .statoOrdine(model.getStatoOrdine())
                .tipoTransazione(model.getTipoTransazione())
                .build();
    }

}
