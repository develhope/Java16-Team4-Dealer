package com.develhope.spring.veichles.service;

import com.develhope.spring.veichles.entity.TipoVeicolo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutoService {

    @Autowired
    private VeicoloRepo autoRepo;

    public Veicolo getById(long id) {
        Optional<Veicolo> veicolo = this.autoRepo.findById(id);
        if (veicolo.isEmpty()) return new Veicolo();
        return veicolo.get();
    }

    public List<Veicolo> readAll() {
        return autoRepo.findAll();
    }

    public List<Veicolo> readAllByType(TipoVeicolo type) {
        return autoRepo.findAll(Sort.by(type.toString()).ascending());
    }

    public Veicolo createVeicolo(Veicolo veicolo) {
        this.autoRepo.save(veicolo);
        return veicolo;
    }

    public Veicolo updateVeicolo(Veicolo veicolo, long id) {
        Veicolo v = getById(id);

        v.setTipoVeicolo(veicolo.getTipoVeicolo());
        v.setMarca(veicolo.getMarca());
        v.setModello(veicolo.getModello());
        v.setCilindrata(veicolo.getCilindrata());
        v.setColore(veicolo.getColore());
        v.setPotenza(veicolo.getPotenza());
        v.setTipoDiCambio(veicolo.getTipoDiCambio());
        v.setAnnoImmatricolazione(veicolo.getAnnoImmatricolazione());
        v.setAlimentazione(veicolo.getAlimentazione());
        v.setPrezzo(veicolo.getPrezzo());
        v.setSconto(v.getSconto());
        return v;

    }


}
