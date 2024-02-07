package com.develhope.spring.transazioni.noleggio.controller;

import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.repository.NoleggioRepo;
import com.develhope.spring.transazioni.noleggio.service.NoleggioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/noleggio")
public class ControllerNoleggio {

    @Autowired
    private NoleggioService noleggioService;



    @PostMapping("/create")
    public Noleggio createNoleggio(@RequestBody Noleggio noleggio) {
        return noleggioService.createNoleggio(noleggio);
    }

    @GetMapping("/getList")
    public List<Noleggio> getAll() {
        return noleggioService.getAll();
    }

    @GetMapping("/get/{id}")
    public Noleggio getNoleggioById(@PathVariable Long id) {
        return noleggioService.getNoleggioById(id);

    }
    @DeleteMapping("delete/{id}")
    public void deleteNoleggio(@PathVariable Long id) {
        noleggioService.deleteNoleggio(id);
    }


    @PatchMapping("/update/{id}")
    public Noleggio updateNoleggio(@PathVariable Long id, @RequestBody Noleggio noleggio) {
        return noleggioService.updateNoleggio(id, noleggio);
    }
}
