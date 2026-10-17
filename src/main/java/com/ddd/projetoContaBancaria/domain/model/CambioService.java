package com.ddd.projetoContaBancaria.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CambioService {

    public Dinheiro converter(Dinheiro origem, Moeda moedaDestino){
        if(origem.moeda() == moedaDestino){
            return origem;
        }

        BigDecimal taxaCambio = obterTaxaCambio(origem.moeda(), moedaDestino);
        BigDecimal valorConvertido = origem.quantia().multiply(taxaCambio).setScale(2, RoundingMode.HALF_UP);

        return new Dinheiro(valorConvertido, moedaDestino);
    }


    private BigDecimal obterTaxaCambio(Moeda origem, Moeda destino){
        if(origem == Moeda.BRL && destino ==Moeda.USD){
            return new BigDecimal("0.20");
        }

        if(origem == Moeda.USD && destino ==Moeda.BRL){
            return new BigDecimal("5.00");
        }

        return BigDecimal.ONE;
    }
}
