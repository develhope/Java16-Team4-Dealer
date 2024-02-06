package com.develhope.spring.service;

import com.develhope.spring.repository.NoleggioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoleggioService {

    @Autowired
    private NoleggioRepo noleggioRepo;
}
