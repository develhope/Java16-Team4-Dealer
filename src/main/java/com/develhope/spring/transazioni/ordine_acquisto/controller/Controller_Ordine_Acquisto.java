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
    public OrdineAcquistoResponse getOAById(@PathVariable Long id){
        return ordineAcquistoService.findOAById(id);
    }

    @GetMapping("/oa/all")
    public List<OrdineAcquistoResponse> getAllOA(){
        return ordineAcquistoService.findAllOA();
    }

    @GetMapping("/oa/allbystatus")
    public List<OrdineAcquistoResponse> getAllOAByStatus(@RequestParam StatoOrdine statoOrdine){
        return ordineAcquistoService.findAllOAByStatoOrdine(statoOrdine);
    }
    @GetMapping("/oa/allstatus")
    public List<OrdineAcquistoResponse> findAllOAByStatoOrdineAsc(){
        return ordineAcquistoService.findAllOAByStatoOrdineAsc();
    }

//    @PostMapping("/create/oa/{idcustomer}/{idveicolo}")
//    public OrdineAcquistoResponse findAllOAByStatoOrdineAsc(@RequestBody OrdineAcquistoRequest requestA,
//                                                            Long idCustomer,
//                                                            Long idVeicolo,
//                                                            @RequestParam(name = "idvendor",required = false) Long idVendor){
//
//    }
}
