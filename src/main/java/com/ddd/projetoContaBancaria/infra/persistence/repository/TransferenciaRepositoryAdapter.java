package com.ddd.projetoContaBancaria.infra.persistence.repository;


import com.ddd.projetoContaBancaria.domain.model.Transferencia;
import com.ddd.projetoContaBancaria.domain.repo.TransferenciaRepository;
import com.ddd.projetoContaBancaria.infra.persistence.entity.TransferenciaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TransferenciaRepositoryAdapter implements TransferenciaRepository {

    private final SpringDataTransferenciaRepository springDataTransferenciaRepository;

    public TransferenciaRepositoryAdapter(SpringDataTransferenciaRepository springDataTransferenciaRepository) {
        this.springDataTransferenciaRepository = springDataTransferenciaRepository;
    }

    @Override
    public void save(Transferencia transferencia){
        TransferenciaEntity entity = new TransferenciaEntity(
                transferencia.getId(),
                transferencia.getContaOrigemId().valor(),
                transferencia.getContaDestinoId().valor(),
                transferencia.getValorEnviado().quantia(),
                transferencia.getValorEnviado().moeda().name(),
                transferencia.getValorRecebido().quantia(),
                transferencia.getValorRecebido().moeda().name(),
                transferencia.getDataHora()
        );
        springDataTransferenciaRepository.save(entity);
    }
}
