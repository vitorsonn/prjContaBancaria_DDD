package com.ddd.projetoContaBancaria.infra.persistence.repository;

import com.ddd.projetoContaBancaria.infra.persistence.entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataContaRepository extends JpaRepository<ContaEntity, UUID> {
}
