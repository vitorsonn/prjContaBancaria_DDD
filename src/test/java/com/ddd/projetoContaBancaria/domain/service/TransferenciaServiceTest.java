package com.ddd.projetoContaBancaria.domain.service;

import com.ddd.projetoContaBancaria.domain.exception.ContaCongeladaException;
import com.ddd.projetoContaBancaria.domain.model.CambioService;
import com.ddd.projetoContaBancaria.domain.model.Conta;
import com.ddd.projetoContaBancaria.domain.model.Dinheiro;
import com.ddd.projetoContaBancaria.domain.model.Moeda;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransferenciaServiceTest {

    private final CambioService cambioService = new CambioService();
    private final TransferenciaService transferenciaService = new TransferenciaService(cambioService);

    @Test
    void deveTransferirEntreContasDeMesmaMoeda() {
        Conta origem = Conta.criarConta(Dinheiro.emBRL(500), Dinheiro.emBRL(0));
        Conta destino = Conta.criarConta(Dinheiro.emBRL(100), Dinheiro.emBRL(0));

        transferenciaService.transferir(origem, destino, Dinheiro.emBRL(200));

        assertEquals(Dinheiro.emBRL(300), origem.getSaldo());
        assertEquals(Dinheiro.emBRL(300), destino.getSaldo());
    }

    @Test
    void deveTransferirComConversaoDeMoeda() {
        // Origem em BRL, Destino em USD
        Conta origem = Conta.criarConta(Dinheiro.emBRL(1000), Dinheiro.emBRL(0));
        Conta destino = Conta.criarConta(new Dinheiro(java.math.BigDecimal.valueOf(50), Moeda.USD), null);

        // Transfere 500 BRL (pela taxa 0.20 deve virar 100 USD no destino)
        transferenciaService.transferir(origem, destino, Dinheiro.emBRL(500));

        assertEquals(Dinheiro.emBRL(500), origem.getSaldo());
        assertEquals(new Dinheiro(new java.math.BigDecimal("150.00"), Moeda.USD), destino.getSaldo());
    }

    @Test
    void naoDevePermitirTransferenciaParaAMesmaConta() {
        Conta conta = Conta.criarConta(Dinheiro.emBRL(500), Dinheiro.emBRL(0));

        assertThrows(IllegalArgumentException.class,
                () -> transferenciaService.transferir(conta, conta, Dinheiro.emBRL(100)));
    }

    @Test
    void naoDeveTransferirSeDestinoEstiverCongelada() {
        Conta origem = Conta.criarConta(Dinheiro.emBRL(500), Dinheiro.emBRL(0));
        Conta destino = Conta.criarConta(Dinheiro.emBRL(100), Dinheiro.emBRL(0));
        destino.congelar();

        assertThrows(ContaCongeladaException.class,
                () -> transferenciaService.transferir(origem, destino, Dinheiro.emBRL(100)));
    }

}
