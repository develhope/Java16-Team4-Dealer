package com.develhope.spring.transazioni.ordine_acquisto.controller;

import com.develhope.spring.shared.Error;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoRequest;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.service.OrdineAcquistoService;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/Acquisto")
//@PreAuthorize
public class Controller_Ordine_Acquisto {

    @Autowired
    private OrdineAcquistoService ordineAcquistoService;

    @GetMapping("/oa/{id}")
    public ResponseEntity<?> findOAById(@PathVariable Long id) {
        Either<Error,OrdineAcquistoResponse> request =ordineAcquistoService.findOAById(id);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }
    @GetMapping()
    public OrdineAcquistoRequest findOAById() {
        return new OrdineAcquistoRequest();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/oa/all")
    public List<OrdineAcquistoResponse> findAllOA() {
        return ordineAcquistoService.findAllOA();
    }

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
    @GetMapping("/oa/customeroa/{idcustomer}")
    public ResponseEntity<?> findAllOAById(@PathVariable Long idcustomer) {
        Either<Error,List<OrdineAcquistoResponse>> request =ordineAcquistoService.findAllOAById(idcustomer);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }

    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/oa/allbystatus")
    public List<OrdineAcquistoResponse> findAllOAByStatus(@RequestParam (defaultValue = "null") StatoOrdine statoOrdine) {
        return ordineAcquistoService.findAllOAByStatoOrdine(statoOrdine);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/oa/allstatus")
    public List<OrdineAcquistoResponse> findAllOAByStatoOrdineAsc() {
        return ordineAcquistoService.findAllOAByStatoOrdineAsc();
    }

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

    @PutMapping("oa/update/{idoa}")
    public ResponseEntity<?> putOA(
            @RequestBody OrdineAcquistoRequest requestOA,
            @PathVariable Long idoa) {

        //TODO: ricontrollare se funziona
        Either<Error,OrdineAcquistoResponse> request = ordineAcquistoService.updateOA(requestOA,idoa);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }

    @PatchMapping("oa/patch/{idoa}")
    public ResponseEntity<?> patchOA(
            @RequestBody OrdineAcquistoRequest requestOA,
            @PathVariable Long idoa) {
        //TODO: ricontrollare se funziona
        Either<Error,OrdineAcquistoResponse> request = ordineAcquistoService.patchOA(requestOA,idoa);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }

    @PatchMapping("oa/concludiordine/{idOrdine}/{idAdmin}")
    public ResponseEntity<?> concludiOrdine(@PathVariable Long idOrdine,
                                                 @PathVariable Long idAdmin) throws IOException {
        //TODO: ricontrollare se funziona una volta che l'ordine può funzionare
        Either<Error,OrdineAcquistoResponse> request = ordineAcquistoService.ordineToAcquisto(idOrdine, idAdmin);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }

    @PatchMapping("oa/patch/statoordine/{idoa}")
    public ResponseEntity<?> patchStatoOrdine(
            @PathVariable Long idoa,
            @RequestParam StatoOrdine statoOrdine ){
        //TODO: ricontrollare se funziona

        Either<Error,ResponseEntity<String>> request = ordineAcquistoService.updateStatoOrdine(idoa, statoOrdine);
        if (request.isLeft()) {
            return ResponseEntity.status(request.getLeft().getCode()).body(request.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(request.get());
        }
    }
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
