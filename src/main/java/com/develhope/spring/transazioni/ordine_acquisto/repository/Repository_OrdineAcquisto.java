package com.develhope.spring.transazioni.ordine_acquisto.repository;

import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.veichles.entity.Veicolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Repository_OrdineAcquisto extends JpaRepository <Ordine_Acquisto, Long>{

//    @Query("SELECT o FROM Ordine_Acquisto o ORDER BY o.StatoOrdine")
//    List<Ordine_Acquisto> findAllOrderByStatoOrdine();
}
