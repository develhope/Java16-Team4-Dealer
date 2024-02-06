package com.develhope.spring.transazioni.ordine_acquisto.repository;

import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository_OrdineAcquisto extends JpaRepository <Ordine_Acquisto, Long>{
}
