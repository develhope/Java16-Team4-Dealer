package com.develhope.spring.users.service.vendorServices;

import ch.qos.logback.classic.Logger;
import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoVeicolo;
import com.develhope.spring.transazioni.ordine_acquisto.repository.Repository_OrdineAcquisto;
import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.entity.TipoVeicolo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private VeicoloRepo veicoloRepo;
    @Autowired
    private Veicolo veicolo;

    @Autowired
    private Ordine_Acquisto ordineAcquisto;

    @Autowired
    private Repository_OrdineAcquisto repositoryOrdineAcquisto;

    public Optional<Veicolo> dettagliVeicolo(Long id) {
        return veicoloRepo.findById(id);
    }

    public ResponseEntity<String> nuovoOrdineByIdVeicolo(Long id, Ordine_Acquisto ordineAcquisto) {
        try {
            Optional<Veicolo> veicoloOptional = veicoloRepo.findById(id);
            if (!veicoloOptional.isPresent()) {
                ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Veicolo non trovato");
            }
            Veicolo veicolo = veicoloOptional.get();
            if (!veicolo.getStatoVendita().equals(StatoVendita.ORDINABILE)) {
                ResponseEntity.status(HttpStatusCode.valueOf(500)).body("Veicolo non ordinabile");
            }

            Ordine_Acquisto nuovoOrdine = new Ordine_Acquisto();
            nuovoOrdine.setAnticipo(ordineAcquisto.getAnticipo());
            nuovoOrdine.setPagato(ordineAcquisto.isPagato());
            nuovoOrdine.setStatoOrdine(ordineAcquisto.getStatoOrdine());
            nuovoOrdine.setStatoVeicolo(ordineAcquisto.getStatoVeicolo());
            repositoryOrdineAcquisto.save(nuovoOrdine);

            return ResponseEntity.ok("Ordine effettuato con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Si è verificato un errore durante la creazione dell'ordine: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteOrdineAcquisto(@PathVariable Long id) {
        try {
            if (ordineAcquisto == null) {
                throw new NullPointerException("L'ordine selezionato è null");
            } else if (!ordineAcquisto.getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordine di Acquisto non trovato");
            } else {
                repositoryOrdineAcquisto.deleteById(id);
                return ResponseEntity.ok("Ordine cancellato correttamente");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Si è verificato un errore durante l'eliminazione dell'ordine: " + e.getMessage());
        }
    }

    public ResponseEntity<String> modificaOrdineAcquisto(@PathVariable Long id) {
        try {
            if (ordineAcquisto == null) {
                throw new NullPointerException("L'ordine selezionato è null");
            } else if (!ordineAcquisto.getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordine di Acquisto non trovato");
            } else {
                Ordine_Acquisto nuovoOrdine = new Ordine_Acquisto();
                nuovoOrdine.setAnticipo(ordineAcquisto.getAnticipo());
                nuovoOrdine.setPagato(ordineAcquisto.isPagato());
                nuovoOrdine.setStatoOrdine(ordineAcquisto.getStatoOrdine());
                nuovoOrdine.setStatoVeicolo(ordineAcquisto.getStatoVeicolo());
                repositoryOrdineAcquisto.save(nuovoOrdine);
                return ResponseEntity.ok("Ordine modificato correttamente");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Si è verificato un errore durante la modifica dell'ordine: " + e.getMessage());
        }
    }

    public ResponseEntity<String> checkOrdineById(@PathVariable Long id) {
        try {
            Optional<Ordine_Acquisto> ordine_acquisto = repositoryOrdineAcquisto.findById(id);
            if (ordineAcquisto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordine non trovato");
            }
            switch (ordineAcquisto.getStatoOrdine()) {
                case ORDINATO:
                    return ResponseEntity.ok("il Veicolo è stato ordinato con successo");
                case IN_CONSEGNA:
                    return ResponseEntity.ok("il Veicolo è in consegna");

                case CONSEGNATO:
                    return ResponseEntity.ok("il Veicolo è consegnato al concessionario e pronto al ritiro");
                case ACQUISTATO:
                    return ResponseEntity.ok("il veicolo è stato acquistato dal cliente");
                default:
                    return ResponseEntity.badRequest().body("Stato ordine non riconosciuto: " + ordine_acquisto.get().getStatoOrdine());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il controllo dell'ordine con id: " + id);
        }
    }

    public ResponseEntity<String> updateStatoOrdine(@PathVariable Long id) {
        if (!repositoryOrdineAcquisto.findById(id).isPresent()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("l'ordine con id= " + id + " non esiste");
        } else if (ordineAcquisto.getId() == null) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("l'ordine è null");

        }
        Ordine_Acquisto ordineToUpdate = repositoryOrdineAcquisto.findById(id).get();
        ordineToUpdate.setStatoOrdine(ordineAcquisto.getStatoOrdine());
        repositoryOrdineAcquisto.save(ordineToUpdate);

        return ResponseEntity.ok("Lo stato dell'ordine è stato aggiornato a: " + ordineToUpdate.getStatoOrdine().toString());
    }

    public List<Ordine_Acquisto> getOrdiniByStatus(StatoOrdine statoOrdine) {
        if (!statoOrdine.equals(StatoOrdine.ACQUISTATO)
                || statoOrdine.equals(StatoOrdine.CONSEGNATO)
                || statoOrdine.equals(StatoOrdine.IN_CONSEGNA)
                || statoOrdine.equals(StatoOrdine.ORDINATO)) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stato ordine non valido! inserisci una di queste opzioni valide: "
                    + StatoOrdine.ACQUISTATO.toString() + "\n"
                    + StatoOrdine.CONSEGNATO.toString() + "\n"
                    + StatoOrdine.IN_CONSEGNA.toString() + "\n"
                    + StatoOrdine.ORDINATO.toString() + "\n"
            );
        }
        return repositoryOrdineAcquisto.findAllByStatoOrdine(statoOrdine);
    }
}

