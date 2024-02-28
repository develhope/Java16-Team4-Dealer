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
    public Either<Error,OrdineAcquistoResponse> findOAById(@PathVariable Long id) {
        Either<Error,OrdineAcquistoResponse> request =ordineAcquistoService.findOAById(id);
        return request.isLeft() ? Either.left(request.getLeft()) : Either.right(request.get());
    }
    @GetMapping()
    public OrdineAcquistoRequest findOAById() {
        return new OrdineAcquistoRequest();
    }

    @GetMapping("/oa/all")
    public List<OrdineAcquistoResponse> findAllOA() {
        return ordineAcquistoService.findAllOA();
    }

    @GetMapping("/oa/verifyorderstatus/{id}")
    public Either<Error,ResponseEntity<String>> verifyOrderStatus(@PathVariable Long id){
        Either<Error,ResponseEntity<String>> request = ordineAcquistoService.verifyOrderById(id);
        return request.isLeft() ? Either.left(request.getLeft()) : Either.right(request.get());
    }
    @GetMapping("/oa/customeroa/{idcustomer}")
    public ResponseEntity<List<OrdineAcquistoResponse>> findAllOAById(@PathVariable Long idcustomer){
        try {
            return ResponseEntity.ok(ordineAcquistoService.findAllOAById(idcustomer));
        }catch (IOException e){
            return ResponseEntity.badRequest().body(null);
        }

    }


    @GetMapping("/oa/allbystatus")
    public List<OrdineAcquistoResponse> findAllOAByStatus(@RequestParam StatoOrdine statoOrdine) {
        return ordineAcquistoService.findAllOAByStatoOrdine(statoOrdine);
    }

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
    public Either<Error,OrdineAcquistoResponse> putOA(
            @RequestBody OrdineAcquistoRequest requestOA,
            @PathVariable Long id) {
        Either<Error,OrdineAcquistoResponse> request = ordineAcquistoService.updateOA(requestOA,id);;
        return request.isLeft() ? Either.left(request.getLeft()) : Either.right(request.get());
    }
    @PatchMapping("oa/patch/{idoa}")
    public Either<Error,OrdineAcquistoResponse> patchOA(
            @RequestBody OrdineAcquistoRequest request,
            @PathVariable Long id) {
        return ordineAcquistoService.patchOA(request,id);
    }

    @PatchMapping("oa/concludiordine/{idoa}/{idadmin}")
    public Either<Error,OrdineAcquistoResponse> concludiOrdine(@PathVariable Long idOrdine,
                                                 @PathVariable Long idAdmin) throws IOException {
        Either<Error,OrdineAcquistoResponse> request = ordineAcquistoService.ordineToAcquisto(idOrdine, idAdmin);
        return request.isLeft() ? Either.left(request.getLeft()) : Either.right(request.get());
    }

    @PatchMapping("oa/patch/statoordine/{idoa}")
    public Either<Error,ResponseEntity<String>> patchStatoOrdine(
            @PathVariable Long id,
            @RequestParam StatoOrdine statoOrdine ){
        Either<Error,ResponseEntity<String>> request = ordineAcquistoService.updateStatoOrdine(id, statoOrdine);
        return request.isLeft() ? Either.left(request.getLeft()) : Either.right(request.get());
    }
    @DeleteMapping("oa/delete/{id}")
    public Either<Error,Boolean> deleteOAById(@PathVariable Long id) {
        Either<Error,Boolean> request = ordineAcquistoService.deleteOA(id);
        return request.isLeft() ? Either.left(request.getLeft()) : Either.right(request.get());
    }

}
