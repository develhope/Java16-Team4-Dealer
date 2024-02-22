package com.develhope.spring.transazioni.noleggio.service;

import com.develhope.spring.transazioni.noleggio.dto.NoleggioModel;
import com.develhope.spring.transazioni.noleggio.dto.NoleggioRequest;
import com.develhope.spring.transazioni.noleggio.dto.NoleggioResponse;
import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoRequest;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.users.entity.TipoUtente;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
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
    @Autowired
    UtenteRepo utenteRepo;
    @Autowired
    VeicoloRepo veicoloRepo;
    @SneakyThrows
    private Utente getUtenteById(long id) {
        Optional<Utente> utente = this.utenteRepo.findById(id);
        if (utente.isEmpty()) throw new IOException("Utente non trovato");
        return utente.get();
    }

    @SneakyThrows
    private Utente utenteTipoUtenteCheck(Long idUtente, TipoUtente tipoUtente) {
        Utente utente = getUtenteById(idUtente);
        if (utente.getTipoUtente() != tipoUtente) throw new IOException("Utente non eleggibile per la richiesta fatta");
        return utente;
    }

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
    private Noleggio buildNoleggio(NoleggioRequest request, Utente customer, Veicolo veicolo,Utente vendor) {
        return Noleggio.builder()
                .dataInizio(request.getDataInizio())
                .dataFine(request.getDataFine())
                .costoGiornaliero(request.getCostoGiornaliero())
                .costoTotale(request.getCostoTotale())
                .pagato(request.getPagato())
                .noleggiato(request.getNoleggiato())
                .build();

    }

    public ResponseEntity<NoleggioResponse> createNoleggio( Long idCliente, Long idVeicolo, Long idVenditore, NoleggioRequest request) {
        Utente customer = utenteTipoUtenteCheck(idVenditore, TipoUtente.CUSTOMER);
        Optional<Veicolo> veicolo = veicoloRepo.findById(idVeicolo);
        if (veicolo.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Utente venditore = null;
        if (idVenditore != null){
            venditore= utenteTipoUtenteCheck(idVenditore, TipoUtente.VENDOR);
        }
        Noleggio noleggioEntity = buildNoleggio(request,customer,veicolo.get(),venditore);
        this.noleggioRepo.save(noleggioEntity);
        return ResponseEntity.ok(entityToResponseNoleggio(noleggioEntity));
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
        noleggioEntity.setCustomer(request.getCustomer());
        noleggioEntity.setVendor(request.getVendor());
        noleggioEntity.setVeicolo(request.getVeicolo());

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
