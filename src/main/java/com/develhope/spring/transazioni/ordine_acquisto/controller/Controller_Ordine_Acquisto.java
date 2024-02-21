package com.develhope.spring.transazioni.ordine_acquisto.controller;

import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoRequest;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.transazioni.ordine_acquisto.service.OrdineAcquistoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/oa/allbystatus")
    public List<OrdineAcquistoResponse> findAllOAByStatus(@RequestParam StatoOrdine statoOrdine) {
        return ordineAcquistoService.findAllOAByStatoOrdine(statoOrdine);
    }

    @GetMapping("/oa/allstatus")
    public List<OrdineAcquistoResponse> findAllOAByStatoOrdineAsc() {
        return ordineAcquistoService.findAllOAByStatoOrdineAsc();
    }

    @PostMapping("/create/oa/{idcustomer}/{idveicolo}")
    public OrdineAcquistoResponse createOA(
            @RequestBody OrdineAcquistoRequest requestA,
            @PathVariable Long idCustomer,
            @PathVariable Long idVeicolo,
            @RequestParam(name = "idvendor", required = false) Long idVendor) {
        return ordineAcquistoService.createOA(requestA, idCustomer, idVeicolo, idVendor);
    }

    @PutMapping("/update/oa/{idoa}")
    public OrdineAcquistoResponse putOA(
            @RequestBody OrdineAcquistoRequest request,
            @PathVariable Long id) {
        return ordineAcquistoService.updateOA(request,id);
    }
    @PatchMapping("/patch/oa/{idoa}")
    public OrdineAcquistoResponse patchOA(
            @RequestBody OrdineAcquistoRequest request,
            @PathVariable Long id) {
        return ordineAcquistoService.patchOA(request,id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteOAById(@PathVariable Long id) {
        return ordineAcquistoService.deleteOA(id);
    }

}
