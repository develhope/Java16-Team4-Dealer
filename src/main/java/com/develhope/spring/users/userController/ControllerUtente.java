package com.develhope.spring.users.userController;

import com.develhope.spring.users.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ControllerUtente {

    @Autowired
    private UtenteService utenteService;


}