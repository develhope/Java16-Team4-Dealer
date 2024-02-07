package com.develhope.spring.veichles.controller;

import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class ControllerAuto {

    @Autowired
    private AutoService autoService;

    public Veicolo create(Veicolo veicolo){
        return new Veicolo();
    }

    public Veicolo read(@RequestParam(value = "id") long id){
        return new Veicolo();
    }

    public Veicolo update(@RequestBody @Validated Veicolo veicolo){
        return new Veicolo();
    }
    public Veicolo patch(@RequestBody Veicolo veicolo){
        return new Veicolo();
    }

    public Veicolo delete(@RequestParam long id){

        return new Veicolo();
    }

}
