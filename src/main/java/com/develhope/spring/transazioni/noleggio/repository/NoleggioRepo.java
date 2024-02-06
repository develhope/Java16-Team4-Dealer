package com.develhope.spring.transazioni.noleggio.repository;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoleggioRepo extends JpaRepository <Noleggio,Long> {
}
