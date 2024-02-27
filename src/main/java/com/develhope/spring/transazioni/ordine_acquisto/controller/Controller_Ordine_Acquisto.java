package com.develhope.spring.transazioni.ordine_acquisto.controller;

import com.develhope.spring.shared.Error;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoRequest;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.service.OrdineAcquistoService;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/Acquisto")

public class Controller_Ordine_Acquisto {

    @Autowired
    private OrdineAcquistoService ordineAcquistoService;

    @GetMapping("/oa/{id}")
    public Either<Error,OrdineAcquistoResponse> findOAById(@PathVariable Long id) {
        return ordineAcquistoService.findOAById(id);
    }

    @GetMapping("/oa/all")
    public List<OrdineAcquistoResponse> findAllOA() {
        return ordineAcquistoService.findAllOA();
    }

    @GetMapping("/oa/verifyorderstatus/{id}")
    public Either<Error,ResponseEntity<String>> verifyOrderStatus(@PathVariable Long id){
        return ordineAcquistoService.verifyOrderById(id);
    }
    @GetMapping("/oa/customeroa/{id}")
    public ResponseEntity<List<OrdineAcquistoResponse>> findAllOAById(@PathVariable Long idCustomer){
        try {
            return ResponseEntity.ok(ordineAcquistoService.findAllOAById(idCustomer));
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
    public Either<Error,OrdineAcquistoResponse> createOA(
            @RequestBody OrdineAcquistoRequest requestA,
            @PathVariable Long idCustomer,
            @PathVariable Long idVeicolo,
            @RequestParam(name = "idvendor", required = false) Long idVendor) {
        return ordineAcquistoService.createOA(requestA, idCustomer, idVeicolo, idVendor);
    }

    @PutMapping("oa/update/{idoa}")
    public Either<Error,OrdineAcquistoResponse> putOA(
            @RequestBody OrdineAcquistoRequest request,
            @PathVariable Long id) {
        return ordineAcquistoService.updateOA(request,id);
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
        return ordineAcquistoService.ordineToAcquisto(idOrdine, idAdmin);
    }

    @PatchMapping("oa/patch/statoordine/{idoa}")
    public Either<Error,ResponseEntity<String>> patchStatoOrdine(
            @PathVariable Long id,
            @RequestParam StatoOrdine statoOrdine ){
        return ordineAcquistoService.updateStatoOrdine(id, statoOrdine);
    }
    @DeleteMapping("oa/delete/{id}")
    public Either<Error,Boolean> deleteOAById(@PathVariable Long id) {
        return ordineAcquistoService.deleteOA(id);
    }

}
