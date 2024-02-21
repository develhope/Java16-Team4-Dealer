package com.develhope.spring.users.userController;

import com.develhope.spring.transazioni.noleggio.dto.NoleggioRequest;
import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.users.service.adminServices.AdminService;
import com.develhope.spring.users.service.adminServices.AdminServiceOrdine;
import com.develhope.spring.users.service.adminServices.AdminServiceUsers;
import com.develhope.spring.users.service.adminServices.AdminServicesNoleggio;
import com.develhope.spring.veichles.dto.VeicoloRequest;
import com.develhope.spring.veichles.dto.VeicoloResponse;
import com.develhope.spring.veichles.entity.Veicolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminServiceOrdine adminServiceOrdine;
    @Autowired
    Utente utente;
    @Autowired
    UtenteRepo utenteRepo;
    @Autowired
    AdminServiceUsers adminServiceUsers;

    @Autowired
    Noleggio noleggio;
    @Autowired
    NoleggioRepo noleggioRepo;
    @Autowired
    AdminServicesNoleggio adminServicesNoleggio;

    @PostMapping("/addVeicolo")
    public VeicoloResponse addVeicolo(@RequestBody VeicoloRequest veicoloRequest) {
        return adminService.createVeicolo(veicoloRequest);

    }

    @GetMapping("/veicolo/{id}")
    public VeicoloResponse getVeicolo(@PathVariable Long id){
        return this.adminService.getVeicolo(id);
        //return new VeicoloResponse(); per prendere il JSON pulito
    }

    @GetMapping("/veicoli")
    public List<VeicoloResponse> getAll(){
        return this.adminService.readAll();
    }

    @PutMapping("/modVeicolo/{id}")
    public ResponseEntity<VeicoloResponse> modVeicolo(@RequestBody VeicoloRequest veicoloRequest, @PathVariable Long id) {
        VeicoloResponse veicoloAggiornato = adminService.updateVeicolo(veicoloRequest,id);
        if (veicoloAggiornato != null ) {
            return ResponseEntity.ok(veicoloAggiornato);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteVeicolo/{id}")
    public ResponseEntity<String> deleteVeicolo(@PathVariable Long id) {
        try {

            return adminService.deleteVeicoloById(id)
                    ? ResponseEntity.ok("Veicolo rimosso con successo")
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veicolo non trovato");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione del veicolo");
        }

    }
////    @DeleteMapping("/deleteVeicolo/")
////    public ResponseEntity<String> deleteVeicolo(@RequestBody Veicolo veicolo) {
////        try {
////
////            return adminService.deleteVeicolo(veicolo)
////                    ? ResponseEntity.ok("Veicolo rimosso con successo")
////                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veicolo non trovato");
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione del veicolo");
////        }
//
//    }

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

    @PostMapping("/creaNoleggio")
    public Noleggio createNoleggio(@PathVariable Long id, @RequestBody NoleggioRequest noleggioRequest) {
        return adminServicesNoleggio.createNoleggioUtente(id,noleggioRequest);
    }

    @PatchMapping("/modificaNoleggio/{id}")
    public Noleggio modNoleggio(@RequestBody Noleggio noleggio, @PathVariable Long id) {
        return adminServicesNoleggio.patchNoleggio(id, noleggio.getDataInizio(), noleggio.getDataFine(), noleggio.getCostoGiornaliero(), noleggio.getCostoTotale(),noleggio.isPagato(),noleggio.isNoleggiato());
    }

    @PatchMapping("/modificaUtente/{id}")
    public ResponseEntity<String> modUtente (@PathVariable Long id){
        try {
            utenteRepo.deleteById(id);
            return ResponseEntity.ok("utente modificato con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la modifica del utente");
        }
    }

    @DeleteMapping("/deleteUtente/{id}")
    public ResponseEntity<String> deleteUtente (@PathVariable Long id){
        try {
            utenteRepo.deleteById(id);
            return ResponseEntity.ok("utente rimosso con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la modifica del utente");
        }
    }

}