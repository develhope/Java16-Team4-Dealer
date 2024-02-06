package com.develhope.spring.users.repository;

import com.develhope.spring.users.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepo extends JpaRepository <Utente, Long> {

}
