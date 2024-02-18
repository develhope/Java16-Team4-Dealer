package com.develhope.spring.users.service.adminServices;

import com.develhope.spring.transazioni.noleggio.service.NoleggioService;
import com.develhope.spring.veichles.entity.StatoVendita;
import com.develhope.spring.veichles.entity.TipoVeicolo;
import com.develhope.spring.veichles.entity.Veicolo;
import com.develhope.spring.veichles.dto.VeicoloRequest;
import com.develhope.spring.veichles.service.VeicoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private VeicoloService veicoloService;


    @Autowired
    private NoleggioService noleggioService;


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

    public Veicolo patchVeicolo(@RequestBody VeicoloRequest veicoloRequest,@PathVariable long id){
        return this.veicoloService.patchByVeicoloRequest(id,veicoloRequest);
    }
     public Veicolo patchStatoVenditaVeicolo(@PathVariable long id, @RequestParam StatoVendita statoVendita){
         return this.veicoloService.patchStatoVenditaVeicolo(id, statoVendita);
    }

    public boolean deleteVeicoloById(long id){
        return this.veicoloService.deleteVeicoloById(id);
    }

    public boolean deleteVeicolo(Veicolo veicolo){
        return this.veicoloService.deleteVeicolo(veicolo);
    }


}
