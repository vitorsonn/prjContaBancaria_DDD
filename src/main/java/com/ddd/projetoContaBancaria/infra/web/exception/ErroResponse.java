package com.ddd.projetoContaBancaria.infra.web.exception;

import java.time.LocalDateTime;

public record ErroResponse(int status,
                           String erro,
                           String mensagem,
                           LocalDateTime timestamp) {
    public static ErroResponse de(int status, String erro, String mensagem) {
        return new ErroResponse(status, erro, mensagem, LocalDateTime.now());

    }
}
