package com.ddd.projetoContaBancaria.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DinheiroTest {

    @Test
    void naoDevePermitirOperacoesComMoedasDiferentesSemConverter(){
        Dinheiro cemReais = Dinheiro.emBRL(100);
        Dinheiro cemDolares = new Dinheiro(BigDecimal.valueOf(100), Moeda.USD);

        assertThrows(IllegalArgumentException.class, () -> {
            cemReais.somar(cemDolares);
        });
    }

    @Test
    void deveSomarValoresComMesmaMoeda(){
        Dinheiro cemReais = Dinheiro.emBRL(100);
        Dinheiro cinquentaReais = Dinheiro.emBRL(50);

        Dinheiro resultado = cemReais.somar(cinquentaReais);
        assertEquals(Dinheiro.emBRL(150), resultado);
    }
}
