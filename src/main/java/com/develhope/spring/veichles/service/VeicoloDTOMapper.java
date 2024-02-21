package com.develhope.spring.veichles.service;

import com.develhope.spring.veichles.dto.VeicoloRequest;
import com.develhope.spring.veichles.dto.VeicoloResponse;
import com.develhope.spring.veichles.entity.Veicolo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@NoArgsConstructor
public class VeicoloDTOMapper implements Function<Veicolo, VeicoloResponse> {
    @Override
    public VeicoloResponse apply(Veicolo veicolo) {
        return convertEntity(veicolo);
    }


    public Veicolo convertDTO(VeicoloRequest veicoloRequest) {
        return Veicolo.builder()
                .id(veicoloRequest.getId())
                .marca(veicoloRequest.getMarca())
                .tipoVeicolo(veicoloRequest.getTipoVeicolo())
                .modello(veicoloRequest.getModello())
                .cilindrata(veicoloRequest.getCilindrata())
                .colore(veicoloRequest.getColore())
                .potenza(veicoloRequest.getPotenza())
                .tipoDiCambio(veicoloRequest.getTipoDiCambio())
                .annoImmatricolazione(veicoloRequest.getAnnoImmatricolazione())
                .alimentazione(veicoloRequest.getAlimentazione())
                .prezzo(veicoloRequest.getPrezzo())
                .usato(veicoloRequest.getUsato())
                .sconto(veicoloRequest.getSconto())
                .accessori(veicoloRequest.getAccessori())
                .statoVendita(veicoloRequest.getStatoVendita())
                .build();
    }
    public VeicoloResponse convertEntity(Veicolo veicolo) {
        return new VeicoloResponse(
                veicolo.getId(),
                veicolo.getMarca(),
                veicolo.getModello(),
                veicolo.getColore(),
                veicolo.getTipoDiCambio(),
                veicolo.getAlimentazione(),
                veicolo.getAccessori(),
                veicolo.getTipoVeicolo(),
                veicolo.getCilindrata(),
                veicolo.getPotenza(),
                veicolo.getAnnoImmatricolazione(),
                veicolo.getPrezzo(),
                veicolo.isUsato(),
                veicolo.getSconto(),
                veicolo.getStatoVendita());
    }
}
