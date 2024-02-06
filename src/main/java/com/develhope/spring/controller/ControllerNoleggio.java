package com.develhope.spring.controller;

import com.develhope.spring.service.NoleggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noleggio")
public class ControllerNoleggio {

    @Autowired
    private NoleggioService noleggioService;
}
