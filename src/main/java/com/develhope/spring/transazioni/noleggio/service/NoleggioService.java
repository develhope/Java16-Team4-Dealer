package com.develhope.spring.transazioni.noleggio.service;

import com.develhope.spring.transazioni.noleggio.dto.NoleggioModel;
import com.develhope.spring.transazioni.noleggio.dto.NoleggioRequest;
import com.develhope.spring.transazioni.noleggio.dto.NoleggioResponse;
import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoRequest;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NoleggioService {

    @Autowired
    private NoleggioRepo noleggioRepo;
    @Autowired
    NoleggioModel noleggioModel;

    @SneakyThrows
    public Noleggio getById(Long id) {
        Optional<Noleggio> optionalNoleggio = this.noleggioRepo.findById(id);
        if (optionalNoleggio.isEmpty()) throw new IOException("Noleggio non trovato");
        return optionalNoleggio.get();
    }

    private Noleggio requestToEntityNoleggio(NoleggioRequest request) {
        return noleggioModel.convertModelToEntity(noleggioModel.convertRequestToModel(request));
    }

    private NoleggioResponse entityToResponseNoleggio(Noleggio entity) {
        return noleggioModel.convertModelToResponse(noleggioModel.convertEntityToModel(entity));
    }

    public NoleggioResponse createNoleggio(NoleggioRequest request) {
        Noleggio noleggioEntity = requestToEntityNoleggio(request);
        this.noleggioRepo.save(noleggioEntity);
        return entityToResponseNoleggio(noleggioEntity);
    }


    public List<Noleggio> getAll() {
        return noleggioRepo.findAll();
    }


    public ResponseEntity<String> deleteNoleggio(long id) {
        Noleggio existingNoleggio = getById(id);
        noleggioRepo.delete(existingNoleggio);
        return ResponseEntity.ok("Noleggio eliminato correttamente");
    }


    public NoleggioResponse updateNoleggio(long id, NoleggioRequest request) {
        Noleggio noleggioEntity = getById(id);

        noleggioEntity.setDataInizio(request.getDataInizio());
        noleggioEntity.setDataFine(request.getDataFine());
        noleggioEntity.setCostoGiornaliero(request.getCostoGiornaliero());
        noleggioEntity.setCostoTotale(request.getCostoTotale());
        noleggioEntity.setPagato(request.getPagato());
        noleggioEntity.setNoleggiato(request.getNoleggiato());

        this.noleggioRepo.save(noleggioEntity);

        return entityToResponseNoleggio(noleggioEntity);
    }
    public NoleggioResponse patchONoleggio (NoleggioRequest request, Long id){
        Noleggio noleggioEntity = getById(id);

        if (request.getDataInizio()!=null)
            noleggioEntity.setDataInizio(request.getDataInizio());

        if (request.getDataFine()!=null)
            noleggioEntity.setDataFine(request.getDataFine());

        if (request.getCostoGiornaliero()!=null)
            noleggioEntity.setCostoGiornaliero(request.getCostoGiornaliero());

        if (request.getCostoTotale()!=null)
            noleggioEntity.setCostoTotale(request.getCostoTotale());

        if (request.getVeicolo()!=null)
            noleggioEntity.setVeicolo(request.getVeicolo());

        if (request.getPagato()!=null)
            noleggioEntity.setPagato(request.getPagato());

        if (request.getNoleggiato()!=null)
            noleggioEntity.setNoleggiato(request.getNoleggiato());

        if (request.getCustomer()!=null)
            noleggioEntity.setCustomer(request.getCustomer());

        if (request.getVendor()!=null)
            noleggioEntity.setVendor(request.getVendor());

        this.noleggioRepo.saveAndFlush(noleggioEntity);

        return entityToResponseNoleggio(noleggioEntity);
    }


}
