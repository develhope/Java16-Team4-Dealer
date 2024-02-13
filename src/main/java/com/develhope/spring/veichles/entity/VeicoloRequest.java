package com.develhope.spring.veichles.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


public class VeicoloRequest {

    private String marca;

    private TipoVeicolo tipoVeicolo;

    private String modello;

    private int cilindrata;

    private String colore;

    private int potenza;

    private String tipoDiCambio;

    private OffsetDateTime annoImmatricolazione;

    private String alimentazione;

    private BigDecimal prezzo;

    private boolean usato;

    private double sconto;

    private String accessori;
}
