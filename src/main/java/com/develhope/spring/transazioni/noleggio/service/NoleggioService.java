package com.develhope.spring.transazioni.noleggio.service;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@Service
public class NoleggioService {

    @Autowired
    private NoleggioRepo noleggioRepo;


    public Noleggio findNoleggioById(Long id) {
        Optional<Noleggio> optionalNoleggio = noleggioRepo.findById(id);

        if (optionalNoleggio.isPresent()) {
            return optionalNoleggio.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Noleggio non trovato con ID: " + id);
        }
    }

    public Noleggio createNoleggio(Noleggio noleggio) {

        return noleggioRepo.save(noleggio);
    }


    public List<Noleggio> getAll() {
        return noleggioRepo.findAll();
    }


    public ResponseEntity<String> deleteNoleggio(long id) {
            Noleggio existingNoleggio = findNoleggioById(id);
            noleggioRepo.delete(existingNoleggio);
            return ResponseEntity.ok("Noleggio eliminato correttamente");
    }


    public Noleggio updateNoleggio(long id, Noleggio noleggio) {
        Optional<Noleggio> noleggioOptional = noleggioRepo.findById(id);

        try {
            Noleggio existingNoleggio = findNoleggioById(id);

            if (noleggio.getDataInizio() != null) {
                existingNoleggio.setDataInizio(noleggio.getDataInizio());
            }

            if (noleggio.getDataFine() != null) {
                existingNoleggio.setDataFine(noleggio.getDataFine());
            }

            if (noleggio.getCostoGiornaliero() != null) {
                existingNoleggio.setCostoGiornaliero(noleggio.getCostoGiornaliero());
            }

            if (noleggio.getCostoTotale() != null) {
                existingNoleggio.setCostoTotale(noleggio.getCostoTotale());
            }

            existingNoleggio.setPagato(noleggio.isPagato());
            existingNoleggio.setNoleggiato(noleggio.isNoleggiato());

            return noleggioRepo.save(existingNoleggio);

        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'aggiornamento del noleggio", e);
        }
    }


}
