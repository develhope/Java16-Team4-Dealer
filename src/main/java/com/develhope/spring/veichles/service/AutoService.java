package com.develhope.spring.veichles.service;

import com.develhope.spring.veichles.repository.AutoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoService {

    @Autowired
    private AutoRepo autoRepo;
}
