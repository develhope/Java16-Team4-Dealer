package com.develhope.spring.transazioni.ordine_acquisto.repository;

import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.transazioni.ordine_acquisto.entity.StatoOrdine;
import com.develhope.spring.veichles.entity.Veicolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Repository_OrdineAcquisto extends JpaRepository<Ordine_Acquisto, Long> {

    List<Ordine_Acquisto> findAllByStatoOrdine(StatoOrdine statoOrdine);
    List<Ordine_Acquisto> findAllByStatoOrdineOrderByStatoOrdineAsc();

}
