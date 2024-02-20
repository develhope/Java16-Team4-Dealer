package com.develhope.spring.transazioni.ordine_acquisto.service;

import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoModel;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoRequest;
import com.develhope.spring.transazioni.ordine_acquisto.dto.OrdineAcquistoResponse;
import com.develhope.spring.transazioni.ordine_acquisto.entity.Ordine_Acquisto;
import com.develhope.spring.transazioni.ordine_acquisto.repository.Repository_OrdineAcquisto;
import com.develhope.spring.users.repository.UtenteRepo;
import com.develhope.spring.veichles.repository.VeicoloRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class OrdineAcquistoService {

    @Autowired
    private Repository_OrdineAcquisto repositoryOrdineAcquisto;

    @Autowired
    private OrdineAcquistoModel mapper;

    @Autowired
    private VeicoloRepo veicoloRepo;

    @Autowired
    private UtenteRepo utenteRepo;

    @SneakyThrows
    private Ordine_Acquisto getById (Long id){
        Optional<Ordine_Acquisto> oA = this.repositoryOrdineAcquisto.findById(id);
        if (oA.isEmpty()) throw new IOException("Ordine o Acquisto non trovato");
        return oA.get();
    }

    private Ordine_Acquisto requestToEntity (OrdineAcquistoRequest request){
        return mapper.convertModelToEnt(mapper.convertRequestToMod(request));
    }

    private OrdineAcquistoResponse entityToResponse (Ordine_Acquisto entity){
        return mapper.convertModelToResponse(mapper.convertEntToModel(entity));
    }

    @SneakyThrows
    public Ordine_Acquisto findById (Long id){
        return getById(id);
    }

    public OrdineAcquistoResponse createOA (OrdineAcquistoRequest request){
        Ordine_Acquisto oa = requestToEntity(request);
        this.repositoryOrdineAcquisto.save(oa);
        return entityToResponse(oa);
    }
/**
    public OrdineAcquistoResponse createAcquisto(OrdineAcquistoRequest requestA,
                                                 Long idUtente,
                                                 Long idVeicolo){

    }
*/
    public OrdineAcquistoResponse createAcquisto (OrdineAcquistoRequest request){
        Ordine_Acquisto oa = requestToEntity(request);
        this.repositoryOrdineAcquisto.save(oa);
        return entityToResponse(oa);
    }

    public OrdineAcquistoResponse updateOA (OrdineAcquistoRequest request, Long id){
        Ordine_Acquisto oa = getById(id);

        oa.setAnticipo(request.getAnticipo());
        oa.setPagato(request.getPagato());
        oa.setCustomer(request.getCustomer());
        oa.setVendor(request.getVendor());
        oa.setVeicolo(request.getVeicolo());
        oa.setStatoOrdine(request.getStatoOrdine());
        oa.setStatoVeicolo(request.getStatoVeicolo());
        this.repositoryOrdineAcquisto.saveAndFlush(oa);

        return entityToResponse(oa);
    }

    public OrdineAcquistoResponse patchOA (OrdineAcquistoRequest request, Long id){
        Ordine_Acquisto oa = getById(id);

        if (request.getAnticipo()!=null)
            oa.setAnticipo(request.getAnticipo());

        if (request.getPagato()!=null)
            oa.setPagato(request.getPagato());

        if (request.getCustomer()!=null)
            oa.setCustomer(request.getCustomer());

        if (request.getVendor()!=null)
            oa.setVendor(request.getVendor());

        if (request.getVeicolo()!=null)
            oa.setVeicolo(request.getVeicolo());

        if (request.getStatoOrdine()!=null)
            oa.setStatoOrdine(request.getStatoOrdine());

        if (request.getStatoVeicolo()!=null)
            oa.setStatoVeicolo(request.getStatoVeicolo());

        this.repositoryOrdineAcquisto.saveAndFlush(oa);

        return entityToResponse(oa);
    }

    public boolean deleteOA (long id){
        Ordine_Acquisto oa = getById(id);
        this.repositoryOrdineAcquisto.deleteById(id);
        return true;
    }
}
