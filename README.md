# Super ERP

Super ERP e uma base JHipster 9 para um ERP multi-tenant com foco em cadastro, estoque e vendas. O dominio foi modelado a partir do arquivo `erp-core.jdl` para organizar os processos centrais de uma operacao empresarial em um unico lugar.

## Visao geral

O sistema foi pensado para centralizar operacoes de uma empresa com segregacao por tenant, mantendo separacao de dados, regras consistentes de negocio e uma base pronta para evoluir por modulo. Em termos praticos, ele atende cenarios como:

- cadastro e relacionamento de empresas, pessoas e parceiros comerciais;
- organizacao territorial para enderecamento e regionalizacao;
- catalogo de itens para operacoes de compra, estoque e venda;
- movimentacao e rastreabilidade de estoque em diferentes armazens;
- acompanhamento do ciclo comercial, da abertura ao faturamento.

## Sobre o projeto

O projeto combina backend Spring Boot e frontend Angular com a proposta de entregar uma base tecnica consistente para um ERP modular. A arquitetura privilegia produtividade de desenvolvimento sem abrir mao de uma estrutura que suporte crescimento gradual.

Entre os pontos mais importantes do projeto estao:

- separacao clara entre dominio, persistencia, API e interface;
- suporte a evolucao incremental do negocio por meio de geracao JHipster e padroes consolidados do ecossistema Spring;
- uso de multi-tenancy no nivel de modelagem para preparar o sistema para operacao com diferentes clientes ou unidades de negocio;
- apoio a rotinas de seguranca, auditoria, integracao e automacao de build;
- convencoes que facilitam manutencao, testes e entrega continua.

Na pratica, o repositório funciona como um ponto de partida para um ERP que pode crescer por etapas: primeiro os cadastros e a base estrutural, depois as rotinas operacionais, e por fim as camadas de integracao, analise e automacao.

## Stack

- Backend: Spring Boot 3 com Java 21
- Frontend: Angular 21
- Persistencia: JPA, Hibernate e Liquibase
- Autenticacao e seguranca: Spring Security e OAuth2 Resource Server
- Documentacao de API: OpenAPI
- Build: Gradle e npm scripts do JHipster

## Estrutura funcional

O dominio foi desenhado para cobrir a cadeia essencial de um ERP:

- base cadastral para identidade, localizacao e relacionamento comercial;
- gestao de estoque com entrada, saida, ajuste e transferencia;
- suporte a vendas com controle de status, valores e composicao de itens;
- rastreabilidade entre armazem, produto, materia-prima e movimentacao;
- suporte a cenarios de pessoa fisica e juridica dentro do mesmo fluxo de negocio.

Essa composicao reduz duplicacao de informacao e permite que os processos conversem entre si sem perder a separacao entre os modulos. O resultado e uma base mais apropriada para operacoes repetitivas, relatórios gerenciais e evolucao para novas regras fiscais, financeiras ou logísticas.

## Relacionamentos principais

- paises se conectam a estados e cidades para suportar enderecamento;
- o contexto do tenant serve como base de segregacao para os cadastros e operacoes;
- fornecedores, clientes, pessoas e empresas compartilham informacoes de localizacao e contato quando necessario;
- produtos, materias-primas, armazens e movimentos de estoque formam o nucleo operacional do inventario;
- vendas e itens de venda consolidam o fluxo comercial e facilitam integracoes futuras com faturamento e contas a receber.

## Requisitos

- Java 21
- Node.js 24.14.0 ou superior
- Docker e Docker Compose para os servicos auxiliares
- Gradle Wrapper disponivel no repositorio

## Como executar

### Backend

```bash
npm run backend:start
```

Ou

```bash
./gradlew -x webapp -x webapp_test
```

### Frontend em modo desenvolvimento

```bash
npm run webapp:dev
```

### Backend e frontend juntos em desenvolvimento

```bash
npm run watch
```

### Banco e servicos auxiliares

```bash
npm run docker:db:up
npm run services:up
```

### Aplicacao em ambiente local com Docker

```bash
npm run app:up
```

## Scripts uteis

- `npm run backend:start` inicia o backend sem o frontend
- `npm run webapp:build:prod` gera o build de producao do frontend
- `npm run build` executa o build da interface
- `npm run test` roda os testes do frontend
- `npm run backend:unit:test` roda testes e integracoes do backend
- `npm run lint` executa a verificacao de estilo

## Estrutura do projeto

- `src/main/java`: codigo Java da aplicacao
- `src/main/webapp`: frontend Angular
- `src/main/resources`: configuracoes, templates, i18n e recursos estaticos
- `src/main/docker`: arquivos de compose e servicos de suporte
- `src/test/java`: testes automatizados
