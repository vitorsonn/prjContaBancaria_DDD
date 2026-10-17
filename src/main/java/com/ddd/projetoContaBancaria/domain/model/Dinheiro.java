package com.ddd.projetoContaBancaria.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Dinheiro(BigDecimal quantia, Moeda moeda) {

    public Dinheiro{
        Objects.requireNonNull(quantia, "A quantia não pode ser nula");
        Objects.requireNonNull(moeda, "A moeda não pode ser nula");
    }

    public static Dinheiro emBRL(double quantia){
        return new Dinheiro(BigDecimal.valueOf(quantia), Moeda.BRL);
    }

    public boolean isMaiorQue(Dinheiro outro){
        validarMesmaMoeda(outro);
        return this.quantia.compareTo(outro.quantia) > 0;
    }

    public boolean isMenorOuIgualZero(){
        return this.quantia.compareTo(BigDecimal.ZERO) <= 0;
    }

    public Dinheiro somar(Dinheiro outro){
        validarMesmaMoeda(outro);
        return new Dinheiro(this.quantia.add(outro.quantia), this.moeda);
    }

    public Dinheiro subtrair(Dinheiro outro){
        validarMesmaMoeda(outro);
        return new Dinheiro(this.quantia.subtract(outro.quantia), this.moeda);
    }

    private void validarMesmaMoeda(Dinheiro outro) {
        if (this.moeda != outro.moeda) {
            throw new IllegalArgumentException("Não é possível comparar/operar moedas diferentes diretamente sem conversão.");
        }
    }
}
