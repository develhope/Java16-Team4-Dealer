package com.develhope.spring.users.service;

import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoVeicolo;
import com.develhope.spring.transazioni.ordine_acquisto.repository.Repository_OrdineAcquisto;
import com.develhope.spring.users.entity.TipoUtente;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AdminServiceOrdine {
    @Autowired
    public Ordine_Acquisto ordineAcquisto;

    @Autowired
    Repository_OrdineAcquisto repositoryOrdineAcquisto;

    @Autowired
    Utente utente;
    @Autowired
    UtenteRepo utenteRepo;

    public Ordine_Acquisto createOrdineAcquisto(Long id) {
        Utente utente = utenteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));
        if (!utente.getTipoUtente().equals(TipoUtente.CUSTOMER)) {
            throw new RuntimeException("l'utente non è un cliente");
        } else {
            Ordine_Acquisto nuovoOrdineAcquisto = new Ordine_Acquisto();
            nuovoOrdineAcquisto.setAnticipo(ordineAcquisto.getAnticipo());
            nuovoOrdineAcquisto.setStatoOrdine(ordineAcquisto.getStatoOrdine());
            nuovoOrdineAcquisto.setStatoVeicolo(ordineAcquisto.getStatoVeicolo());

            return nuovoOrdineAcquisto;
        }
    }

    public ResponseEntity<String> deleteOrdineAcquisto(@PathVariable Long id) {
        if (ordineAcquisto == null) {
            return ResponseEntity.notFound().build();
        } else if (!ordineAcquisto.getId().equals(id)) {
            return ResponseEntity.badRequest().body("L'id dell'ordine non corrisponde");
        } else {
            repositoryOrdineAcquisto.deleteById(id);
            return ResponseEntity.ok("Ordine cancellato");
        }

    }

    public Ordine_Acquisto patchOrdineAcquisto(Long id, BigDecimal nuovoAnticipo, boolean nuovoPagato, StatoOrdine nuovoStatoOrdine, StatoVeicolo nuovoStatoVeicolo) {
        Optional<Ordine_Acquisto> optionalOrdineAcquisto = repositoryOrdineAcquisto.findById(id);

        if (optionalOrdineAcquisto.isEmpty()) {
            throw new RuntimeException("Ordine con ID " + id + " non trovato");
        }

        Ordine_Acquisto ordineAcquisto = optionalOrdineAcquisto.get();
        ordineAcquisto.setAnticipo(nuovoAnticipo);
        ordineAcquisto.setPagato(nuovoPagato);
        ordineAcquisto.setStatoOrdine(nuovoStatoOrdine);
        ordineAcquisto.setStatoVeicolo(nuovoStatoVeicolo);


        return repositoryOrdineAcquisto.save(ordineAcquisto);
    }

}

