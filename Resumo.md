# Arquitetura Hexagonal - Resumo Simplificado

Este README apresenta um resumo simplificado de cada capítulo do livro "Get Your Hands Dirty on Clean Architecture", focando nos conceitos essenciais da Arquitetura Hexagonal.

## Capítulo 1: O que há de errado com a arquitetura em camadas?

**Problemas da arquitetura tradicional em camadas:**
- Acoplamento forte com o banco de dados
- Dependências apontando na direção errada (de cima para baixo)
- Vazamento de conceitos entre camadas
- Modelo de domínio anêmico (sem lógica de negócio)
- Dificuldade para testar a lógica de negócio isoladamente

**Resultado:**
- Código difícil de manter e evoluir
- Regras de negócio espalhadas por toda a aplicação
- Testes complexos e frágeis

## Capítulo 2: Invertendo Dependências

**Princípio da Inversão de Dependências:**
- Módulos de alto nível não devem depender de módulos de baixo nível
- Ambos devem depender de abstrações
- Abstrações não devem depender de detalhes
- Detalhes devem depender de abstrações

**Aplicação na Arquitetura Hexagonal:**
- Dependências apontam para o centro (domínio)
- Interfaces (portas) definem como o mundo externo interage com a aplicação
- Implementações (adaptadores) conectam o mundo externo à aplicação
- O domínio não conhece detalhes de infraestrutura

## Capítulo 3: Organizando o Código

**Opções de organização:**
- Por camadas (tradicional): controladores, serviços, repositórios
- Por componentes/features: agrupando por funcionalidade
- Por arquitetura hexagonal: domínio, aplicação, adaptadores

**Estrutura recomendada:**
```
com.example.application
├── domain
│   └── model
├── application
│   ├── port
│   │   ├── in
│   │   └── out
│   └── service
└── adapter
    ├── in
    │   └── web
    └── out
        └── persistence
```

**Benefícios:**
- Código mais organizado e fácil de navegar
- Limites claros entre componentes
- Facilita a aplicação dos princípios da arquitetura limpa

## Capítulo 4: Implementando um Caso de Uso

**Componentes principais:**
- **Porta de entrada**: interface que define o caso de uso
- **Comando**: objeto que encapsula os parâmetros do caso de uso
- **Serviço de aplicação**: implementa o caso de uso
- **Portas de saída**: interfaces para acessar recursos externos

**Exemplo simplificado:**
```java
// Porta de entrada
interface SendMoneyUseCase {
    boolean sendMoney(SendMoneyCommand command);
}

// Comando
class SendMoneyCommand {
    private final AccountId sourceAccountId;
    private final AccountId targetAccountId;
    private final Money money;
}

// Serviço de aplicação
class SendMoneyService implements SendMoneyUseCase {
    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountStatePort updateAccountStatePort;
    
    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        // Implementação do caso de uso
    }
}

// Portas de saída
interface LoadAccountPort {
    Account loadAccount(AccountId accountId);
}

interface UpdateAccountStatePort {
    void updateActivities(Account account);
}
```

## Capítulo 5: Implementando um Adaptador Web

**Características:**
- Implementa as portas de entrada
- Traduz requisições HTTP para comandos de caso de uso
- Converte respostas de caso de uso para respostas HTTP
- Não contém lógica de negócio

**Exemplo simplificado:**
```java
@RestController
class AccountController {
    private final SendMoneyUseCase sendMoneyUseCase;
    
    @PostMapping("/accounts/send/{sourceId}/{targetId}/{amount}")
    ResponseEntity<Void> sendMoney(
        @PathVariable Long sourceId,
        @PathVariable Long targetId,
        @PathVariable Long amount) {
        
        SendMoneyCommand command = new SendMoneyCommand(
            new AccountId(sourceId),
            new AccountId(targetId),
            Money.of(amount));
            
        boolean success = sendMoneyUseCase.sendMoney(command);
        
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
```

## Capítulo 6: Implementando um Adaptador de Persistência

**Características:**
- Implementa as portas de saída
- Traduz entidades de domínio para entidades de persistência
- Encapsula detalhes do banco de dados
- Não contém lógica de negócio

**Exemplo simplificado:**
```java
@Repository
class AccountPersistenceAdapter implements 
    LoadAccountPort, UpdateAccountStatePort {
    
    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;
    
    @Override
    public Account loadAccount(AccountId accountId) {
        AccountJpaEntity account = 
            accountRepository.findById(accountId.getValue())
                .orElseThrow();
                
        List<ActivityJpaEntity> activities =
            activityRepository.findByOwnerSince(
                accountId.getValue());
                
        return accountMapper.mapToDomain(account, activities);
    }
    
    @Override
    public void updateActivities(Account account) {
        // Implementação
    }
}
```

## Capítulo 7: Testando Elementos da Arquitetura

**Tipos de testes:**
- **Testes unitários**: para entidades de domínio e casos de uso
- **Testes de integração**: para adaptadores
- **Testes de sistema**: para fluxos completos

**Benefícios:**
- Testes mais simples e focados
- Menos dependências de infraestrutura
- Maior cobertura de código
- Feedback mais rápido

**Exemplo de teste unitário para caso de uso:**
```java
@Test
void sendMoneySucceeds() {
    // given
    Account sourceAccount = givenSourceAccount();
    Account targetAccount = givenTargetAccount();
    
    given(loadAccountPort.loadAccount(eq(sourceAccountId), any()))
        .willReturn(sourceAccount);
    given(loadAccountPort.loadAccount(eq(targetAccountId), any()))
        .willReturn(targetAccount);
    
    // when
    boolean success = sendMoneyService.sendMoney(
        new SendMoneyCommand(
            sourceAccountId,
            targetAccountId,
            Money.of(500L)));
    
    // then
    assertThat(success).isTrue();
    verify(updateAccountStatePort).updateActivities(sourceAccount);
    verify(updateAccountStatePort).updateActivities(targetAccount);
}
```

## Capítulo 8: Mapeamento entre Limites

**Estratégias de mapeamento:**
- **Mapeamento manual**: métodos de conversão
- **Mapeamento com biblioteca**: MapStruct, ModelMapper
- **Mapeamento com assemblers**: classes específicas

**Recomendações:**
- Mantenha o mapeamento simples e explícito
- Evite vazamento de conceitos entre camadas
- Use DTOs específicos para cada caso de uso
- Considere o custo de manutenção vs. automação

## Capítulo 9: Montando a Aplicação

**Abordagens:**
- **Configuração manual**: criar e conectar componentes manualmente
- **Injeção de dependências**: usar um framework como Spring

**Exemplo com Spring:**
```java
@Configuration
class BeanConfiguration {
    @Bean
    SendMoneyUseCase sendMoneyUseCase(
        LoadAccountPort loadAccountPort,
        AccountLock accountLock,
        UpdateAccountStatePort updateAccountStatePort) {
        
        return new SendMoneyService(
            loadAccountPort,
            accountLock,
            updateAccountStatePort);
    }
}
```

## Capítulo 10: Aplicando Limites Arquiteturais

**Técnicas:**
- **Módulos Java**: para impor limites em tempo de compilação
- **ArchUnit**: para verificar a arquitetura em tempo de teste
- **Pacotes**: para organizar o código conforme a arquitetura

**Exemplo com ArchUnit:**
```java
@Test
void domainMustNotDependOnOtherPackages() {
    noClasses()
        .that()
        .resideInAPackage("..domain..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage("..application..", "..adapter..")
        .check(classes);
}
```

## Capítulo 11: Tomando Atalhos Conscientemente

**Atalhos possíveis:**
- Usar entidades de domínio como entidades de persistência
- Usar DTOs como entidades de domínio
- Combinar camadas em aplicações simples

**Considerações:**
- Avalie o custo vs. benefício de cada atalho
- Documente as decisões e seus motivos
- Esteja preparado para refatorar quando necessário
- Mantenha a consciência dos princípios arquiteturais

## Capítulo 12: Decidindo sobre um Estilo de Arquitetura

**Estilos de arquitetura limpa:**
- **Arquitetura Hexagonal (Ports and Adapters)**
- **Arquitetura Limpa (Clean Architecture)**
- **Arquitetura Cebola (Onion Architecture)**

**Princípios comuns:**
- Separação de preocupações
- Inversão de dependências
- Domínio no centro
- Independência de frameworks
- Testabilidade

**Como escolher:**
- Considere a complexidade do domínio
- Avalie a experiência da equipe
- Pense no ciclo de vida do projeto
- Adapte ao contexto específico

## Conclusão

A Arquitetura Hexagonal oferece uma abordagem estruturada para desenvolver aplicações com foco no domínio, facilitando testes, manutenção e evolução ao longo do tempo. Ao separar claramente o núcleo da aplicação das preocupações técnicas, ela permite que a lógica de negócios seja desenvolvida e testada independentemente da infraestrutura.

Os principais benefícios incluem:
- Código mais manutenível e testável
- Domínio de negócio claramente definido
- Facilidade para trocar componentes externos
- Evolução independente das diferentes partes do sistema

Implementar a Arquitetura Hexagonal requer disciplina e um bom entendimento dos princípios subjacentes, mas os benefícios em termos de manutenibilidade e testabilidade geralmente superam o custo inicial de implementação.
