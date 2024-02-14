package com.develhope.spring.users.service.vendorServices;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
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
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public class VendorService {

    @Autowired
    private VeicoloRepo veicoloRepo;
    @Autowired
    private Veicolo veicolo;

    @Autowired
    private Ordine_Acquisto ordineAcquisto;

    @Autowired
    private Repository_OrdineAcquisto repositoryOrdineAcquisto;

    public Optional<Veicolo> dettagliVeicolo (Long id) {
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
}
