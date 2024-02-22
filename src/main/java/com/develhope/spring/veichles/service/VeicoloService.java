package com.develhope.spring.veichles.service;


import com.develhope.spring.veichles.dto.VeicoloResponse;
import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.entity.TipoVeicolo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.dto.VeicoloRequest;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VeicoloService {

    @Autowired
    private VeicoloRepo autoRepo;
    @Autowired
    private VeicoloDTOMapper mapper;

    @SneakyThrows
    private Veicolo getById(long id) {
        Optional<Veicolo> veicolo = this.autoRepo.findById(id);
        if (veicolo.isEmpty()) throw new  IOException("Veicolo non trovato");
        return veicolo.get();
    }

    private VeicoloResponse immatricola(long id) {
        Veicolo veicolo = getById(id);
        veicolo.setAnnoImmatricolazione(OffsetDateTime.now());
        return mapper.apply(veicolo);
    }

    @SneakyThrows
    public VeicoloResponse findById(long id) {
        return mapper.convertEntity(getById(id));
    }


    public List<VeicoloResponse> readAll() {
        return autoRepo.findAll().stream().map(mapper).collect(Collectors.toList());
    }

    public List<VeicoloResponse> readAllByType(TipoVeicolo type) {
        return autoRepo.findAll(Sort.by(type.toString()).ascending()).stream().map(mapper).collect(Collectors.toList());
    }

    public List<VeicoloResponse> readAllByMarca(String marca) {
        return autoRepo.findAllByMarca(marca).stream().map(mapper).collect(Collectors.toList());
    }

    public List<VeicoloResponse> readAllByModello(String modello) {
        return autoRepo.findAllByModello(modello).stream().map(mapper).collect(Collectors.toList());
    }

    public List<VeicoloResponse> readAllByPrezzo(BigDecimal prezzo) {
        return autoRepo.findAllByPrezzoOrderByPrezzoAsc(prezzo).stream().map(mapper).collect(Collectors.toList());
    }

    public List<VeicoloResponse> readAllByUsato(boolean usato) {
        return autoRepo.findAllByUsato(usato).stream().map(mapper).collect(Collectors.toList());
    }

    public List<VeicoloResponse> findAllByRange(BigDecimal min, BigDecimal max) {
        return autoRepo.findVeicoloByRange(min, max).stream().map(mapper).collect(Collectors.toList());
    }

    public List<VeicoloResponse> findAllByStatoVendita(StatoVendita statoVendita) {
        return autoRepo.findAllByStatoVendita(statoVendita).stream().map(mapper).collect(Collectors.toList());
    }

    public List<VeicoloResponse> findAllByStatoVenditaAsc() {
        return autoRepo.findAllOrderByStatoVenditaAsc().stream().map(mapper).collect(Collectors.toList());
    }

    public VeicoloResponse createVeicolo(VeicoloRequest veicoloRequest) {
        Veicolo request =mapper.convertDTO(veicoloRequest);
        this.autoRepo.save(request);
        return mapper.apply(request);
    }

    public VeicoloResponse patchStatoUsato(long id, boolean stato) {
        Veicolo veicolo = getById(id);
        return mapper.apply(veicolo);
    }

    public VeicoloResponse updateVeicolo(VeicoloRequest veicolo, long id) {
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
        v.setUsato(veicolo.getUsato());
        v.setSconto(v.getSconto());
        v.setAccessori(veicolo.getAccessori());
        v.setStatoVendita(veicolo.getStatoVendita());

        this.autoRepo.saveAndFlush(v);
        return mapper.apply(v);
    }


    public VeicoloResponse patchByVeicoloRequest(long id, VeicoloRequest veicoloRequest) {
        Veicolo veicoloResult = getById(id);



        if (veicoloRequest.getColore() != null)
            veicoloResult.setColore(veicoloRequest.getColore());

        if (veicoloRequest.getTipoDiCambio() != null)
            veicoloResult.setTipoDiCambio(veicoloRequest.getTipoDiCambio());

        if (veicoloRequest.getAnnoImmatricolazione() != null)
            veicoloResult.setAnnoImmatricolazione(veicoloRequest.getAnnoImmatricolazione());

        if (veicoloRequest.getAlimentazione() != null)
            veicoloResult.setAlimentazione(veicoloRequest.getAlimentazione());

        if (veicoloRequest.getPrezzo() != null)
            veicoloResult.setPrezzo(veicoloRequest.getPrezzo());

        if (veicoloRequest.getSconto() != null)
            veicoloResult.setSconto(veicoloRequest.getSconto());

        if (veicoloRequest.getAccessori() != null)
            veicoloResult.setAccessori(veicoloRequest.getAccessori());

        this.autoRepo.saveAndFlush(veicoloResult);
        return mapper.apply(veicoloResult);
    }

    public VeicoloResponse patchStatoVenditaVeicolo(long id, StatoVendita statoVendita) {
        Veicolo veicoloResult = getById(id);

        veicoloResult.setStatoVendita(statoVendita);
        this.autoRepo.saveAndFlush(veicoloResult);
        return mapper.apply(veicoloResult);
    }

    public boolean deleteVeicoloById(long id) {
        try {
            Veicolo v = getById(id);
        }catch (Exception  e){
            return false;
        }
        this.autoRepo.deleteById(id);
        return true;
    }

    public boolean deleteVeicolo(VeicoloRequest veicoloRequest) {
        this.autoRepo.delete(mapper.convertDTO(veicoloRequest));
        return true;
    }

}
