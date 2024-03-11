package com.develhope.spring.transazioni.ordine_acquisto.controller;

import com.develhope.spring.shared.Error;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoRequest;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.service.OrdineAcquistoService;
import com.develhope.spring.users.entity.TipoUtente;
import com.develhope.spring.users.entity.Utente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/Acquisto")
//@PreAuthorize
public class Controller_Ordine_Acquisto {

    @Autowired
    private OrdineAcquistoService ordineAcquistoService;

    @Operation(summary = "Ottieni ordine/acquisto", description = "Recupera un ordine/acquisto specifico in base al suo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "510", description = "Ordine/Acquisto non trovato")

    })
    @GetMapping("/oa/{id}")
    public ResponseEntity<?> findOAById(@PathVariable Long id) {
        Either<Error,OrdineAcquistoResponse> request =ordineAcquistoService.findOAById(id);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }


    @Operation(summary = "Ottieni tutti gli ordini/acquisti", description = "Recupera tutti gli ordini/acquisti nel sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/oa/all")
    public List<OrdineAcquistoResponse> findAllOA() {
        return ordineAcquistoService.findAllOA();
    }


    @Operation(summary = "Verifica stato ordine/acquisto", description = "Verifica stato ordine/acquisto in base al suo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verifica stato ordine/acquisto avvenuta correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "510", description = "Ordine/Acquisto non trovato")
    })
            @GetMapping("/oa/verifyorderstatus/{idoa}")
    public ResponseEntity<?> verifyOrderStatus(@PathVariable Long idoa){
        //TODO: controllare cosa succede, sono troppo stanco ora
        Either<Error,String> request = ordineAcquistoService.verifyOrderById(idoa);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }

    @Operation(summary = "Ottieni tutti gli ordini/acquisti di un utente", description = "Recupera tutti gli ordini/acquisti nel sistema tramite l'ID dell'utente inserito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "512", description = "Utente non trovato")
    })
    @GetMapping("/oa/customeroa/{idcustomer}")
    public ResponseEntity<?> findAllOAByIdUser(@PathVariable Long idcustomer) {
        Either<Error,List<OrdineAcquistoResponse>> request =ordineAcquistoService.findAllOAById(idcustomer);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }

    }


    @Operation(summary = "Ottieni tutti gli ordini/acquisti tramite lo stato", description = "Recupera tutti gli ordini/acquisti nel sistema tramite lo status dell'ordine inserito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/oa/allbystatus")
    public List<OrdineAcquistoResponse> findAllOAByStatus(@RequestParam (defaultValue = "null") StatoOrdine statoOrdine) {
        return ordineAcquistoService.findAllOAByStatoOrdine(statoOrdine);
    }


    @Operation(summary = "Ottieni tutti gli ordini/acquisti ordinati in base allo stato", description = "Recupera tutti gli ordini/acquisti nel sistema tramite lo status dell'ordine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/oa/allstatus")
    public List<OrdineAcquistoResponse> findAllOAByStatoOrdineAsc() {
        return ordineAcquistoService.findAllOAByStatoOrdineAsc();
    }


    @Operation(summary = "Crea ordine/acquisto ", description = "Crea ordine/acquisto, è richiesto ID utente e ID veicolo con la possibilità di inserire anche l'ID del venditore ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "510", description = "Ordine o Acquisto non trovato"),
            @ApiResponse(responseCode = "511", description = "Veicolo non trovato"),
            @ApiResponse(responseCode = "512", description = "Utente non trovato"),
            @ApiResponse(responseCode = "513", description = "Veicolo non eleggibile per la richiesta fatta"),
            @ApiResponse(responseCode = "514", description = "Utente non eleggibile per la richiesta fatta")
    })
    @PostMapping("oa/create/{idcustomer}/{idveicolo}")
    public  ResponseEntity<?>createOA(
            @RequestBody OrdineAcquistoRequest requestA,
            @PathVariable Long idcustomer,
            @PathVariable Long idveicolo,
            @RequestParam(name = "idvendor", required = false) Long idVendor) {

        Either<Error,OrdineAcquistoResponse> request = ordineAcquistoService.createOA(requestA, idcustomer, idveicolo, idVendor);

//        return request.isLeft() ? Either.left(request.getLeft()) : Either.right(request.get());
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }


    @Operation(summary = "Update ordine/acquisto", description = "Sostituzione di alcuni parametri di un ordine/acquisto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "510", description = "Ordine o Acquisto non trovato")

    })
    @PutMapping("oa/update/{idoa}")
    public ResponseEntity<?> putOA(
            @RequestBody OrdineAcquistoRequest requestOA,
            @PathVariable Long idoa) {

        Either<Error,OrdineAcquistoResponse> request = ordineAcquistoService.updateOA(requestOA,idoa);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }

    @Operation(summary = "Update parziale ordine/acquisto", description = "Sostituzione di alcuni parametri di un ordine/acquisto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "510", description = "Ordine o Acquisto non trovato")
    })

    @PatchMapping("oa/patch/{idoa}")
    public ResponseEntity<?> patchOA(
            @RequestBody OrdineAcquistoRequest requestOA,
            @PathVariable Long idoa) {

        Either<Error,OrdineAcquistoResponse> request = ordineAcquistoService.patchOA(requestOA,idoa);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }


    @Operation(summary = "Conclusione di un ordine", description = "Sostituzione parametri per rendere un ordine un acquisto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "403", description = "Non hai l'autorizzazione per procedere"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "510", description = "Ordine o Acquisto non trovato"),
            @ApiResponse(responseCode = "511", description = "Veicolo non trovato"),
            @ApiResponse(responseCode = "512", description = "Utente non trovato"),
            @ApiResponse(responseCode = "513", description = "Veicolo non eleggibile per la richiesta fatta"),
            @ApiResponse(responseCode = "514", description = "Utente non eleggibile per la richiesta fatta")
    })
    @PatchMapping("oa/concludiordine/{idOrdine}/{idAdmin}")
    public ResponseEntity<?> concludiOrdine(@AuthenticationPrincipal Utente user,
                                            @PathVariable Long idOrdine,
                                            @PathVariable Long idAdmin) throws IOException {
        if (user.getTipoUtente()== TipoUtente.CUSTOMER){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non hai l'autorizzazione per procedere");
        }
        Either<Error,OrdineAcquistoResponse> request = ordineAcquistoService.ordineToAcquisto(idOrdine, idAdmin);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }

    @Operation(summary = "Update stato ordine", description = "Aggiorna lo stato di un ordine inserito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "510", description = "Ordine o Acquisto non trovato")
    })

    @PatchMapping("oa/patch/statoordine/{idoa}")
    public ResponseEntity<?> patchStatoOrdine(
            @AuthenticationPrincipal Utente user,
            @PathVariable Long idoa,
            @RequestParam StatoOrdine statoOrdine ){
        if (user.getTipoUtente()== TipoUtente.CUSTOMER){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non hai l'autorizzazione per procedere");
        }

        Either<Error,ResponseEntity<String>> request = ordineAcquistoService.updateStatoOrdine(idoa, statoOrdine);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }


    @Operation(summary = "Cancellazione ordine/acquisto", description = "Cancella un ordine o un acquisto inserito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordine/Acquisto recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "510", description = "Ordine o Acquisto non trovato")
    })
    @DeleteMapping("oa/delete/{id}")
    public ResponseEntity<?> deleteOAById(@PathVariable Long id) {
        Either<Error,Boolean> request = ordineAcquistoService.deleteOA(id);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }

}
