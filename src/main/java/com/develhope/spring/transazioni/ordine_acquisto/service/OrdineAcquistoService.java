package com.develhope.spring.transazioni.ordine_acquisto.service;

import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoModel;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoRequest;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoVeicolo;
import com.develhope.spring.transazioni.ordine_acquisto.repository.Repository_OrdineAcquisto;
import com.develhope.spring.users.entity.TipoUtente;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class OrdineAcquistoService {

    @Autowired
    private Repository_OrdineAcquisto repositoryOrdineAcquisto;

    @Autowired
    private OrdineAcquistoModel mapper;

    @Autowired
    private VeicoloRepo veicoloRepo;

    @Autowired
    private UtenteRepo utenteRepo;

    @SneakyThrows
    private Ordine_Acquisto getOAById(Long id) {
        Optional<Ordine_Acquisto> oA = this.repositoryOrdineAcquisto.findById(id);
        if (oA.isEmpty()) throw new IOException("Ordine o Acquisto non trovato");
        return oA.get();
    }

    private Ordine_Acquisto requestToEntity(OrdineAcquistoRequest request) {
        return mapper.convertModelToEnt(mapper.convertRequestToMod(request));
    }

    private OrdineAcquistoResponse entityToResponse(Ordine_Acquisto entity) {
        return mapper.convertModelToResponse(mapper.convertEntToModel(entity));
    }

    @SneakyThrows
    private Veicolo getVeicoloById(long id) {
        Optional<Veicolo> veicolo = this.veicoloRepo.findById(id);
        if (veicolo.isEmpty()) throw new IOException("Veicolo non trovato");
        return veicolo.get();
    }

    @SneakyThrows
    private Veicolo veicoloStatoOrdineCheck(Long idVeicolo, StatoVeicolo statoVeicolo) {
        Veicolo veicolo = getVeicoloById(idVeicolo);

        if (statoVeicolo == StatoVeicolo.ORDINATO && veicolo.getStatoVendita() == StatoVendita.ORDINABILE)
            return veicolo;

        if (statoVeicolo == StatoVeicolo.ACQUISTATO && veicolo.getStatoVendita() == StatoVendita.ACQUISTABILE)
            return veicolo;

        throw new IOException("Veicolo non eleggibile per la richiesta fatta");

    }

    @SneakyThrows
    private Utente utenteTipoUtenteCheck(Long idUtente, TipoUtente tipoUtente) {
        Utente utente = getUtenteById(idUtente);
        if (utente.getTipoUtente() != tipoUtente) throw new IOException("Utente non eleggibile per la richiesta fatta");
        return utente;
    }

    @SneakyThrows
    private Utente utenteTipoUtenteCheck(Long idUtente, TipoUtente tipoUtente, TipoUtente tipoUtente2) {
        Utente utente = getUtenteById(idUtente);
        if (utente.getTipoUtente() != tipoUtente
                || utente.getTipoUtente() != tipoUtente2)
            throw new IOException("Utente non eleggibile per la richiesta fatta");
        return utente;
    }

    @SneakyThrows
    private Utente getUtenteById(long id) {
        Optional<Utente> veicolo = this.utenteRepo.findById(id);
        if (veicolo.isEmpty()) throw new IOException("Utente non trovato");
        return veicolo.get();
    }

    private Ordine_Acquisto buildOA(OrdineAcquistoRequest request, Utente customer, Utente vendor, Veicolo vToBuy) {
        return Ordine_Acquisto.builder()
                .anticipo(request.getAnticipo())
                .pagato(request.getPagato())
                .customer(customer)
                .vendor(vendor)
                .veicolo(vToBuy)
                .statoOrdine(request.getStatoOrdine())
                .statoVeicolo(request.getStatoVeicolo())
                .build();
    }

    @SneakyThrows
    public Ordine_Acquisto findOAById(Long id) {
        return getOAById(id);
    }

    @SneakyThrows
    public List<Ordine_Acquisto> findAllOA() {
        return this.repositoryOrdineAcquisto.findAll();
    }

    @SneakyThrows
    public List<Ordine_Acquisto> findAllOAByStatoOrdine(StatoOrdine statoOrdine) {
        return this.repositoryOrdineAcquisto.findAllByStatoOrdine(statoOrdine);
    }
    @SneakyThrows
    public List<Ordine_Acquisto> findAllOAByStatoOrdineAsc(StatoOrdine statoOrdine) {
        return this.repositoryOrdineAcquisto.findAllByStatoOrdine(statoOrdine);
    }

//    public OrdineAcquistoResponse createOA(OrdineAcquistoRequest request) {
//        Ordine_Acquisto oa = requestToEntity(request);
//        this.repositoryOrdineAcquisto.save(oa);
//        return entityToResponse(oa);
//    }

//    @SneakyThrows
//    public OrdineAcquistoResponse createAcquisto(OrdineAcquistoRequest requestA,
//                                                 Long idCustomer,
//                                                 Long idVeicolo) {
//        Veicolo vToBuy = veicoloStatoOrdineCheck(idVeicolo, StatoVendita.ACQUISTABILE);
//        Utente customer = utenteTipoUtenteCheck(idCustomer, TipoUtente.CUSTOMER, TipoUtente.ADMIN);
//
//        Ordine_Acquisto acquisto = buildOA(requestA,customer,null,vToBuy);
//        this.repositoryOrdineAcquisto.saveAndFlush(acquisto);
//
//        return entityToResponse(acquisto);
//    }

    @SneakyThrows
    public OrdineAcquistoResponse createOA(OrdineAcquistoRequest requestA,
                                           Long idCustomer,
                                           Long idVeicolo,
                                           Long idVendor) {
        Veicolo vToBuy = veicoloStatoOrdineCheck(idVeicolo, requestA.getStatoVeicolo());
        Utente customer = utenteTipoUtenteCheck(idCustomer, TipoUtente.CUSTOMER);
        Utente vendor = null;
        if (idVendor != null) {
            vendor = utenteTipoUtenteCheck(idVendor, TipoUtente.VENDOR);
        }

        Ordine_Acquisto acquisto = buildOA(requestA, customer, vendor, vToBuy);
        this.repositoryOrdineAcquisto.saveAndFlush(acquisto);

        return entityToResponse(acquisto);
    }

//    public OrdineAcquistoResponse createAcquisto(OrdineAcquistoRequest request) {
//        Ordine_Acquisto oa = requestToEntity(request);
//        this.repositoryOrdineAcquisto.save(oa);
//        return entityToResponse(oa);
//    }

    public OrdineAcquistoResponse updateOA(OrdineAcquistoRequest request, Long id) {
        Ordine_Acquisto oa = getOAById(id);

        oa.setAnticipo(request.getAnticipo());
        oa.setPagato(request.getPagato());
        oa.setCustomer(request.getCustomer());
        oa.setVendor(request.getVendor());
        oa.setVeicolo(request.getVeicolo());
        oa.setStatoOrdine(request.getStatoOrdine());
        oa.setStatoVeicolo(request.getStatoVeicolo());
        this.repositoryOrdineAcquisto.saveAndFlush(oa);

        return entityToResponse(oa);
    }

    public OrdineAcquistoResponse patchOA(OrdineAcquistoRequest request, Long id) {
        Ordine_Acquisto oa = getOAById(id);

        if (request.getAnticipo() != null)
            oa.setAnticipo(request.getAnticipo());

        if (request.getPagato() != null)
            oa.setPagato(request.getPagato());

        if (request.getCustomer() != null)
            oa.setCustomer(request.getCustomer());

        if (request.getVendor() != null)
            oa.setVendor(request.getVendor());

        if (request.getVeicolo() != null)
            oa.setVeicolo(request.getVeicolo());

        if (request.getStatoOrdine() != null)
            oa.setStatoOrdine(request.getStatoOrdine());

        if (request.getStatoVeicolo() != null)
            oa.setStatoVeicolo(request.getStatoVeicolo());

        this.repositoryOrdineAcquisto.saveAndFlush(oa);

        return entityToResponse(oa);
    }

    public ResponseEntity<String> verifyOrderById(Long id) {
        try {
            Ordine_Acquisto oaToVerify = getOAById(id);

            switch (oaToVerify.getStatoOrdine()) {
                case ORDINATO:
                    return ResponseEntity.ok("il Veicolo è stato ordinato con successo");
                case IN_CONSEGNA:
                    return ResponseEntity.ok("il Veicolo è in consegna");

                case CONSEGNATO:
                    return ResponseEntity.ok("il Veicolo è consegnato al concessionario e pronto al ritiro");
                case ACQUISTATO:
                    return ResponseEntity.ok("il veicolo è stato acquistato dal cliente");
                default:
                    return ResponseEntity.badRequest().body("Stato ordine non riconosciuto: " + oaToVerify.getStatoOrdine());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordine non trovato");
        }
    }

    public ResponseEntity<String> updateStatoOrdine(Long id, StatoOrdine statoOrdine){
        try {
            Ordine_Acquisto oaToUpdateStatus = getOAById(id);
            oaToUpdateStatus.setStatoOrdine(statoOrdine);
            this.repositoryOrdineAcquisto.saveAndFlush(oaToUpdateStatus);
            return ResponseEntity.ok("Stato ordine Cambiato con successo in: "+oaToUpdateStatus.getStatoOrdine());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordine non trovato");
        }
    }

    public boolean deleteOA(long id) {
        Ordine_Acquisto oa = getOAById(id);
        this.repositoryOrdineAcquisto.deleteById(id);
        return true;
    }
}
