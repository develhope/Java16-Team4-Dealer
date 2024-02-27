package com.develhope.spring.users.service;

import com.develhope.spring.transazioni.noleggio.dto.NoleggioModel;
import com.develhope.spring.transazioni.noleggio.dto.NoleggioResponse;
import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import com.develhope.spring.transazioni.noleggio.service.NoleggioService;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.service.OrdineAcquistoService;
import com.develhope.spring.users.dto.CreateUtenteRequest;
import com.develhope.spring.users.dto.UpdateUtenteRequest;
import com.develhope.spring.users.dto.UtenteModel;
import com.develhope.spring.users.dto.UtenteResponse;
import com.develhope.spring.users.entity.TipoUtente;
import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import com.develhope.spring.veichles.service.VeicoloService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    UtenteRepo utenteRepo;
    @Autowired
    UtenteModel utenteModel;

    @Autowired
    NoleggioModel noleggioModel;
    @Autowired
    NoleggioService noleggioService;
    @Autowired
    OrdineAcquistoService ordineAcquistoService;


    @SneakyThrows
    public Utente getById(Long id) {
        Optional<Utente> optionalUtente = this.utenteRepo.findById(id);
        if (optionalUtente.isEmpty()) throw new IOException("Utente non trovato");
        return optionalUtente.get();
    }

    private Utente requestToEntityUtente(CreateUtenteRequest request) {
        return utenteModel.convertModelToEntity(utenteModel.convertRequestToModel(request));
    }

    private UtenteResponse entityToResponseUtente(Utente entity) {
        return utenteModel.convertModelToResponse(utenteModel.convertEntityToModel(entity));
    }

    public UtenteResponse createUtente(CreateUtenteRequest request) {
        Utente utenteEntity = requestToEntityUtente(request);
        this.utenteRepo.save(utenteEntity);
        return entityToResponseUtente(utenteEntity);
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

        return entityToResponseUtente(utenteEntity);
    }

    public UtenteResponse patchUtente(UpdateUtenteRequest request, Long id) {
        Utente utenteEntity = getById(id);

        if (request.getFirstName() != null)
            utenteEntity.setFirstName(request.getFirstName());


        if (request.getLastName() != null)
            utenteEntity.setLastName(request.getLastName());

        if (request.getPhoneNumber() != null)
            utenteEntity.setPhoneNumber(request.getPhoneNumber());

        if (request.getEmail() != null)
            utenteEntity.setEmail(request.getEmail());

        if (request.getPassword() != null)
            utenteEntity.setPassword(request.getPassword());


        this.utenteRepo.saveAndFlush(utenteEntity);

        return entityToResponseUtente(utenteEntity);
    }


    public List<NoleggioResponse> getNoleggiByIdUtente(Long idUtente) {

        List<Noleggio> listaNoleggiUser = noleggioService.findAllById(idUtente);

        return listaNoleggiUser
                .stream()
                .map(entity -> noleggioModel.convertModelToResponse(noleggioModel.convertEntityToModel(entity)))
                .toList();
    }


    public List<OrdineAcquistoResponse> getOrdineAcquistoByIdUtente(Long idUtente) throws IOException {

        return ordineAcquistoService.findAllOAById(idUtente);

    }

    public TipoUtente checkTipoUtente (Long id){
        return  getById(id).getTipoUtente();
    }


}
