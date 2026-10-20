package com.ddd.projetoContaBancaria.infra.persistence;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transferencias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaEntity {

    @Id
    private UUID id;
    private UUID contaOrigemId;
    private UUID contaDestinoId;
    private BigDecimal valorEnviado;
    private String moedaEnviada;
    private BigDecimal valorRecebido;
    private String moedaRecebida;
    private LocalDateTime dataHora;
}
