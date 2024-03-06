package com.develhope.spring.veichles.controllers;


import com.develhope.spring.shared.Error;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.veichles.dto.VeicoloRequest;
import com.develhope.spring.veichles.dto.VeicoloResponse;
import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.service.VeicoloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class VeicoloController {

    @Autowired
    private VeicoloService veicoloService;


    @Operation(summary = "Ottieni veicolo per ID", description = "Recupera un veicolo specifico in base al suo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "veicolo recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "511", description = "veicolo non trovato")
    })
    @GetMapping("/veicolo/{id}")
    public ResponseEntity<?> getVeicolo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.veicoloService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(511, "veicolo non trovato"));

        }

        // return new VeicoloResponse(); //per prendere il JSON pulito
    }


    @Operation(summary = "Ottieni tutti i veicoli", description = "Recupera tutti i veicoli nel sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elenco di tutti i veicoli"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/veicoli")
    public List<VeicoloResponse> getAll() {
        return this.veicoloService.readAll();
    }


    @Operation(summary = "Ottieni veicolo per marca", description = "Recupera tutti i veicoli in base alla marca.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/findveicoliByMarca")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByMarca(@RequestParam(name = "marca") String marca) {
        try {
            List<VeicoloResponse> response = this.veicoloService.readAllByMarca(marca);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Ottieni veicolo per modello", description = "Recupera tutti i veicoli in base al modello.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/findveicoliByModello")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByModello(@RequestParam(name = "modello") String modello) {
        try {
            List<VeicoloResponse> response = this.veicoloService.readAllByModello(modello);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Ottieni veicolo per prezzo massimo", description = "Recupera tutti i veicoli in base al prezzo massimo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/findveicoliByPrezzoMax")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByPrezzoMax(@RequestParam(name = "prezzoMax") BigDecimal prezzoMax) {
        try {
            List<VeicoloResponse> response = this.veicoloService.readAllByPrezzoMax(prezzoMax);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Ottieni veicolo usato", description = "Recupera tutti i veicoli usati.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/findveicoliByUsato")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByUsato(@RequestParam(name = "usato") boolean usato) {
        try {
            List<VeicoloResponse> response = this.veicoloService.readAllByUsato(usato);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Ottieni veicolo in base allo stato vendita", description = "Recupera tutti i veicoli in base allo stato vendita.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/findveicoliByStatoVendita")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByStatoVendita(@RequestParam(name = "statoVendita") StatoVendita statoVendita) {
        try {
            List<VeicoloResponse> response = this.veicoloService.findAllByStatoVendita(statoVendita);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Ottieni veicolo in base allo stato vendita in ordine ascendente", description = "Recupera tutti i veicoli in base allo stato vendita in ordine ascendente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping("/findveicoli/perstatovendita")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByStatoVenditaAsc() {
        try {
            List<VeicoloResponse> response = this.veicoloService.findAllByStatoVenditaAsc();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Ottieni veicolo in base ad uno specifico range di prezzo", description = "Recupera tutti i veicoli in base ad uno specifico range di prezzo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })

    @GetMapping("/findveicoliByRange")
    public ResponseEntity<List<VeicoloResponse>> findAllByRange(
            @RequestParam(name = "min") BigDecimal min,
            @RequestParam(name = "max") BigDecimal max) {
        try {
            List<VeicoloResponse> response = this.veicoloService.findAllByRange(min, max);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Modifica stato veicolo", description = "Modifica stato di un veicolo tramite ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo modificato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "511", description = "Veicolo non trovato")
    })
    @PatchMapping("/usato")
    public ResponseEntity<?> patchStatoUsato(
            @RequestParam(name = "isUsato") Boolean isUsato,
            @RequestParam(name = "idVeicolo") Long idVeicolo) {
        try {
            VeicoloResponse response = this.veicoloService.patchStatoUsato(idVeicolo, isUsato);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(511, "veicolo non trovato"));
        }
    }


    @Operation(summary = "Crea un nuovo veicolo", description = "Crea un nuovo veicolo in base alle informazioni fornite nel corpo della richiesta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo creato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Errore interno del server")
    })
    @PostMapping("/addVeicolo")
    public VeicoloResponse addVeicolo(@AuthenticationPrincipal Utente user, @RequestBody VeicoloRequest veicoloRequest) {
        System.out.println(user);
        return veicoloService.createVeicolo(veicoloRequest);

    }


    @Operation(summary = "Aggiorna l'intero veicolo", description = "Aggiorna l'intero veicolo in base al corpo della richiesta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo aggiornato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "511", description = "veicolo non trovato")

    })
    @PutMapping("/updateveicolo/{id}")
    public ResponseEntity<?> updateVeicolo(@RequestBody VeicoloRequest veicoloRequest, @PathVariable Long id) {
        VeicoloResponse veicoloAggiornato = veicoloService.updateVeicolo(veicoloRequest, id);
        if (veicoloAggiornato != null) {
            return ResponseEntity.ok(veicoloAggiornato);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(511, "veicolo non trovato"));
        }
    }


    @Operation(summary = "Aggiorna informazioni parziali del veicolo", description = "Aggiorna informazioni specifiche del veicolo in base al corpo della richiesta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo modificato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "511", description = "Veicolo non trovato")

    })
    @PatchMapping("/modificaveicolo/{id}")
    public ResponseEntity<?> patchVeicolo(@RequestBody VeicoloRequest veicoloRequest, @PathVariable Long id) {
        try {
            VeicoloResponse response = this.veicoloService.patchByVeicoloRequest(id, veicoloRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(511, "veicolo non trovato"));
        }
    }


    @Operation(summary = "Elimina un veicolo", description = "Elimina un veicolo specifico in base al suo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veicolo eliminato correttamente"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "511", description = "Veicolo non trovato")

    })
    @DeleteMapping("/deleteVeicolo/{id}")
    public ResponseEntity<?> deleteVeicolo(@PathVariable Long id) {
        try {

            return veicoloService.deleteVeicoloById(id)
                    ? ResponseEntity.ok("Veicolo rimosso con successo")
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veicolo non trovato");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error(511, "veicolo non trovato"));

        }
    }


}
