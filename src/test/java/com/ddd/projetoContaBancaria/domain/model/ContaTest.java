package com.ddd.projetoContaBancaria.domain.model;


import com.ddd.projetoContaBancaria.domain.exception.ContaCongeladaException;
import com.ddd.projetoContaBancaria.domain.exception.SaldoInsuficienteException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    @Test
    void devePermitirSaqueUsandoLimiteChequeEspecial(){

        //100 de saldo e 200 de limite, total disponivel 300
        Conta conta = Conta.criarConta(Dinheiro.emBRL(100), Dinheiro.emBRL(200));

        //Saque de 250, que é maior que o saldo mas menor que o total disponivel
        conta.sacar(Dinheiro.emBRL(250));

        //O saldo esperado deve ficar negativo (-150), mas a operação deve ser permitida por conta do cheque
        assertEquals(Dinheiro.emBRL(-150), conta.getSaldo());
    }

    @Test
    void naoDevePermitirSaqueAcimaDoSaldoEChequeEspecial(){
        Conta conta = Conta.criarConta(Dinheiro.emBRL(100), Dinheiro.emBRL(200));

        // Limite total é 300, tentar sacar 301 deve falhar
        assertThrows(SaldoInsuficienteException.class, () -> conta.sacar(Dinheiro.emBRL(301)));
    }

    @Test
    void naoDevePermitirOperacaoEmContaCongelada() {
        Conta conta = Conta.criarConta(Dinheiro.emBRL(500), Dinheiro.emBRL(0));
        conta.congelar();

        assertThrows(ContaCongeladaException.class, () -> conta.sacar(Dinheiro.emBRL(100)));
        assertThrows(ContaCongeladaException.class, () -> conta.depositar(Dinheiro.emBRL(100)));
    }





}
