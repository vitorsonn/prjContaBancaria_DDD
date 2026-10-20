package com.ddd.projetoContaBancaria.config;


import com.ddd.projetoContaBancaria.application.usecase.RealizarTransferenciaUseCase;
import com.ddd.projetoContaBancaria.domain.model.CambioService;
import com.ddd.projetoContaBancaria.domain.repo.ContaRepository;
import com.ddd.projetoContaBancaria.domain.repo.TransferenciaRepository;
import com.ddd.projetoContaBancaria.domain.service.TransferenciaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public CambioService cambioService() {
        return new CambioService();
    }

    @Bean
    public TransferenciaService transferenciaService(CambioService cambioService) {
        return new TransferenciaService(cambioService);
    }

    @Bean
    public RealizarTransferenciaUseCase realizarTransferenciaUseCase(
            ContaRepository contaRepository,
            TransferenciaRepository transferenciaRepository,
            TransferenciaService servicoTransferencia) {
        return new RealizarTransferenciaUseCase(contaRepository,servicoTransferencia, transferenciaRepository);
    }
}
