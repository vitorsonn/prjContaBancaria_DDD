package com.ddd.projetoContaBancaria.infra.persistence.mapper;

import com.ddd.projetoContaBancaria.domain.model.Conta;
import com.ddd.projetoContaBancaria.domain.model.ContaID;
import com.ddd.projetoContaBancaria.domain.model.Dinheiro;
import com.ddd.projetoContaBancaria.domain.model.Moeda;
import com.ddd.projetoContaBancaria.infra.persistence.entity.ContaEntity;

public class ContaMapper {

    public static Conta toDomain(ContaEntity entity) {
        Dinheiro saldo = new Dinheiro(entity.getSaldo(), Moeda.valueOf(entity.getMoedaSaldo()));
        Dinheiro limite = new Dinheiro(entity.getLimiteChequeEspecial(), Moeda.valueOf(entity.getMoedaLimite()));
        ContaID id = new ContaID(entity.getId());

        Conta conta = new Conta(id, saldo, limite);
        if (entity.isCongelada()) {
            conta.congelar();
        }
        return conta;
    }

    public static ContaEntity toEntity(Conta conta) {
        return new ContaEntity(
                conta.getId().valor(),
                conta.getSaldo().quantia(),
                conta.getSaldo().moeda().name(),
                conta.getLimiteChequeEspecial().quantia(),
                conta.getLimiteChequeEspecial().moeda().name(),
                conta.isCongelada()
        );
    }


}