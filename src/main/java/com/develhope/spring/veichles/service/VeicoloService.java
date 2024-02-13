package com.develhope.spring.veichles.service;

import com.develhope.spring.veichles.entity.TipoVeicolo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VeicoloService {

    @Autowired
    private VeicoloRepo autoRepo;

    public Veicolo getById(long id) {
        Optional<Veicolo> veicolo = this.autoRepo.findById(id);
        return veicolo.orElse(null);
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
        v.setUsato(veicolo.isUsato());
        v.setAccessori(veicolo.getAccessori());
        return v;
    }
    public Veicolo patchVeicolo(@RequestBody Map<String,Object> veicolo, @PathVariable long id) {
        Veicolo veicoloDaPatchare = getById(id);

        applyPatch(veicoloDaPatchare,veicolo);

        this.autoRepo.save(veicoloDaPatchare);


        return veicoloDaPatchare;
    }
    private void applyPatch(Veicolo veicolo, Map<String, Object> patch) {

        for (String fieldName : patch.keySet()) {
                
                switch (fieldName) {
                    case "marca":
                        veicolo.setMarca((String) patch.get(fieldName));
                        break;
                    case "tipoVeicolo":
                        veicolo.setTipoVeicolo(TipoVeicolo.valueOf((String) patch.get(fieldName)));
                        break;
                    case "modello":
                        veicolo.setModello((String) patch.get(fieldName));
                        break;
                    case "cilindrata":
                        veicolo.setCilindrata((int) patch.get(fieldName));
                        break;
                    case "colore":
                        veicolo.setColore((String) patch.get(fieldName));
                        break;
                    case "potenza":
                        veicolo.setPotenza((int) patch.get(fieldName));
                        break;
                    case "tipoDiCambio":
                        veicolo.setTipoDiCambio((String) patch.get(fieldName));
                        break;
                    case "annoImmatricolazione":
                        veicolo.setAnnoImmatricolazione((OffsetDateTime) patch.get(fieldName));
                        break;
                    case "alimentazione":
                        veicolo.setAlimentazione((String) patch.get(fieldName));
                        break;
                    case "prezzo":
                        veicolo.setPrezzo(BigDecimal.valueOf((double) patch.get(fieldName)));
                        break;
                    case "usato":
                        veicolo.setUsato((boolean) patch.get(fieldName));
                        break;
                    case "sconto":
                        veicolo.setSconto((double) patch.get(fieldName));
                        break;
                    case "accessori":
                        veicolo.setAccessori((String) patch.get(fieldName));
                        break;
                    default:
                        break;
                }
            }
        }

    public boolean deleteVeicoloById(long id){
        Veicolo  v = getById(id);
        if (v==null)return false;
        this.autoRepo.deleteById(id);
        return true;
    }
    public boolean deleteVeicolo(Veicolo veicolo){
        this.autoRepo.delete(veicolo);
        return true;
    }


}
