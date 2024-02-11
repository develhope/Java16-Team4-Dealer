package com.develhope.spring.users.userController;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.service.AdminServiceOrdine;
import com.develhope.spring.users.service.AdminServiceVeicolo;
import com.develhope.spring.users.service.AdminServicesNoleggio;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class AdminController {

    @Autowired
    private AdminServiceVeicolo adminServiceVeicolo;
    @Autowired
    VeicoloRepo veicoloRepo;
    @Autowired
    private AdminServiceOrdine adminServiceOrdine;
    @Autowired
    Utente utente;

    @Autowired
    Noleggio noleggio;
    @Autowired
    NoleggioRepo noleggioRepo;
    @Autowired
    AdminServicesNoleggio adminServicesNoleggio;

    @PostMapping("/addVeicolo")
    public Veicolo addVeicolo(@RequestBody Veicolo nuovoVeicolo) {
        return adminServiceVeicolo.addVeicolo(nuovoVeicolo);

    }

    @PatchMapping("/modVeicolo/{id}")
    public ResponseEntity<Veicolo> modVeicolo(@RequestBody Veicolo veicoloModificato) {
        Veicolo veicoloAggiornato = adminServiceVeicolo.modVeicolo(veicoloModificato.getId(), veicoloModificato);
        if (veicoloAggiornato != null && veicoloAggiornato.getId().equals(veicoloModificato.getId())) {
            return ResponseEntity.ok(veicoloAggiornato);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteVeicolo/{id}")
    public ResponseEntity<String> deleteVeicolo(Long id) {
        try {
            veicoloRepo.deleteById(id);
            return ResponseEntity.ok("Veicolo rimosso con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione del veicolo");
        }

    }

    @PostMapping("/creaOrdine/{id}")
    public Ordine_Acquisto createOrdine(@PathVariable Long id) {
        return adminServiceOrdine.createOrdineAcquisto(id);
    }

    @PatchMapping("/modificaOrdine/{id}")
    public Ordine_Acquisto modOrdineAcquisto(@RequestBody Ordine_Acquisto ordineAcquisto, @PathVariable Long id) {
        return adminServiceOrdine.patchOrdineAcquisto(id, ordineAcquisto.getAnticipo(), ordineAcquisto.isPagato(), ordineAcquisto.getStatoOrdine(), ordineAcquisto.getStatoVeicolo());
    }

    @DeleteMapping("deleteOrdine/{id}")
    public ResponseEntity<String> deleteOrdine(Long id) {
        return adminServiceOrdine.deleteOrdineAcquisto(id);
    }

    @PostMapping("/creaNoleggio/{id}")
    public Noleggio createNoleggio(@PathVariable Long id) {
        return adminServicesNoleggio.createNoleggioUtente(id);
    }

    @PatchMapping("/modificaNoleggio/{id}")
    public Noleggio modNoleggio(@RequestBody Noleggio noleggio, @PathVariable Long id) {
        return adminServicesNoleggio.patchNoleggio(id, noleggio.getDataInizio(), noleggio.getDataFine(), noleggio.getCostoGiornaliero(), noleggio.getCostoTotale(),noleggio.isPagato(),noleggio.isNoleggiato());
    }

    @DeleteMapping("deleteNoleggio/{id}")
    public ResponseEntity<String> deleteNoleggio(Long id) {
        return adminServicesNoleggio.deleteNoleggio(id);
    }


}