package com.ddd.projetoContaBancaria.domain.model;

import com.ddd.projetoContaBancaria.domain.exception.ContaCongeladaException;
import com.ddd.projetoContaBancaria.domain.exception.SaldoInsuficienteException;

import java.math.BigDecimal;
import java.util.Objects;

public class Conta {

    private final ContaID id;
    private Dinheiro saldo;
    private Dinheiro limiteChequeEspecial;
    private boolean congelada;



    public Conta(ContaID id, Dinheiro saldoInicial, Dinheiro limiteChequeEspecial) {
        this.id = Objects.requireNonNull(id, "ID não pode ser nulo");
        this.saldo = Objects.requireNonNull(saldoInicial, "Saldo inicial não pode ser nulo");
        // Limite padrão é 0 na mesma moeda do saldo, caso venha nulo
        this.limiteChequeEspecial = limiteChequeEspecial != null
                ? limiteChequeEspecial
                : new Dinheiro(BigDecimal.ZERO, saldoInicial.moeda());

        if (this.saldo.moeda() != this.limiteChequeEspecial.moeda()) {
            throw new IllegalArgumentException("A moeda do limite deve ser igual à moeda da conta");
        }

        this.congelada = false;
    }

    public static Conta criarConta(Dinheiro saldoInicial, Dinheiro limiteChequeEspecial){
        return new Conta(ContaID.gerarNovoID(), saldoInicial, limiteChequeEspecial);
    }

    public void sacar(Dinheiro valor){
        validarOperacaoDisponivel(valor);

        Dinheiro saldoDisponivelTotal = this.saldo.somar(this.limiteChequeEspecial);

        if(valor.isMaiorQue(saldoDisponivelTotal)){
            throw new SaldoInsuficienteException("Saldo e limite insuficientes para realizar o saque");
        }

        this.saldo = this.saldo.subtrair(valor);

    }

    public void depositar(Dinheiro valor){
        validarOperacaoDisponivel(valor);
        this.saldo = this.saldo.somar(valor);
    }

    public void congelar(){
        this.congelada = true;
    }

    public void descongelar(){
        this.congelada = false;
    }

    private void validarOperacaoDisponivel(Dinheiro valor) {
        if (this.congelada) {
            throw new ContaCongeladaException("Conta congelada por suspeita de fraude. Operação impedida.");
        }

        if (valor.isMenorOuIgualZero()) {
            throw new IllegalArgumentException("O valor da operação deve ser maior que zero.");
        }

        if (valor.moeda() != this.saldo.moeda()) {
            throw new IllegalArgumentException("Moeda do valor incompatível com a moeda da conta.");
        }
    }

    public ContaID getId() {
        return id;
    }

    public Dinheiro getSaldo() {
        return saldo;
    }

    public Dinheiro getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public boolean isCongelada() {
        return congelada;
    }
}
