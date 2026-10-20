# 🏦 Sistema de Transferências & Contas Bancárias (DDD + Clean Architecture)

Este projeto é uma API REST desenvolvida em Java e Spring Boot, aplicando os conceitos de **Domain-Driven Design (DDD)** e **Clean Architecture (Arquitetura Hexagonal)**.

O objetivo principal é isolar completamente o núcleo das regras de negócio de frameworks e detalhes de persistência.

---

## 🎯 Domínio e Regras de Negócio Implementadas

1. **Gestão de Saldo e Limite:** Cada conta possui saldo e limite de cheque especial opcional. Saques e débitos consideram a soma de `saldo + limiteChequeEspecial`.
2. **Transferências Idênticas:** Impedimento absoluto de transferências para a própria conta de origem.
3. **Validação de Valores:** Apenas valores estritamente maiores que zero são permitidos.
4. **Proteção Contra Inadimplência:** Operações falham se o valor solicitado ultrapassar o limite disponível (`saldo + limiteChequeEspecial`), impedindo saldo negativo além do limite.
5. **Câmbio de Moedas no Domínio:** Suporte a conversões automáticas entre moedas diferentes (BRL, USD e EUR), utilizando taxas de conversão calculadas pelo domínio.
6. **Prevenção de Fraude (Congelamento):** Contas marcadas como congeladas não podem enviar nem receber valores.
7. **Auditabilidade:** Todo evento de transferência gera um registro histórico imutável (`Transferencia`) gravado na base de dados.

---

## 🏗️ Arquitetura e Estrutura de Pastas

A aplicação segue a divisão rigorosa por camadas, onde a camada interna (`domain`) não possui dependências do Spring Boot ou de bibliotecas de terceiros.

```text
src/
└── main/
    └── java/com/ddd/projetoContaBancaria/
        ├── domain/                      # O CORAÇÃO: puro Java, zero frameworks
        │   ├── model/                   # Agregados, entidades e Value Objects
        │   │                             # (Conta, Dinheiro, Moeda, Transferencia)
        │   ├── service/                 # Domain Services
        │   │                             # (ServicoTransferencia, ServicoCambio)
        │   ├── repository/              # Ports / Interfaces
        │   │                             # (ContaRepository, TransferenciaRepository)
        │   └── exception/               # Exceções de domínio
        │                                 # (SaldoInsuficienteException, ContaCongeladaException)
        │
        ├── application/                 # CASOS DE USO: orquestração do fluxo
        │   ├── usecase/                 # RealizarTransferenciaUseCase
        │   └── dto/                     # DTOs de entrada e saída
        │                                     # (TransferenciaInput, TransferenciaOutput)
        │
        ├── infra/                       # ADAPTADORES E TECNOLOGIA
        │   ├── persistence/             # Adaptadores do Spring Data JPA e banco H2
        │   │   ├── entity/               # Mapeamento ORM
        │   │   │                             # (ContaEntity, TransferenciaEntity)
        │   │   ├── mapper/               # Conversão entre domínio e banco
        │   │   │                             # (ContaMapper)
        │   │   └── repository/           # Adaptadores dos repositórios
        │   │                                 # (ContaRepositoryAdapter)
        │   └── web/                     # Controllers REST e handler global de exceções
        │
        └── config/                      # INJEÇÃO DE DEPENDÊNCIAS
            └── BeansConfig.java         # Configuração dos beans de domínio no Spring
```

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+** — com uso de Records para Value Objects imutáveis.
- **Spring Boot 3** — Web, Data JPA e Validation.
- **H2 Database** — banco de dados em memória.
- **Lombok** — restrito às entidades e DTOs de infraestrutura.
- **JUnit 5 e Mockito** — testes unitários isolados do domínio.
- **SpringDoc OpenAPI (Swagger UI)** — documentação e testes dos endpoints.

---

## 🚀 Como Executar o Projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/vitorsonn/prjContaBancaria_DDD.git
cd prjContaBancaria_DDD
```

### 2. Compilar e executar

```bash
mvn spring-boot:run
```

### 3. Acessar a documentação interativa (Swagger)

Abra no navegador:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### 4. Acessar o console do banco de dados H2

Abra no navegador:

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

**Credenciais de acesso:**

| Configuração | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:bancodb` |
| Usuário | `sa` |
| Senha | Deixe em branco |

---

## 🧪 Rodando os Testes Unitários

Para validar as regras de negócio do domínio sem precisar iniciar a aplicação Spring Boot, execute:

```bash
mvn test
```
