package com.develhope.spring.transazioni.noleggio.controller;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import com.develhope.spring.transazioni.noleggio.service.NoleggioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/noleggio")
public class ControllerNoleggio {

    @Autowired
    private NoleggioService noleggioService;

    @Autowired
    private NoleggioRepo noleggioRepo;


    @PostMapping("/create")
    public Noleggio createStudent(@RequestBody Noleggio noleggio) {
        return noleggioRepo.save(noleggio);
    }

    @GetMapping("/getList")
    public List<Noleggio> getAll() {
        return noleggioRepo.findAll();
    }

    @GetMapping("/get/{id}")
    public Noleggio getNoleggioById(@PathVariable Long id) {
        Optional<Noleggio> optionalNoleggio = noleggioRepo.findById(id);
        if (optionalNoleggio.isPresent()) {
            return optionalNoleggio.get();
        } else {
            return null;
        }
    }
    @DeleteMapping("delete/{id}")
    public void deleteNoleggio(@PathVariable Long id) {
        noleggioRepo.deleteById(id);
    }


    @PatchMapping("/update/{id}")
    public Noleggio updateNoleggio(@PathVariable Long id, @RequestBody Noleggio noleggio) {
        Noleggio existingNoleggio = noleggioService.findNoleggioById(id);

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
