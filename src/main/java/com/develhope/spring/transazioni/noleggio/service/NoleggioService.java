package com.develhope.spring.transazioni.noleggio.service;

import com.develhope.spring.shared.Error;
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
import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import io.vavr.control.Either;
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
    public Either<Error,Noleggio> getById(Long id) {
        Optional<Noleggio> optionalNoleggio = this.noleggioRepo.findById(id);
        if (optionalNoleggio.isEmpty())return Either.left(new Error(510,"Noleggio non trovato"));
        return Either.right(optionalNoleggio.get());    }

    public Noleggio requestToEntityNoleggio(NoleggioRequest request) {
        return noleggioModel.convertModelToEntity(noleggioModel.convertRequestToModel(request));
    }

    public NoleggioResponse entityToResponseNoleggio(Noleggio entity) {
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
                .customer(customer)
                .vendor(vendor)
                .veicolo(veicolo)
                .build();

    }

    public ResponseEntity<NoleggioResponse> createNoleggio(Long idCliente, Long idVeicolo, Long idVenditore, NoleggioRequest request) {
        Utente customer = utenteTipoUtenteCheck(idCliente, TipoUtente.CUSTOMER);
        Optional<Veicolo> veicolo = veicoloRepo.findById(idVeicolo);
        if (veicolo.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (veicolo.get().getStatoVendita()!= StatoVendita.NOLEGGIABILE ) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
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


    public Either<Error,ResponseEntity<String>> deleteNoleggio(long id) {
        Either<Error,Noleggio> existingNoleggio = getById(id);
        if (existingNoleggio.isLeft())return Either.left(existingNoleggio.getLeft());
        this.noleggioRepo.deleteById(id);
        return Either.right(ResponseEntity.ok("Noleggio eliminato correttamente"));
    }
    
    public Either<Error,NoleggioResponse> updateNoleggio(long id, NoleggioRequest request) {
        Either<Error,Noleggio> noleggioEntity = getById(id);
        if (noleggioEntity.isLeft()){
            return Either.left(noleggioEntity.getLeft());
        }
        Utente customer = utenteRepo.findById(request.getIdcustomer()).orElse(null);
        Utente vendor = utenteRepo.findById(request.getIdvendor()).orElse(null);
        Veicolo veicolo = veicoloRepo.findById(request.getIdveicolo()).orElse(null);

        noleggioEntity.get().setDataInizio(request.getDataInizio());
        noleggioEntity.get().setDataFine(request.getDataFine());
        noleggioEntity.get().setCostoGiornaliero(request.getCostoGiornaliero());
        noleggioEntity.get().setCostoTotale(request.getCostoTotale());
        noleggioEntity.get().setPagato(request.getPagato());
        noleggioEntity.get().setNoleggiato(request.getNoleggiato());
        noleggioEntity.get().setCustomer(customer);
        noleggioEntity.get().setVendor(vendor);
        noleggioEntity.get().setVeicolo(veicolo);
        this.noleggioRepo.save(noleggioEntity.get());

        return Either.right(entityToResponseNoleggio(noleggioEntity.get()));
    }
    public Either<Error,NoleggioResponse> patchONoleggio (NoleggioRequest request, Long id){
        Either<Error,Noleggio> noleggioEntity = getById(id);
        if (noleggioEntity.isLeft())return Either.left(noleggioEntity.getLeft());
        Veicolo veicolo = veicoloRepo.findById(request.getIdveicolo()).orElse(null);
        Utente vendor = utenteRepo.findById(request.getIdvendor()).orElse(null);
        Utente customer = utenteRepo.findById(request.getIdcustomer()).orElse(null);

        if (request.getDataInizio()!=null)
            noleggioEntity.get().setDataInizio(request.getDataInizio());

        if (request.getDataFine()!=null)
            noleggioEntity.get().setDataFine(request.getDataFine());

        if (request.getCostoGiornaliero()!=null)
            noleggioEntity.get().setCostoGiornaliero(request.getCostoGiornaliero());

        if (request.getCostoTotale()!=null)
            noleggioEntity.get().setCostoTotale(request.getCostoTotale());

        if (request.getIdveicolo()!=null)
            noleggioEntity.get().setVeicolo(veicolo);

        if (request.getPagato()!=null)
            noleggioEntity.get().setPagato(request.getPagato());

        if (request.getNoleggiato()!=null)
            noleggioEntity.get().setNoleggiato(request.getNoleggiato());

        if (request.getIdcustomer()!=null)
            noleggioEntity.get().setCustomer(customer);

        if (request.getIdvendor()!=null)
            noleggioEntity.get().setVendor(vendor);
        this.noleggioRepo.saveAndFlush(noleggioEntity.get());

        return Either.right(entityToResponseNoleggio(noleggioEntity.get()));
    }

    public  List<Noleggio> findAllById (Long id) {
        return noleggioRepo.findAllById(id);
    }

}
