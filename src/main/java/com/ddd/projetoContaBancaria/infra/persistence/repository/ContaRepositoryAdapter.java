package com.ddd.projetoContaBancaria.infra.persistence.repository;


import com.ddd.projetoContaBancaria.domain.model.Conta;
import com.ddd.projetoContaBancaria.domain.model.ContaID;
import com.ddd.projetoContaBancaria.domain.repo.ContaRepository;
import com.ddd.projetoContaBancaria.infra.persistence.entity.ContaEntity;
import com.ddd.projetoContaBancaria.infra.persistence.mapper.ContaMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ContaRepositoryAdapter implements ContaRepository {

    private final SpringDataContaRepository springDataContaRepository;

    public ContaRepositoryAdapter(SpringDataContaRepository springDataContaRepository) {
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
