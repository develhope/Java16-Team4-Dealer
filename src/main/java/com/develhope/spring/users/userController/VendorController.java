package com.develhope.spring.users.userController;

import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.users.service.vendorServices.VendorService;
import com.develhope.spring.veichles.entity.Veicolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @GetMapping("/getVeicolo/{id}")
    public Optional <Veicolo> veicolo(@PathVariable Long id){
        return vendorService.dettagliVeicolo(id);
    }

    @PostMapping("/createOrdineAcquisto/{id}")
    private ResponseEntity<String> nuovoOrdine(@PathVariable Long id, @RequestBody Ordine_Acquisto ordineAcquisto){
        return vendorService.nuovoOrdineByIdVeicolo(id,ordineAcquisto);
    }
    @PutMapping("/modOrdineAcquisto/{id}")
    private ResponseEntity<String> modificaOrdine(@PathVariable Long id, @RequestBody Ordine_Acquisto ordineAcquisto){
        return vendorService.modificaOrdineAcquisto(id);
    }
    @DeleteMapping("/deleteOrdine/{id}")
    private ResponseEntity<String> deleteOrdine(@PathVariable Long id){
        return vendorService.deleteOrdineAcquisto(id);
    }
}
