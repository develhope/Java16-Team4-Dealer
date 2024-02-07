package com.develhope.spring.transazioni.noleggio.service;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
            throw new EntityNotFoundException("Noleggio non trovato con ID: " + id);
        }
    }

    public Noleggio createNoleggio(Noleggio noleggio) {
        return noleggioRepo.save(noleggio);
    }


    public List<Noleggio> getAll() {
        return noleggioRepo.findAll();
    }

    public Noleggio getNoleggioById(long id) {
        Optional<Noleggio> optionalNoleggio = noleggioRepo.findById(id);
        if (optionalNoleggio.isPresent()) {
            return optionalNoleggio.get();
        } else {
            return null;
        }
    }
    public void deleteNoleggio(long id) {
        noleggioRepo.deleteById(id);
    }


    public Noleggio updateNoleggio(long id, Noleggio noleggio) {
        Noleggio existingNoleggio = findNoleggioById(id);

        if (existingNoleggio == null) {
            throw new EntityNotFoundException("Noleggio non trovato con ID: " + id);
        }

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
    }


}
