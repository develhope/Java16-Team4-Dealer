package com.develhope.spring.transazioni.noleggio.noleggioController;

import com.develhope.spring.transazioni.noleggio.dto.NoleggioRequest;
import com.develhope.spring.transazioni.noleggio.dto.NoleggioResponse;
import com.develhope.spring.transazioni.noleggio.entity.Noleggio;
import com.develhope.spring.transazioni.noleggio.service.NoleggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noleggio")
public class NoleggioController {

    @Autowired
    NoleggioService noleggioService;

    @PostMapping("/nuovoNoleggio/{idCustomer}/{idVeicolo}/{idVenditore}")
    public ResponseEntity<NoleggioResponse> nuovoNoleggio (                                                                                                                               @PathVariable Long idCustomer,
                                           @PathVariable Long idVeicolo,
                                           @RequestBody NoleggioRequest noleggioRequest,
                                           @PathVariable (required = false) Long idVenditore){
        return noleggioService.createNoleggio(idCustomer,idVeicolo,idVenditore,noleggioRequest);
    }
    @GetMapping("/AllNoleggio")
    public List<Noleggio> tutti_iNoleggi (){
        return noleggioService.getAll();
    }
    @PutMapping("/UpdateNoleggio/{idNoleggio}")
    public NoleggioResponse updateNoleggio (@PathVariable Long idNoleggio, @RequestBody NoleggioRequest request){
        return noleggioService.updateNoleggio(idNoleggio, request);
    }
    @PatchMapping("/patchNoleggio/{idNoleggio}")
    public NoleggioResponse patchNoleggio (@PathVariable Long idNoleggio, @RequestBody NoleggioRequest request){
        return noleggioService.patchONoleggio(request,idNoleggio);
    }

    @DeleteMapping("/cancellaNoleggio/{idNoleggio}")
    public ResponseEntity<String> deleteNoleggio(@PathVariable Long idNoleggio) {
        return noleggioService.deleteNoleggio(idNoleggio);
    }
}
