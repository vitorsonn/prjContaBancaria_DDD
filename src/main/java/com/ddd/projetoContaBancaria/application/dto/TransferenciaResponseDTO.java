package com.ddd.projetoContaBancaria.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferenciaResponseDTO(UUID contaOrigemId,
                                       BigDecimal saldoOrigem,
                                       UUID contaDestinoId,
                                       BigDecimal saldoDestino,
                                       String status) {
}
