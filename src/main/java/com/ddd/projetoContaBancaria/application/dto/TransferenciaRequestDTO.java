package com.ddd.projetoContaBancaria.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferenciaRequestDTO(UUID contaOrigemId,
                                      UUID contaDestinoId,
                                      BigDecimal valor,
                                      String moeda) {
}
