package com.ddd.projetoContaBancaria.application.usecase;

import com.ddd.projetoContaBancaria.application.dto.TransferenciaRequestDTO;
import com.ddd.projetoContaBancaria.application.dto.TransferenciaResponseDTO;
import com.ddd.projetoContaBancaria.domain.model.*;
import com.ddd.projetoContaBancaria.domain.repo.ContaRepository;
import com.ddd.projetoContaBancaria.domain.repo.TransferenciaRepository;
import com.ddd.projetoContaBancaria.domain.service.TransferenciaService;

public class RealizarTransferenciaUseCase {

    private final ContaRepository contaRepository;
    private final TransferenciaService transferenciaService;
    private final TransferenciaRepository transferenciaRepository;

    public RealizarTransferenciaUseCase(ContaRepository contaRepository, TransferenciaService transferenciaService, TransferenciaRepository transferenciaRepository ) {
        this.contaRepository = contaRepository;
        this.transferenciaService = transferenciaService;
        this.transferenciaRepository = transferenciaRepository;
    }

    public TransferenciaResponseDTO executar(TransferenciaRequestDTO requestDTO){
        ContaID contaOrigemId = new ContaID(requestDTO.contaOrigemId());
        ContaID contaDestinoId = new ContaID(requestDTO.contaDestinoId());

        Conta origem = contaRepository.findById(contaOrigemId)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada"));

        Conta destino = contaRepository.findById(contaDestinoId)
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada"));

        Moeda moedaOperacao = Moeda.valueOf(requestDTO.moeda());
        Dinheiro valorTransferencia = new Dinheiro(requestDTO.valor(), moedaOperacao);

        Dinheiro saldoDestinoAntes = destino.getSaldo();


        transferenciaService.transferir(origem, destino, valorTransferencia);

        Dinheiro valorRecebido = destino.getSaldo().subtrair(saldoDestinoAntes);
        contaRepository.save(origem);
        contaRepository.save(destino);




        Transferencia historico = Transferencia.criar(contaOrigemId, contaDestinoId, valorTransferencia, valorRecebido);
        transferenciaRepository.save(historico);

        return new TransferenciaResponseDTO(
                origem.getId().valor(),
                origem.getSaldo().quantia(),
                destino.getId().valor(),
                destino.getSaldo().quantia(),
                "TRANSFERENCIA_REALIZADA_COM_SUCESSO"
        );
    }
}
