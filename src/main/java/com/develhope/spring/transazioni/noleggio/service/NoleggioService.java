package com.develhope.spring.transazioni.noleggio.service;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
