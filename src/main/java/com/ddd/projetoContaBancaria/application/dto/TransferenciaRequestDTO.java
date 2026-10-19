package com.ddd.projetoContaBancaria.application.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferenciaRequestDTO(
        @NotNull(message = "A conta de origem é obrigatória")
        UUID contaOrigemId,
        @NotNull(message = "A conta de destino é obrigatória")
        UUID contaDestinoId,
        @NotNull(message = "O valor da transferência é obrigatório")
        BigDecimal valor,
        @NotNull(message = "A moeda da transferência é obrigatória")
        String moeda) {
}
