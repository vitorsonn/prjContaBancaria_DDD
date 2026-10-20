package com.ddd.projetoContaBancaria.domain.repo;

import com.ddd.projetoContaBancaria.domain.model.Transferencia;

public interface TransferenciaRepository {
    void save(Transferencia transferencia);
}
