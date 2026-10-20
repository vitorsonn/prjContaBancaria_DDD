package com.ddd.projetoContaBancaria.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTransferenciaRepository extends JpaRepository<TransferenciaEntity, UUID> {
}
