package com.develhope.spring.transazioni.noleggio.service;

import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoleggioService {

    @Autowired
    private NoleggioRepo noleggioRepo;
}
