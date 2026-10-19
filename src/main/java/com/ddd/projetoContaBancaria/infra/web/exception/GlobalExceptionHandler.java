package com.ddd.projetoContaBancaria.infra.web.exception;


import com.ddd.projetoContaBancaria.domain.exception.ContaCongeladaException;
import com.ddd.projetoContaBancaria.domain.exception.SaldoInsuficienteException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ErroResponse> tratarSaldoInsuficiente(SaldoInsuficienteException ex) {
        ErroResponse erro = ErroResponse.de(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Saldo Insuficiente",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

    @ExceptionHandler(ContaCongeladaException.class)
    public ResponseEntity<ErroResponse> tratarContaCongelada(ContaCongeladaException ex) {
        ErroResponse erro = ErroResponse.de(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Conta Congelada",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponse> tratarIllegalArgument(IllegalArgumentException ex) {
        ErroResponse erro = ErroResponse.de(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição Inválida",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarValidacao(MethodArgumentNotValidException ex) {
        String mensagens = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErroResponse erro = ErroResponse.de(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                mensagens
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
