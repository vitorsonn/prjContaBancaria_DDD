package com.ddd.projetoContaBancaria.application;


import com.ddd.projetoContaBancaria.application.dto.TransferenciaRequestDTO;
import com.ddd.projetoContaBancaria.application.dto.TransferenciaResponseDTO;
import com.ddd.projetoContaBancaria.application.usecase.RealizarTransferenciaUseCase;
import com.ddd.projetoContaBancaria.domain.model.CambioService;
import com.ddd.projetoContaBancaria.domain.model.Conta;
import com.ddd.projetoContaBancaria.domain.model.ContaID;
import com.ddd.projetoContaBancaria.domain.model.Dinheiro;
import com.ddd.projetoContaBancaria.domain.repo.ContaRepository;
import com.ddd.projetoContaBancaria.domain.service.TransferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RealizarTransferenciaUseCaseTest {


    private RealizarTransferenciaUseCase realizerTransferenciaUseCase;
    private ContaRepository fakeRepository;

    @BeforeEach
    void setUp(){
        fakeRepository = new ContaRepository(){
            private final Map<ContaID, Conta> tabela = new HashMap<>();

            public Optional<Conta> findById(ContaID id) {
                return Optional.ofNullable(tabela.get(id));
            }

            public void save(Conta conta) {
                tabela.put(conta.getId(), conta);
            }

        };

        CambioService cambioService = new CambioService();
        TransferenciaService transferenciaService = new TransferenciaService(cambioService);
        realizerTransferenciaUseCase = new RealizarTransferenciaUseCase(fakeRepository, transferenciaService);

    }

    @Test
    void deveExecutarTransferenciaComSucesso(){

        Conta origem = Conta.criarConta(Dinheiro.emBRL(500), Dinheiro.emBRL(0));
        Conta destino = Conta.criarConta(Dinheiro.emBRL(100), Dinheiro.emBRL(0));

        fakeRepository.save(origem);
        fakeRepository.save(destino);

        TransferenciaRequestDTO requestDTO = new TransferenciaRequestDTO(
                origem.getId().valor(),
                destino.getId().valor(),
                new BigDecimal("200.00"),
                "BRL"
        );

        TransferenciaResponseDTO responseDTO = realizerTransferenciaUseCase.executar(requestDTO);

        assertEquals("TRANSFERENCIA_REALIZADA_COM_SUCESSO", responseDTO.status());
        assertEquals(new BigDecimal("300.00"), responseDTO.saldoOrigem());
        assertEquals(new BigDecimal("300.00"), responseDTO.saldoDestino());

    }
}
