package com.develhope.spring.veichles.controllers;


import com.develhope.spring.veichles.dto.VeicoloRequest;
import com.develhope.spring.veichles.dto.VeicoloResponse;
import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.service.VeicoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class VeicoloController {

    @Autowired
    private VeicoloService veicoloService;


    @GetMapping("/veicolo/{id}")
    public VeicoloResponse getVeicolo(@PathVariable Long id) {
        return this.veicoloService.findById(id);
       // return new VeicoloResponse(); //per prendere il JSON pulito
    }

    @GetMapping("/veicoli")
    public List<VeicoloResponse> getAll() {
        return this.veicoloService.readAll();
    }

    @GetMapping("/findveicoliByMarca")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByMarca(@RequestParam (name = "marca") String marca){
        try {
            List<VeicoloResponse> response = this.veicoloService.readAllByMarca(marca);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/findveicoliByModello")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByModello(@RequestParam (name = "modello") String modello){
        try {
            List<VeicoloResponse> response = this.veicoloService.readAllByModello(modello);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/findveicoliByPrezzo")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByPrezzo(@RequestParam (name = "prezzo") BigDecimal prezzo){
        try {
            List<VeicoloResponse> response = this.veicoloService.readAllByPrezzo(prezzo);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/findveicoliByUsato")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByUsato(@RequestParam (name = "usato") boolean usato){
        try {
            List<VeicoloResponse> response = this.veicoloService.readAllByUsato(usato);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/findveicoliByStatoVendita")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByStatoVendita(@RequestParam (name = "statoVendita") StatoVendita statoVendita){
        try {
            List<VeicoloResponse> response = this.veicoloService.findAllByStatoVendita(statoVendita);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/findveicoli/perstatovendita")
    public ResponseEntity<List<VeicoloResponse>> findVeicoloByStatoVenditaAsc(){
        try {
            List<VeicoloResponse> response = this.veicoloService.findAllByStatoVenditaAsc();
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/findveicoliByRange")
    public ResponseEntity<List<VeicoloResponse>> findAllByRange(
            @RequestParam (name = "min") BigDecimal min,
            @RequestParam (name = "max") BigDecimal max){
        try {
            List<VeicoloResponse> response = this.veicoloService.findAllByRange(min, max);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/usato")
    public ResponseEntity<VeicoloResponse> patchStatoUsato(
            @RequestParam (name = "isUsato") Boolean isUsato,
            @RequestParam (name = "idVeicolo") Long idVeicolo){
        try {
            VeicoloResponse response = this.veicoloService.patchStatoUsato(idVeicolo,isUsato);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }






    @PostMapping("/addVeicolo")
    public VeicoloResponse addVeicolo(@RequestBody VeicoloRequest veicoloRequest) {
        return veicoloService.createVeicolo(veicoloRequest);

    }

    @PutMapping("/updateveicolo/{id}")
    public ResponseEntity<VeicoloResponse> updateVeicolo(@RequestBody VeicoloRequest veicoloRequest, @PathVariable Long id) {
        VeicoloResponse veicoloAggiornato = veicoloService.updateVeicolo(veicoloRequest, id);
        if (veicoloAggiornato != null) {
            return ResponseEntity.ok(veicoloAggiornato);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/modificaveicolo/{id}")
    public ResponseEntity<VeicoloResponse> patchVeicolo(@RequestBody VeicoloRequest veicoloRequest, @PathVariable Long id) {
        try {
            VeicoloResponse response = this.veicoloService.patchByVeicoloRequest(id, veicoloRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/deleteVeicolo/{id}")
    public ResponseEntity<String> deleteVeicolo(@PathVariable Long id) {
        try {

            return veicoloService.deleteVeicoloById(id)
                    ? ResponseEntity.ok("Veicolo rimosso con successo")
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veicolo non trovato");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione del veicolo");
        }
    }


}
