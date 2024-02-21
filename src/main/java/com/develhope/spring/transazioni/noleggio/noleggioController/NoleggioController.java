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

    @PostMapping("/nuovoNoleggio")
    public NoleggioResponse nuovoNoleggio (@RequestBody NoleggioRequest noleggioRequest){
        return noleggioService.createNoleggio(noleggioRequest);
    }
    @GetMapping("/AllNoleggio")
    public List<Noleggio> tutti_iNoleggi (){
        return noleggioService.getAll();
    }
    @PutMapping("/UpdateNoleggio")
    public NoleggioResponse updateNoleggio (@PathVariable Long idNoleggio, @RequestBody NoleggioRequest request){
        return noleggioService.updateNoleggio(idNoleggio, request);
    }
    @PatchMapping("/patchNoleggio")
    public NoleggioResponse patchNoleggio (@PathVariable Long idNoleggio, @RequestBody NoleggioRequest request){
        return noleggioService.patchONoleggio(request,idNoleggio);
    }

    @DeleteMapping("/cancellaNoleggio/{id}")
    public ResponseEntity<String> deleteNoleggio(@PathVariable Long id) {
        return noleggioService.deleteNoleggio(id);
    }
}
