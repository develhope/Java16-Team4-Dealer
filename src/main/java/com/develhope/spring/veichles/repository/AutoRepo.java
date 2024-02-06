package com.develhope.spring.veichles.repository;

import com.develhope.spring.veichles.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoRepo extends JpaRepository <Auto,Long> {
}
