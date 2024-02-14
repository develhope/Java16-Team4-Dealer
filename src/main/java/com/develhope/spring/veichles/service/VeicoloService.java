package com.develhope.spring.veichles.service;

import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoVeicolo;
import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.entity.TipoVeicolo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.entity.VeicoloRequest;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    public List<Veicolo> readAllByMarca(String marca) {
        return autoRepo.findAllByMarca(marca);
    }

    public List<Veicolo> readAllByModello(String modello) {
        return autoRepo.findAllByModello(modello);
    }

    public List<Veicolo> readAllByPrezzo(BigDecimal prezzo) {
        return autoRepo.findAllByPrezzoOrderByPrezzoAsc(prezzo);
    }

    public List<Veicolo> readAllByUsato(boolean usato) {
        return autoRepo.findAllByUsato(usato);
    }

    public List<Veicolo> findAllByRange(BigDecimal min, BigDecimal max) {
        return autoRepo.findVeicoloByRange(min, max);
    }

    public List<Veicolo> findAllByStatoVendita(StatoVendita statoVendita) {
        return autoRepo.findAllByStatoVendita(statoVendita);
    }

    public List<Veicolo> findAllByStatoVenditaAsc() {
        return autoRepo.findAllOrderByStatoVenditaAsc();
    }

    public Veicolo createVeicolo(Veicolo veicolo) {
        this.autoRepo.save(veicolo);
        return veicolo;
    }

    public Veicolo patchStatoUsato(long id, boolean stato) {
        Veicolo veicolo = this.autoRepo.findById(id).orElse(null);
        if (veicolo != null) {
            veicolo.setUsato(stato);
            this.autoRepo.save(veicolo);
        }
        return veicolo;
    }

    public Veicolo updateVeicolo(Veicolo veicolo, long id) {
        Veicolo v = getById(id);

        v.setMarca(veicolo.getMarca());
        v.setTipoVeicolo(veicolo.getTipoVeicolo());
        v.setModello(veicolo.getModello());
        v.setCilindrata(veicolo.getCilindrata());
        v.setColore(veicolo.getColore());
        v.setPotenza(veicolo.getPotenza());
        v.setTipoDiCambio(veicolo.getTipoDiCambio());
        v.setAnnoImmatricolazione(veicolo.getAnnoImmatricolazione());
        v.setAlimentazione(veicolo.getAlimentazione());
        v.setPrezzo(veicolo.getPrezzo());
        v.setUsato(veicolo.isUsato());
        v.setSconto(v.getSconto());
        v.setAccessori(veicolo.getAccessori());
        v.setStatoVendita(veicolo.getStatoVendita());
        return v;
    }


    public Veicolo patchByVeicoloRequest(long id, VeicoloRequest veicoloRequest) {
        Optional<Veicolo> veicoloResult = this.autoRepo.findById(id);

        if (veicoloResult.isEmpty()) return null;

        if (veicoloRequest.getColore() != null)
            veicoloResult.get().setColore(veicoloRequest.getColore());

        if (veicoloRequest.getTipoDiCambio() != null)
            veicoloResult.get().setTipoDiCambio(veicoloRequest.getTipoDiCambio());

        if (veicoloRequest.getAnnoImmatricolazione() != null)
            veicoloResult.get().setAnnoImmatricolazione(veicoloRequest.getAnnoImmatricolazione());

        if (veicoloRequest.getAlimentazione() != null)
            veicoloResult.get().setAlimentazione(veicoloRequest.getAlimentazione());

        if (veicoloRequest.getPrezzo() != null)
            veicoloResult.get().setPrezzo(veicoloRequest.getPrezzo());

        if (veicoloRequest.getSconto() != null)
            veicoloResult.get().setSconto(veicoloRequest.getSconto());

        if (veicoloRequest.getAccessori() != null)
            veicoloResult.get().setAccessori(veicoloRequest.getAccessori());

        this.autoRepo.saveAndFlush(veicoloResult.get());
        return veicoloResult.get();
    }

    public Veicolo patchStatoVenditaVeicolo(long id, StatoVendita statoVendita) {
        Optional<Veicolo> veicoloResult = this.autoRepo.findById(id);

        if (veicoloResult.isEmpty()) return null;

        veicoloResult.get().setStatoVendita(statoVendita);
        this.autoRepo.saveAndFlush(veicoloResult.get());
        return veicoloResult.get();
    }

    public boolean deleteVeicoloById(long id) {
        Veicolo v = getById(id);
        if (v == null) return false;
        this.autoRepo.deleteById(id);
        return true;
    }

    public boolean deleteVeicolo(Veicolo veicolo) {
        this.autoRepo.delete(veicolo);
        return true;
    }

    //ALTERNATIVA_PATCH{
    //    public Veicolo patchVeicolo(@RequestBody Map<String, Object> veicolo, @PathVariable long id) {
//        Optional<Veicolo> veicoloDaPatchare = this.autoRepo.findById(id);
//
//        if (veicoloDaPatchare.isEmpty()) return null;
//
//        applyPatch(veicoloDaPatchare.get(), veicolo);
//        this.autoRepo.save(veicoloDaPatchare.get());
//
//        return veicoloDaPatchare.get();
//    }
//
//    private void applyPatch(Veicolo veicolo, Map<String, Object> patch) {
//
//        for (String fieldName : patch.keySet()) {
//
//            switch (fieldName) {
//                case "marca":
//                    veicolo.setMarca((String) patch.get(fieldName));
//                    break;
//                case "tipoVeicolo":
//                    veicolo.setTipoVeicolo(TipoVeicolo.valueOf((String) patch.get(fieldName)));
//                    break;
//                case "modello":
//                    veicolo.setModello((String) patch.get(fieldName));
//                    break;
//                case "cilindrata":
//                    veicolo.setCilindrata((int) patch.get(fieldName));
//                    break;
//                case "colore":
//                    veicolo.setColore((String) patch.get(fieldName));
//                    break;
//                case "potenza":
//                    veicolo.setPotenza((int) patch.get(fieldName));
//                    break;
//                case "tipoDiCambio":
//                    veicolo.setTipoDiCambio((String) patch.get(fieldName));
//                    break;
//                case "annoImmatricolazione":
//                    veicolo.setAnnoImmatricolazione((OffsetDateTime) patch.get(fieldName));
//                    break;
//                case "alimentazione":
//                    veicolo.setAlimentazione((String) patch.get(fieldName));
//                    break;
//                case "prezzo":
//                    veicolo.setPrezzo(BigDecimal.valueOf((double) patch.get(fieldName)));
//                    break;
//                case "usato":
//                    veicolo.setUsato((boolean) patch.get(fieldName));
//                    break;
//                case "sconto":
//                    veicolo.setSconto((double) patch.get(fieldName));
//                    break;
//                case "accessori":
//                    veicolo.setAccessori((String) patch.get(fieldName));
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
    //   }


}
