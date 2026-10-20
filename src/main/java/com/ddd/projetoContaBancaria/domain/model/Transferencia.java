package com.ddd.projetoContaBancaria.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transferencia {

    private final UUID id;
    private final ContaID contaOrigemId;
    private final ContaID contaDestinoId;
    private final Dinheiro valorEnviado;
    private final Dinheiro valorRecebido;
    private final LocalDateTime dataHora;

    public Transferencia(UUID id, ContaID contaOrigemId, ContaID contaDestinoId, Dinheiro valorEnviado, Dinheiro valorRecebido, LocalDateTime dataHora) {
        this.id = Objects.requireNonNull(id, "ID não pode ser nulo");
        this.contaOrigemId = Objects.requireNonNull(contaOrigemId, "Conta de origem não pode ser nula");
        this.contaDestinoId = Objects.requireNonNull(contaDestinoId, "Conta de destino não pode ser nula");
        this.valorEnviado = Objects.requireNonNull(valorEnviado, "Valor enviado não pode ser nulo");
        this.valorRecebido = Objects.requireNonNull(valorRecebido, "Valor recebido não pode ser nulo");
        this.dataHora = Objects.requireNonNull(dataHora, "Data e hora não podem ser nulas");
    }

    public static Transferencia criar(ContaID origem, ContaID destino, Dinheiro valorEnviado, Dinheiro valorRecebido) {
        return new Transferencia(
                UUID.randomUUID(),
                origem,
                destino,
                valorEnviado,
                valorRecebido,
                LocalDateTime.now()
        );
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Dinheiro getValorRecebido() {
        return valorRecebido;
    }

    public ContaID getContaDestinoId() {
        return contaDestinoId;
    }

    public ContaID getContaOrigemId() {
        return contaOrigemId;
    }

    public Dinheiro getValorEnviado() {
        return valorEnviado;
    }

    public UUID getId() {
        return id;
    }
}
