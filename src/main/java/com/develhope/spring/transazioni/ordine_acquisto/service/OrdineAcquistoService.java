package com.develhope.spring.transazioni.ordine_acquisto.service;

import com.develhope.spring.shared.Error;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoModel;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoRequest;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.entity.TipoTransazione;
import com.develhope.spring.transazioni.ordine_acquisto.repository.Repository_OrdineAcquisto;
import com.develhope.spring.users.entity.TipoUtente;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import com.develhope.spring.veichles.service.VeicoloService;
import io.vavr.control.Either;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdineAcquistoService {

    @Autowired
    private Repository_OrdineAcquisto repositoryOrdineAcquisto;

    @Autowired
    private OrdineAcquistoModel mapper;

    @Autowired
    private VeicoloRepo veicoloRepo;

    @Autowired
    private VeicoloService veicoloService;

    @Autowired
    private UtenteRepo utenteRepo;


    private Either<Error,Ordine_Acquisto> getOAById(Long id) {
        Optional<Ordine_Acquisto> oA = this.repositoryOrdineAcquisto.findById(id);
        if (oA.isEmpty())
            return Either.left(new Error(510,"Ordine o Acquisto non trovato"));
        return Either.right(oA.get());
    }


    private Either<Error,Utente> getUtenteById(long id) {
        Optional<Utente> utente = this.utenteRepo.findById(id);
        if (utente.isEmpty())
            return Either.left(new Error(512,"Utente non trovato"));

        return Either.right(utente.get());
    }


    private Either<Error,Veicolo> getVeicoloById(long id) {
        Optional<Veicolo> veicolo = this.veicoloRepo.findById(id);
        if (veicolo.isEmpty())
            return Either.left(new Error(511,"Veicolo non trovato"));
        return Either.right(veicolo.get());
    }

    private Ordine_Acquisto requestToEntity(OrdineAcquistoRequest request) {
        return mapper.convertModelToEnt(mapper.convertRequestToMod(request));
    }

    private OrdineAcquistoResponse entityToResponse(Ordine_Acquisto entity) {
        return mapper.convertModelToResponse(mapper.convertEntToModel(entity));
    }




    private Either<Error,Veicolo> veicoloStatoOrdineCheck(Long idVeicolo, TipoTransazione tipoTransazione) {
        Either<Error,Veicolo> veicolo = getVeicoloById(idVeicolo);
        if (veicolo.isLeft()) return  Either.left(veicolo.getLeft());
        if (tipoTransazione == TipoTransazione.ORDINE && veicolo.get().getStatoVendita() == StatoVendita.ORDINABILE)
            return Either.right(veicolo.get());

        if (tipoTransazione == TipoTransazione.ACQUISTO && veicolo.get().getStatoVendita() == StatoVendita.ACQUISTABILE)
            return Either.right(veicolo.get());

        return Either.left(new Error(513,"Veicolo non eleggibile per la richiesta fatta"));

    }


    private Either<Error,Utente> utenteTipoUtenteCheck(Long idUtente, TipoUtente tipoUtente) {
        Either<Error,Utente> utente = getUtenteById(idUtente);
        if (utente.isLeft())
            return Either.left(utente.getLeft());
        if (utente.get().getTipoUtente() != tipoUtente)
            return Either.left(new Error(514,"Utente non eleggibile per la richiesta fatta"));
        return Either.right(utente.get());
    }

    @SneakyThrows
    private Either<Error,Utente> utenteTipoUtenteCheck(Long idUtente, TipoUtente tipoUtente, TipoUtente tipoUtente2) {
        Either<Error,Utente> utente = getUtenteById(idUtente);
        if (utente.isLeft()) return Either.left(utente.getLeft());
        if (utente.get().getTipoUtente() != tipoUtente
                || utente.get().getTipoUtente() != tipoUtente2)
            throw new IOException("Utente non eleggibile per la richiesta fatta");
        return utente;
    }


    private Ordine_Acquisto buildOA(OrdineAcquistoRequest request, Utente customer, Utente vendor, Veicolo vToBuy) {
        return Ordine_Acquisto.builder()
                .anticipo(request.getAnticipo())
                .pagato(request.getPagato())
                .customer(customer)
                .vendor(vendor)
                .veicolo(vToBuy)
                .statoOrdine(request.getStatoOrdine())
                .tipoTransazione(request.getTipoTransazione())
                .build();
    }


    public Either<Error,OrdineAcquistoResponse> findOAById(Long id) {
        Either<Error,Ordine_Acquisto> oa = getOAById(id);
        if (oa.isLeft())
            return Either.left(oa.getLeft());
        return Either.right(entityToResponse(oa.get()));
    }


    public List<OrdineAcquistoResponse> findAllOA() {
        return this.repositoryOrdineAcquisto.findAll()
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }


    public List<OrdineAcquistoResponse> findAllOAByStatoOrdine(StatoOrdine statoOrdine) {
        return this.repositoryOrdineAcquisto.findAllByStatoOrdine(statoOrdine)
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }


    public Either<Error,List<OrdineAcquistoResponse>> findAllOAById(Long idCustomer){
        Either <Error, Utente> utente = getUtenteById(idCustomer);
        if (utente.isLeft()) return Either.left(utente.getLeft());
        return Either.right(this.repositoryOrdineAcquisto.findAllByCustomerIdOrderByPagatoAsc(idCustomer).stream().map(this::entityToResponse).collect(Collectors.toList()));
    }

    public List<OrdineAcquistoResponse> findAllOAByStatoOrdineAsc() {
        return this.repositoryOrdineAcquisto.findAllByOrderByStatoOrdineAsc()
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }

    public Either<Error,OrdineAcquistoResponse> createOA(OrdineAcquistoRequest requestA,
                                                         Long idCustomer,
                                                         Long idVeicolo,
                                                         Long idVendor) {

        Either<Error,Veicolo> vToBuy = veicoloStatoOrdineCheck(idVeicolo, requestA.getTipoTransazione());
        if (vToBuy.isLeft())
            return Either.left(vToBuy.getLeft());

        Either<Error,Utente> customer = utenteTipoUtenteCheck(idCustomer, TipoUtente.CUSTOMER);
        if (customer.isLeft())
            return Either.left(customer.getLeft());

        Ordine_Acquisto acquisto;

        if (idVendor != null) {
            Either<Error, Utente> vendor = utenteTipoUtenteCheck(idVendor, TipoUtente.VENDOR);
            System.out.println(vendor.toString());
            if (vendor.isLeft())
                return Either.left(vendor.getLeft());
            acquisto = buildOA(requestA, customer.get(), vendor.get(), vToBuy.get());
        } else {
             acquisto = buildOA(requestA, customer.get(), null, vToBuy.get());
        }

        this.repositoryOrdineAcquisto.saveAndFlush(acquisto);
        if (requestA.getPagato()) this.veicoloService.immatricola(vToBuy.get());

        return Either.right(entityToResponse(acquisto));
    }

    public Either<Error,OrdineAcquistoResponse> ordineToAcquisto(Long idOrdine,
                                                   Long idAdmin) throws IOException {
        try {
            Either<Error,Utente> admin = utenteTipoUtenteCheck(idAdmin, TipoUtente.ADMIN);
            if (admin.isLeft()) return Either.left(admin.getLeft());
            Either<Error,Ordine_Acquisto> ordine = getOAById(idOrdine);
            if (ordine.isLeft()) return Either.left(ordine.getLeft());
            ordine.get().setStatoOrdine(StatoOrdine.ACQUISTATO);
            ordine.get().setPagato(true);
            ordine.get().setTipoTransazione(TipoTransazione.ACQUISTO);
            this.veicoloService.immatricola(ordine.get().getVeicolo()); //bisogna capire se cambia effettivamente il veicolo
            return Either.right(entityToResponse(repositoryOrdineAcquisto.saveAndFlush(ordine.get())));
        }catch (Exception e ){
            throw new IOException("Questo utente non può effettuare questa operazione");
        }
    }


    public Either<Error,OrdineAcquistoResponse> updateOA(OrdineAcquistoRequest oaRequest, Long id) {
        Either<Error,Ordine_Acquisto> oa = getOAById(id);
        if (oa.isLeft())return Either.left(oa.getLeft());
        Ordine_Acquisto request = requestToEntity(oaRequest);

        oa.get().setAnticipo(request.getAnticipo());
        oa.get().setPagato(request.isPagato());
        oa.get().setCustomer(request.getCustomer());
        oa.get().setVendor(request.getVendor());
        oa.get().setVeicolo(request.getVeicolo());
        oa.get().setStatoOrdine(request.getStatoOrdine());
        oa.get().setTipoTransazione(request.getTipoTransazione());
        this.repositoryOrdineAcquisto.saveAndFlush(oa.get());

        return Either.right(entityToResponse(oa.get()));
    }

    public Either<Error,OrdineAcquistoResponse> patchOA(OrdineAcquistoRequest request, Long id) {
        Either<Error,Ordine_Acquisto> oa = getOAById(id);
        if (oa.isLeft())return Either.left(oa.getLeft());

        if (request.getAnticipo() != null)
            oa.get().setAnticipo(request.getAnticipo());

        if (request.getPagato() != null)
            oa.get().setPagato(request.getPagato());

        if (request.getCustomer() != null)
            oa.get().setCustomer(request.getCustomer());

        if (request.getVendor() != null)
            oa.get().setVendor(request.getVendor());

        if (request.getVeicolo() != null)
            oa.get().setVeicolo(request.getVeicolo());

        if (request.getStatoOrdine() != null)
            oa.get().setStatoOrdine(request.getStatoOrdine());

        if (request.getTipoTransazione() != null)
            oa.get().setTipoTransazione(request.getTipoTransazione());

        this.repositoryOrdineAcquisto.saveAndFlush(oa.get());

        return Either.right(entityToResponse(oa.get()));
    }

    public Either<Error,String> verifyOrderById(Long id) {
            Either<Error,Ordine_Acquisto> oaToVerify = getOAById(id);
            if (oaToVerify.isLeft()) return Either.left(oaToVerify.getLeft());

            if (oaToVerify.get().getStatoOrdine()==null)
                return Either.left(new Error(515,"Stato ordine non riconosciuto: " + oaToVerify.get().getStatoOrdine()));

            switch (oaToVerify.get().getStatoOrdine()) {
                case ORDINATO:
                    return Either.right("il Veicolo è stato ordinato con successo");
                case IN_CONSEGNA:
                    return Either.right("il Veicolo è in consegna");
                case CONSEGNATO:
                    return Either.right("il Veicolo è consegnato al concessionario e pronto al ritiro");
                case ACQUISTATO:
                    return Either.right("il veicolo è stato acquistato dal cliente");
                default:
                    return Either.left(new Error(515,"Stato ordine non riconosciuto: " + oaToVerify.get().getStatoOrdine()));
            }
    }

    public Either<Error,ResponseEntity<String>> updateStatoOrdine(Long id, StatoOrdine statoOrdine){

            Either<Error,Ordine_Acquisto> oaToUpdateStatus = getOAById(id);
            if (oaToUpdateStatus.isLeft()) return Either.left(oaToUpdateStatus.getLeft());

            oaToUpdateStatus.get().setStatoOrdine(statoOrdine);
            this.repositoryOrdineAcquisto.saveAndFlush(oaToUpdateStatus.get());
            return Either.right(ResponseEntity.ok().body("Stato ordine Cambiato con successo in: "+oaToUpdateStatus.get().getStatoOrdine()));

    }

    public Either<Error,Boolean> deleteOA(long id) {
        Either<Error,Ordine_Acquisto> oaToUpdateStatus = getOAById(id);
        if (oaToUpdateStatus.isLeft()) return Either.left(oaToUpdateStatus.getLeft());
        this.repositoryOrdineAcquisto.deleteById(id);
        return Either.right(true);
    }
}
