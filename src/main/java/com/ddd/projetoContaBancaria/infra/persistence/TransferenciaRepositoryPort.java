package com.ddd.projetoContaBancaria.infra.persistence;


import com.ddd.projetoContaBancaria.domain.model.Transferencia;
import com.ddd.projetoContaBancaria.domain.repo.TransferenciaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TransferenciaRepositoryPort implements TransferenciaRepository {

    private final SpringDataTransferenciaRepository springDataTransferenciaRepository;

    public TransferenciaRepositoryPort(SpringDataTransferenciaRepository springDataTransferenciaRepository) {
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
