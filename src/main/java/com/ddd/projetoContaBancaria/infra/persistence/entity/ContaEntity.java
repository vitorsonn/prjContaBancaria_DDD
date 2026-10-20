package com.ddd.projetoContaBancaria.infra.persistence.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "contas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaEntity {

    @Id
    private UUID id;
    private BigDecimal saldo;
    private String moedaSaldo;
    private BigDecimal limiteChequeEspecial;
    private String moedaLimite;
    private boolean congelada;




}
