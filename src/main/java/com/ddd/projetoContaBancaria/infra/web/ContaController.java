package com.ddd.projetoContaBancaria.infra.web;


import com.ddd.projetoContaBancaria.application.dto.TransferenciaRequestDTO;
import com.ddd.projetoContaBancaria.application.dto.TransferenciaResponseDTO;
import com.ddd.projetoContaBancaria.application.usecase.RealizarTransferenciaUseCase;
import com.ddd.projetoContaBancaria.domain.model.Conta;
import com.ddd.projetoContaBancaria.domain.model.Dinheiro;
import com.ddd.projetoContaBancaria.domain.model.Moeda;
import com.ddd.projetoContaBancaria.domain.repo.ContaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/contas")
@Tag(name = "Contas Bancárias", description = "Endpoints para gerenciamento de contas e transferências")
public class ContaController {

    private final RealizarTransferenciaUseCase useCase;
    private final ContaRepository contaRepository;

    public ContaController(RealizarTransferenciaUseCase useCase, ContaRepository contaRepository) {
        this.useCase = useCase;
        this.contaRepository = contaRepository;
    }



    @PostMapping("/criar")
    @Operation(summary = "Cria uma nova conta bancária")
    public ResponseEntity<Map<String, Object>> criarConta(@RequestParam BigDecimal saldoInicial,
                                                          @RequestParam(defaultValue = "0") BigDecimal limiteChequeEspecial,
                                                          @RequestParam(defaultValue = "BRL") String moeda){

        Moeda m = Moeda.valueOf(moeda);
        Conta conta = Conta.criarConta(new Dinheiro(saldoInicial, m), new Dinheiro(saldoInicial, m));
        contaRepository.save(conta);

        return ResponseEntity.ok(Map.of(
                "id", conta.getId().valor(),
                "saldo", conta.getSaldo().quantia(),
                "limiteChequeEspecial", conta.getLimiteChequeEspecial().quantia(),
                "moeda", conta.getSaldo().moeda().name()
        ));
    }

    @PostMapping("/transferir")
    @Operation(summary = "Realiza transferência entre duas contas")
    public ResponseEntity<TransferenciaResponseDTO> transferir(@RequestBody TransferenciaRequestDTO requestDTO) {
        TransferenciaResponseDTO responseDTO = useCase.executar(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }








}
