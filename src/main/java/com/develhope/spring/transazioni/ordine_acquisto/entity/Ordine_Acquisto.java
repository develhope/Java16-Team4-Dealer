package com.develhope.spring.transazioni.ordine_acquisto.entity;

import com.develhope.spring.users.entity.Utente;
import com.develhope.spring.veichles.entity.Veicolo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Ordine_Acquisto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal anticipo;
    private boolean pagato;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Utente customer;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Utente vendor;

    @OneToOne
    @JoinColumn(name = "veicolo_id")
    private Veicolo veicolo;

    @Enumerated (EnumType.STRING)
    private StatoOrdine statoOrdine;

    @Enumerated (EnumType.STRING)
    private TipoTransazione tipoTransazione;
}
