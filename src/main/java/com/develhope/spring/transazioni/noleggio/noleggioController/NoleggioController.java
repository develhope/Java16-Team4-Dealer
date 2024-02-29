package com.develhope.spring.transazioni.noleggio.noleggioController;

import com.develhope.spring.shared.Error;
import com.develhope.spring.transazioni.noleggio.dto.NoleggioRequest;
import com.develhope.spring.transazioni.noleggio.dto.NoleggioResponse;
import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.service.NoleggioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noleggio")
public class NoleggioController {

    @Autowired
    NoleggioService noleggioService;
    @Operation(summary = "crea un nuovo noleggio",description = "crea un nuovo noleggio passando id del cliente, del venditore e del veicolo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "noleggio creato con successo"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "500", description = "internal Error")
    })
    @PostMapping("/nuovoNoleggio/{idCustomer}/{idVeicolo}/{idVenditore}")
    public ResponseEntity<NoleggioResponse> nuovoNoleggio (                                                                                                                               @PathVariable Long idCustomer,
                                           @PathVariable Long idVeicolo,
                                           @RequestBody NoleggioRequest noleggioRequest,
                                           @PathVariable (required = false) Long idVenditore){
        return noleggioService.createNoleggio(idCustomer,idVeicolo,idVenditore,noleggioRequest);
    }
    @Operation(summary = "elenca tutti i noleggi",description = "ritorna tutti i noleggi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "elenco di tutti i noleggi"),
            @ApiResponse(responseCode = "400", description = "bad request, richiesta non valida"),
            @ApiResponse(responseCode = "518", description = "noleggi non trovati"),
            @ApiResponse(responseCode = "500", description = "internal Error")
    })
    @GetMapping("/AllNoleggio")
    public List<Noleggio> tutti_iNoleggi (){
        return noleggioService.getAll();
    }
    @Operation(summary = "Aggiorna noleggi",description = "aggiorna il noleggio dell'id passato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noleggio aggiornato correttamente"),
            @ApiResponse(responseCode = "400", description = "bad request, richiesta non valida"),
            @ApiResponse(responseCode = "518", description = "Noleggio non trovato"),
            @ApiResponse(responseCode = "500", description = "internal Error")
    })
    @PutMapping("/UpdateNoleggio/{idNoleggio}")
    public ResponseEntity<?> updateNoleggio (@PathVariable Long idNoleggio, @RequestBody NoleggioRequest request){
        Either<Error,NoleggioResponse> nol = noleggioService.updateNoleggio(idNoleggio, request);
        if (nol.isLeft()){
            return ResponseEntity.status(nol.getLeft().getCode()).body(nol.getLeft().getMessage());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(nol.get());
        }
    }
    @Operation(summary = "Modifica Noleggio",description = "Modifica il noleggio con l'id passata")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noleggio modificato correttamente"),
            @ApiResponse(responseCode = "400", description = "bad request, richiesta non valida"),
            @ApiResponse(responseCode = "500", description = "internal Error")
    })
    @PatchMapping("/patchNoleggio/{idNoleggio}")
    public ResponseEntity<?> patchNoleggio (@PathVariable Long idNoleggio, @RequestBody NoleggioRequest request){
        Either<Error, NoleggioResponse> nol = noleggioService.patchONoleggio(request,idNoleggio);
        if (nol.isLeft()){
            return ResponseEntity.status(nol.getLeft().getCode()).body(nol.getLeft().getMessage());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(nol.get());
        }
    }
    @Operation(summary = "Elimina Noleggio",description = "Elimina un noleggio con id passata")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noleggio eliminato correttamente"),
            @ApiResponse(responseCode = "400", description = "bad request, richiesta non valida"),
           @ApiResponse(responseCode = "500", description = "internal Error")
    })
    @DeleteMapping("/cancellaNoleggio/{idNoleggio}")
    public ResponseEntity<String> deleteNoleggio(@PathVariable Long idNoleggio) {
        Either<Error, ResponseEntity<String>> request = noleggioService.deleteNoleggio(idNoleggio);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(request.get()));
        }
    }
}
