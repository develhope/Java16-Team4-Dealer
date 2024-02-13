package com.develhope.spring.veichles.repository;

import com.develhope.spring.veichles.entity.TipoVeicolo;
import com.develhope.spring.veichles.entity.Veicolo;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    List<Veicolo> findAllByPotenza(int potenza);
    List<Veicolo> findAllByTipoDiCambio(String tipoDiCambio);
    List<Veicolo> findAllByAnnoImmatricolazione(OffsetDateTime annoImmatricolazione);

    List<Veicolo> findAllByAlimentazione(String alimentazione);
    List<Veicolo> findAllByPrezzoOrderByPrezzoAsc(BigDecimal prezzo);
    List<Veicolo> findAllBySconto(BigDecimal sconto);

    @Query("SELECT v FROM Veicolo v WHERE v.prezzo BETWEEN :prezzoMin AND :prezzoMax ORDER BY v.prezzo ASC")
    List<Veicolo> findVeicoloByRange(
            @Param("prezzoMin") BigDecimal prezzoMin,
            @Param("prezzoMax") BigDecimal prezzoMax
    );
}
