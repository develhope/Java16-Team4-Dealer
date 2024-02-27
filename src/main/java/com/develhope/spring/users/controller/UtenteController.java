package com.develhope.spring.users.controller;


import com.develhope.spring.users.dto.CreateUtenteRequest;
import com.develhope.spring.users.dto.UpdateUtenteRequest;
import com.develhope.spring.users.dto.UtenteResponse;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.service.UtenteService;
import io.swagger.v3.oas.annotations.Parameter;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UtenteController {

    @Autowired
    UtenteService utenteService;

    @PostMapping(path = "/add")
    public ResponseEntity<UtenteResponse> createUser(@RequestBody CreateUtenteRequest createUtenteRequest) {
        UtenteResponse savedUser = utenteService.createUtente(createUtenteRequest);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }


    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Utente>> getAllUsers() {
        return new ResponseEntity<>(utenteService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<Utente> getById(@PathVariable Long id) {
        return new ResponseEntity<>(utenteService.getById(id), HttpStatus.OK);
    }


    @DeleteMapping(path = "/delete/{userId}")
    public ResponseEntity<String> deletAUser(@PathVariable Long id) {
       utenteService.deleteUtente(id);
       return ResponseEntity.ok("Utente cancellato con successo");
    }

    @PutMapping(path = "/Fullupdate/{id}")
    public ResponseEntity<String> fullUpdateUtente(@RequestBody UpdateUtenteRequest request, Long id) {
        utenteService.updateUtente(id, request);
        return ResponseEntity.ok("Utente aggionato con successo");
    }

    @PatchMapping(path = "/update/{id}")
    public ResponseEntity<String> updateUtente(@RequestBody UpdateUtenteRequest request, Long id) {
        utenteService.patchUtente(request, id);
        return ResponseEntity.ok("Utente modificato con successo");
    }

}
