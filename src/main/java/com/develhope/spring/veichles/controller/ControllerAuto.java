package com.develhope.spring.veichles.controller;

import com.develhope.spring.veichles.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class ControllerAuto {

    @Autowired
    private AutoService autoService;
}
