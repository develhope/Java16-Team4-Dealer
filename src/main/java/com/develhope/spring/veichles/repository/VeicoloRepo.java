package com.develhope.spring.veichles.repository;

import com.develhope.spring.veichles.entity.Veicolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeicoloRepo extends JpaRepository <Veicolo,Long> {
}
