package com.develhope.spring.transazioni.ordine_acquisto.controller;

import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoRequest;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.service.OrdineAcquistoService;
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
    public OrdineAcquistoResponse findOAById(@PathVariable Long id) {
        return ordineAcquistoService.findOAById(id);
    }

    @GetMapping("/oa/all")
    public List<OrdineAcquistoResponse> findAllOA() {
        return ordineAcquistoService.findAllOA();
    }

    @GetMapping("/oa/verifyorderstatus/{id}")
    public ResponseEntity<String> verifyOrderStatus(@PathVariable Long id){
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
    public OrdineAcquistoResponse createOA(
            @RequestBody OrdineAcquistoRequest requestA,
            @PathVariable Long idCustomer,
            @PathVariable Long idVeicolo,
            @RequestParam(name = "idvendor", required = false) Long idVendor) {
        return ordineAcquistoService.createOA(requestA, idCustomer, idVeicolo, idVendor);
    }

    @PutMapping("oa/update/{idoa}")
    public OrdineAcquistoResponse putOA(
            @RequestBody OrdineAcquistoRequest request,
            @PathVariable Long id) {
        return ordineAcquistoService.updateOA(request,id);
    }
    @PatchMapping("oa/patch/{idoa}")
    public OrdineAcquistoResponse patchOA(
            @RequestBody OrdineAcquistoRequest request,
            @PathVariable Long id) {
        return ordineAcquistoService.patchOA(request,id);
    }

    @PatchMapping("oa/concludiordine/{idoa}/{idadmin}")
    public OrdineAcquistoResponse concludiOrdine(@PathVariable Long idOrdine,
                                                 @PathVariable Long idAdmin) throws IOException {
        return ordineAcquistoService.ordineToAcquisto(idOrdine, idAdmin);
    }

    @PatchMapping("oa/patch/statoordine/{idoa}")
    public ResponseEntity<String> patchStatoOrdine(
            @PathVariable Long id,
            @RequestParam StatoOrdine statoOrdine ){
        return ordineAcquistoService.updateStatoOrdine(id, statoOrdine);
    }
    @DeleteMapping("oa/delete/{id}")
    public boolean deleteOAById(@PathVariable Long id) {
        return ordineAcquistoService.deleteOA(id);
    }

}
