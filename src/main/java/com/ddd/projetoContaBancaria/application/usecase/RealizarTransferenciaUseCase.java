package com.ddd.projetoContaBancaria.application.usecase;

import com.ddd.projetoContaBancaria.application.dto.TransferenciaRequestDTO;
import com.ddd.projetoContaBancaria.application.dto.TransferenciaResponseDTO;
import com.ddd.projetoContaBancaria.domain.model.Conta;
import com.ddd.projetoContaBancaria.domain.model.ContaID;
import com.ddd.projetoContaBancaria.domain.model.Dinheiro;
import com.ddd.projetoContaBancaria.domain.model.Moeda;
import com.ddd.projetoContaBancaria.domain.repo.ContaRepository;
import com.ddd.projetoContaBancaria.domain.service.TransferenciaService;

public class RealizarTransferenciaUseCase {

    private final ContaRepository contaRepository;
    private final TransferenciaService transferenciaService;

    public RealizarTransferenciaUseCase(ContaRepository contaRepository, TransferenciaService transferenciaService) {
        this.contaRepository = contaRepository;
        this.transferenciaService = transferenciaService;
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

        transferenciaService.transferir(origem, destino, valorTransferencia);

        contaRepository.save(origem);
        contaRepository.save(destino);

        return new TransferenciaResponseDTO(
                origem.getId().valor(),
                origem.getSaldo().quantia(),
                destino.getId().valor(),
                destino.getSaldo().quantia(),
                "TRANSFERENCIA_REALIZADA_COM_SUCESSO"
        );
    }
}
