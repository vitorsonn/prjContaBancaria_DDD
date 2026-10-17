package com.ddd.projetoContaBancaria.domain.service;

import com.ddd.projetoContaBancaria.domain.model.CambioService;
import com.ddd.projetoContaBancaria.domain.model.Conta;
import com.ddd.projetoContaBancaria.domain.model.Dinheiro;

import java.util.Objects;

public class TransferenciaService {

    private final CambioService cambioService;

    public TransferenciaService(CambioService cambioService) {
        this.cambioService = Objects.requireNonNull(cambioService, "cambioService cannot be null");
    }

    public void transferir(Conta origem, Conta destino, Dinheiro valor ){
        if(origem.getId().equals(destino.getId())){
            throw new IllegalArgumentException("Não é possível transferir para a mesma conta");
        }

        if(valor == null || valor.isMenorOuIgualZero()){
            throw new IllegalArgumentException("Valor da transferência deve ser maior que zero");
        }

        origem.sacar(valor);

        Dinheiro valorDeposito = cambioService.converter(valor, destino.getSaldo().moeda());

        destino.depositar(valorDeposito);

    }
}
