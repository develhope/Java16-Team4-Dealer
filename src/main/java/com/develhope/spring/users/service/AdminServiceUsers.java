package com.develhope.spring.users.service;

import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceUsers {

   @Autowired
    UtenteRepo utenteRepo;
   @Autowired
   Utente utente;

   public Utente modUtente (Long id) {
       utenteRepo.findById(id);
       Utente utenteModificato = new Utente();
       utenteModificato.setFirstName(utente.getFirstName());
       utenteModificato.setLastName(utente.getLastName());
       utenteModificato.setEmail(utente.getEmail());
       utenteModificato.setPassword(utente.getPassword());

       return utenteModificato;
   }

   public void deleteUtente (Long id) {
       utenteRepo.deleteById(id);
   }





}
