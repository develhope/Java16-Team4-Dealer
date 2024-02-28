package com.develhope.spring.users.controller;


import com.develhope.spring.users.dto.CreateUtenteRequest;
import com.develhope.spring.users.dto.UpdateUtenteRequest;
import com.develhope.spring.users.dto.UtenteResponse;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.service.UtenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Crea un nuovo utente", description = "Crea un nuovo utente in base alle informazioni fornite nel corpo della richiesta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utente creato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Errore interno del server")
    })
    @PostMapping(path = "/add")
    public ResponseEntity<UtenteResponse> createUser(@RequestBody CreateUtenteRequest createUtenteRequest) {
        UtenteResponse savedUser = utenteService.createUtente(createUtenteRequest);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }


    @Operation(summary = "Ottieni tutti gli utenti", description = "Recupera tutti gli utenti nel sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elenco di tutti gli utenti"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Utenti non trovati"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Utente>> getAllUsers() {
        return new ResponseEntity<>(utenteService.getAll(), HttpStatus.OK);
    }


    @Operation(summary = "Ottieni utente per ID", description = "Recupera un utente specifico in base al suo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utente recuperato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Controller non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "512", description = "Utente non trovato")
    })
    @GetMapping(path = "/user/{id}")
    public ResponseEntity<Utente> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(utenteService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Utente non trovato", HttpStatus.valueOf(512));
        }
    }


    @Operation(summary = "Elimina un utente", description = "Elimina un utente specifico in base al suo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utente eliminato correttamente"),
            @ApiResponse(responseCode = "404", description = "Utente non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping(path = "/delete/{userId}")
    public ResponseEntity<String> deletAUser(@PathVariable Long id) {
        utenteService.deleteUtente(id);
        try {
            return ResponseEntity.ok("Utente cancellato con successo");
        } catch (Exception e) {
            return new ResponseEntity("Utente non trovato", HttpStatus.valueOf(512));
        }
    }


    @Operation(summary = "Aggiorna l'intero utente", description = "Aggiorna l'intero utente in base al corpo della richiesta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utente aggiornato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Utente non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping(path = "/Fullupdate/{id}")
    public ResponseEntity<String> fullUpdateUtente(@PathVariable Long id, @RequestBody UpdateUtenteRequest request) {
        utenteService.updateUtente(id, request);
        try {
            return ResponseEntity.ok("Utente aggionato con successo");
        } catch (Exception e) {
            return new ResponseEntity("Utente non trovato", HttpStatus.valueOf(512));
        }
    }


    @Operation(summary = "Aggiorna informazioni parziali dell'utente", description = "Aggiorna informazioni specifiche dell'utente in base al corpo della richiesta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utente modificato correttamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Richiesta non valida"),
            @ApiResponse(responseCode = "404", description = "Utente non trovato"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PatchMapping(path = "/update/{id}")
    public ResponseEntity<String> updateUtente(@PathVariable Long id, @RequestBody UpdateUtenteRequest request) {
        utenteService.patchUtente(request, id);
        try {
            return ResponseEntity.ok("Utente modificato con successo");
        } catch (Exception e) {
            return new ResponseEntity("Utente non trovato", HttpStatus.valueOf(512));
        }
    }

}
