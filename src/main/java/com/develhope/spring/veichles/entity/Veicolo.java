package com.develhope.spring.veichles.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
@Component
@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor

public class Veicolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Enumerated(value = EnumType.STRING)
    private TipoVeicolo tipoVeicolo;

    @Column(name = "modello", nullable = false)
    private String modello;

    @Column(name = "cilindrata", nullable = false)
    private int cilindrata;

    @Column(name = "colore", nullable = false)
    private String colore;

    @Column(name = "potenza", nullable = false)
    private int potenza;

    @Column(name = "tipoDiCambio", nullable = false)
    private String tipoDiCambio;

    @Column(name = "annoImmatricolazione", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime annoImmatricolazione;

    @Column(name = "alimentazione", nullable = false)
    private String alimentazione;

    @Column(name = "prezzo", nullable = false)
    private double prezzo;

    @Column(name = "sconto", nullable = false)
    private double sconto;
}
