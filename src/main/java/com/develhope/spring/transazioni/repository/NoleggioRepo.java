package com.develhope.spring.transazioni.repository;

import com.develhope.spring.transazioni.Noleggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoleggioRepo extends JpaRepository <Noleggio,Long> {
}
