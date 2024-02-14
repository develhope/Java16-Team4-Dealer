package com.develhope.spring.users.service.adminServices;

import com.develhope.spring.veichles.entity.TipoVeicolo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.service.VeicoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private VeicoloService veicoloService;


    @Autowired
    private VeicoloService noleggioService;
//
//    @Autowired
//    private UtenteService utenteService;

    public Veicolo createVeicolo (Veicolo veicolo) {
        return this.veicoloService.createVeicolo(veicolo);
    }

    public Veicolo updateVeicolo(Veicolo veicolo,long id){
        return this.veicoloService.updateVeicolo(veicolo,id);
    }

    public List<Veicolo> readAllByType(TipoVeicolo tipoVeicolo){
        return this.veicoloService.readAllByType(tipoVeicolo);
    }

    public List<Veicolo> readAll(){
        return this.veicoloService.readAll();
    }

    public Veicolo patchVeicolo(Map<String,Object> veicoloJSON, long id){
        return this.veicoloService.patchVeicolo(veicoloJSON,id);
    }

    public boolean deleteVeicoloById(long id){
        return this.veicoloService.deleteVeicoloById(id);
    }

    public boolean deleteVeicolo(Veicolo veicolo){
        return this.veicoloService.deleteVeicolo(veicolo);
    }


}
