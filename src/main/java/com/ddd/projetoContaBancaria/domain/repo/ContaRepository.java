package com.ddd.projetoContaBancaria.domain.repo;

import com.ddd.projetoContaBancaria.domain.model.Conta;
import com.ddd.projetoContaBancaria.domain.model.ContaID;

import java.util.Optional;

public interface ContaRepository {

    Optional<Conta> findById(ContaID id);
    void save(Conta conta);
}
