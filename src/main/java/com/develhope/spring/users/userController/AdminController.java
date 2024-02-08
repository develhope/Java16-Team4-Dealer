package com.develhope.spring.users.userController;

import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.users.service.AdminService;
import com.develhope.spring.veichles.entity.Veicolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UtenteRepo utenteRepo;

    @PostMapping("/addVeicolo")
    public Veicolo addVeicolo (@RequestBody Veicolo nuovoVeicolo){
        return adminService.addVeicolo(nuovoVeicolo);

    }
    @PatchMapping("/modVeicolo/{id}")
    public ResponseEntity<Veicolo> modVeicolo(@RequestBody Veicolo veicoloModificato) {
        Veicolo veicoloAggiornato = adminService.modVeicolo(veicoloModificato.getId(), veicoloModificato);
        if (veicoloAggiornato != null && veicoloAggiornato.getId().equals(veicoloAggiornato.getId())) {
            return ResponseEntity.ok(veicoloAggiornato);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}