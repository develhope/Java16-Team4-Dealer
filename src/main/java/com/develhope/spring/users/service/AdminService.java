package com.develhope.spring.users.service;

import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.veichles.entity.TipoVeicolo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {


    @Autowired
    private VeicoloRepo veicoloRepo;

    @Autowired
    private Veicolo veicolo;

    public Veicolo addVeicolo (Veicolo veicolo) {
        Veicolo nuovoveicolo = new Veicolo();
        nuovoveicolo.setTipoVeicolo(TipoVeicolo.AUTO);
        nuovoveicolo.setMarca(veicolo.getMarca());
        nuovoveicolo.setModello(veicolo.getModello());
        nuovoveicolo.setCilindrata(veicolo.getCilindrata());
        nuovoveicolo.setColore(veicolo.getColore());
        nuovoveicolo.setPotenza(veicolo.getPotenza());
        nuovoveicolo.setTipoDiCambio(veicolo.getTipoDiCambio());
        nuovoveicolo.setAnnoImmatricolazione(veicolo.getAnnoImmatricolazione());
        nuovoveicolo.setAlimentazione(veicolo.getAlimentazione());
        nuovoveicolo.setPrezzo(veicolo.getPrezzo());
        nuovoveicolo.setSconto(veicolo.getSconto());

        return veicoloRepo.save(veicolo);
    };

    public Veicolo modVeicolo(Long id, Veicolo veicoloModificato) {
        Optional<Veicolo> veicoloEsistenteOptional = veicoloRepo.findById(id);
        if (veicoloEsistenteOptional.isPresent()) {

            Veicolo veicoloEsistente = veicoloEsistenteOptional.get();
            veicoloEsistente.setMarca(veicoloModificato.getMarca());
            veicoloEsistente.setModello(veicoloModificato.getModello());
            veicoloEsistente.setCilindrata(veicoloModificato.getCilindrata());
            veicoloEsistente.setColore(veicoloModificato.getColore());
            veicoloEsistente.setPotenza(veicoloModificato.getPotenza());
            veicoloEsistente.setTipoDiCambio(veicoloModificato.getTipoDiCambio());
            veicoloEsistente.setAnnoImmatricolazione(veicoloModificato.getAnnoImmatricolazione());
            veicoloEsistente.setAlimentazione(veicoloModificato.getAlimentazione());
            veicoloEsistente.setPrezzo(veicoloModificato.getPrezzo());
            veicoloEsistente.setSconto(veicoloModificato.getSconto());

            return veicoloRepo.save(veicoloEsistente);
        } else {
            return null; // Veicolo non trovato
        }
    }

}
