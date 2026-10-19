package com.ddd.projetoContaBancaria.infra.persistence;


import com.ddd.projetoContaBancaria.domain.model.Conta;
import com.ddd.projetoContaBancaria.domain.model.ContaID;
import com.ddd.projetoContaBancaria.domain.repo.ContaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ContaRepositoryPort implements ContaRepository {

    private final SpringDataContaRepository springDataContaRepository;

    public ContaRepositoryPort(SpringDataContaRepository springDataContaRepository) {
        this.springDataContaRepository = springDataContaRepository;
    }

    @Override
    public Optional<Conta> findById(ContaID id) {
        return springDataContaRepository.findById(id.valor())
                .map(ContaMapper::toDomain);
    }

    @Override
    public void save(Conta conta) {
        ContaEntity entity = ContaMapper.toEntity(conta);
        springDataContaRepository.save(entity);
    }



}
