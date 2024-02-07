package com.develhope.spring.veichles.repository;

import com.develhope.spring.veichles.entity.TipoVeicolo;
import com.develhope.spring.veichles.entity.Veicolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface VeicoloRepo extends JpaRepository <Veicolo,Long> {

    List<Veicolo> findAllByTipoVeicolo(TipoVeicolo tipoVeicolo);

    List<Veicolo> findAllByMarca(String marca);
    List<Veicolo> findAllByModello(String modello);
    List<Veicolo> findAllByCilindrata(int cilindrata);
    List<Veicolo> findAllByColore(String colore);
    List<Veicolo> findAllByMarca(int potenza);
    List<Veicolo> findAllByTipoDiCambio(String tipoDiCambio);
    List<Veicolo> findAllByAnnoImmatricolazione(OffsetDateTime annoImmatricolazione);

    List<Veicolo> findAllByAlimentazione(String alimentazione);
    List<Veicolo> findAllByPrezzo(BigDecimal prezzo);
    List<Veicolo> findAllBySconto(BigDecimal sconto);

}
