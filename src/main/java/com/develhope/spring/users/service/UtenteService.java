package com.develhope.spring.users.service;

import com.develhope.spring.users.dto.CreateUtenteRequest;
import com.develhope.spring.users.dto.UpdateUtenteRequest;
import com.develhope.spring.users.dto.UtenteModel;
import com.develhope.spring.users.dto.UtenteResponse;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.repository.UtenteRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UtenteService {

    @Autowired
    private UtenteRepo utenteRepo;
    @Autowired
    UtenteModel utenteModel;

    @SneakyThrows
    public Utente getById(Long id) {
        Optional<Utente> optionalUtente = this.utenteRepo.findById(id);
        if (optionalUtente.isEmpty()) throw new IOException("Utente non trovato");
        return optionalUtente.get();
    }

    private Utente requestToEntityNoleggio(CreateUtenteRequest request) {
        return utenteModel.convertModelToEntity(utenteModel.convertRequestToModel(request));
    }

    private UtenteResponse entityToResponseNoleggio(Utente entity) {
        return utenteModel.convertModelToResponse(utenteModel.convertEntityToModel(entity));
    }

    public UtenteResponse createUtente(CreateUtenteRequest request) {
        Utente utenteEntity = requestToEntityNoleggio(request);
        this.utenteRepo.save(utenteEntity);
        return entityToResponseNoleggio(utenteEntity);
    }
    public List<Utente> getAll() {
        return utenteRepo.findAll();
    }

    public ResponseEntity<String> deleteUtente(long id) {
        Utente existingUtente = getById(id);
        utenteRepo.delete(existingUtente);
        return ResponseEntity.ok("Utente eliminato correttamente");
    }
    public UtenteResponse updateUtente(long id, UpdateUtenteRequest request) {
        Utente utenteEntity = getById(id);

        utenteEntity.setFirstName(request.getFirstName());
        utenteEntity.setLastName(request.getLastName());
        utenteEntity.setPhoneNumber(request.getPhoneNumber());
        utenteEntity.setEmail(request.getEmail());
        utenteEntity.setPassword(request.getPassword());

        this.utenteRepo.saveAndFlush(utenteEntity);

        return entityToResponseNoleggio(utenteEntity);
    }

    public UtenteResponse patchUtente (UpdateUtenteRequest request, Long id){
        Utente utenteEntity = getById(id);

        if (request.getFirstName()!=null)
            utenteEntity.setFirstName(request.getFirstName());


        if (request.getLastName()!=null)
            utenteEntity.setLastName(request.getLastName());

        if (request.getPhoneNumber()!=null)
            utenteEntity.setPhoneNumber(request.getPhoneNumber());

        if (request.getEmail()!=null)
            utenteEntity.setEmail(request.getEmail());

        if (request.getPassword()!=null)
            utenteEntity.setPassword(request.getPassword());


        this.utenteRepo.saveAndFlush(utenteEntity);

        return entityToResponseNoleggio(utenteEntity);
    }

}
