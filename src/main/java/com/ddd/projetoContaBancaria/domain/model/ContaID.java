package com.ddd.projetoContaBancaria.domain.model;

import java.util.Objects;
import java.util.UUID;

public record ContaID(UUID valor) {
    public ContaID{
        Objects.requireNonNull(valor, "O valor do ID da conta não pode ser nulo");
    }


    public static ContaID gerarNovoID() {
        return new ContaID(UUID.randomUUID());
    }

    public static ContaID converterString(String uuidAsString){
        return new ContaID(UUID.fromString(uuidAsString));
    }
}
