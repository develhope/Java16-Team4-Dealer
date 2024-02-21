package com.develhope.spring.users.service.adminServices;

import com.develhope.spring.transazioni.noleggio.dto.NoleggioRequest;
import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import com.develhope.spring.transazioni.noleggio.service.NoleggioService;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoVeicolo;
import com.develhope.spring.transazioni.ordine_acquisto.repository.Repository_OrdineAcquisto;
import com.develhope.spring.users.entity.TipoUtente;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.repository.UtenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Optional;

@Service
public class AdminServicesNoleggio {


    @Autowired
    NoleggioRepo noleggioRepo;
    @Autowired
    NoleggioService noleggioService;

    @Autowired
    Utente utente;
    @Autowired
    UtenteRepo utenteRepo;

    public Noleggio createNoleggioUtente(Long id,NoleggioRequest noleggioRequest) {
        Utente utente = utenteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));
        if (!utente.getTipoUtente().equals(TipoUtente.CUSTOMER)) {
            throw new RuntimeException("l'utente non è un cliente");
        } else {
            Noleggio nuovoNoleggio = new Noleggio();
            nuovoNoleggio.setDataInizio(noleggioRequest.getDataInizio());
            nuovoNoleggio.setDataFine(noleggioRequest.getDataFine());
            nuovoNoleggio.setCostoGiornaliero(noleggioRequest.getCostoGiornaliero());
            nuovoNoleggio.setCostoTotale(noleggioRequest.getCostoTotale());
            nuovoNoleggio.setPagato(noleggioRequest.getPagato());
            nuovoNoleggio.setNoleggiato(noleggioRequest.getNoleggiato());

            return nuovoNoleggio;
        }
    }

    public ResponseEntity<String> deleteNoleggio(@PathVariable Long id) {
        return noleggioService.deleteNoleggio(id);

    }

    public Noleggio patchNoleggio(Long id, OffsetDateTime inizio, OffsetDateTime fine, BigDecimal costoGiornaliero, BigDecimal costoTotale, boolean pagato, boolean noleggiato) {
        Optional<Noleggio> optionalNoleggio = noleggioRepo.findById(id);

        if (optionalNoleggio.isEmpty()) {
            throw new RuntimeException("il noleggio con ID " + id + " non trovato");
        }

        Noleggio noleggioModificato = optionalNoleggio.get();
        noleggioModificato.setDataInizio(inizio);
        noleggioModificato.setDataFine(fine);
        noleggioModificato.setCostoGiornaliero(costoGiornaliero);
        noleggioModificato.setCostoTotale(costoTotale);
        noleggioModificato.setPagato(pagato);
        noleggioModificato.setNoleggiato(noleggiato);

        return noleggioRepo.save(noleggioModificato);
    }


}
