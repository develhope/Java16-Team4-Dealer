package com.develhope.spring.users.service.customerServices;

import com.develhope.spring.veichles.service.VeicoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceVeicolo {

    @Autowired
    VeicoloService veicoloService;

    //public

}
//public List<Giocatore> searchGiocatore(String ruolo, Integer altezza) {
//    if (ruolo != null && altezza == null) {
//        return giocatoreRepository.findByRuolo(ruolo);
//    } else if (ruolo == null && altezza != null) {
//        return giocatoreRepository.findByAltezza(altezza);
//    } else return giocatoreRepository.findAll();
//
//}