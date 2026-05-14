# Aamuserapi - API do Projeto Alto Alegre Mercado

## Descrição
API REST para gerenciamento de usuários no sistema Alto Alegre Mercado, utilizando Spring Boot, JPA e MySQL.

## Estrutura do Projeto
- **Model**: `User`, `Role`, `Person`, `Address`
- **Service**: `UserServiceImpl`
- **Repository**: `UserRepository`
- **Controller**: `UserController`
- **Entrada**: `AamuserapiApplication`

## Dependências
- Spring Boot 2.4.2
- Spring Web, Data JPA, AMQP
- MySQL Connector
- Testes: JUnit 4, Spring Boot Test
- Jackson (versões comentadas)

## Configuração
1. Altere `application.properties` com dados do banco
2. Execute `mvn clean install`
3. Rode `mvn spring-boot:run`

## Funcionalidades
- CRUD de usuários
- Integração com RabbitMQ (dependência ativa)
- TODO: Implementar segurança (dependência comentada)

## Testes
- Executar: `mvn test`
- Testes incluem JPA, RabbitMQ e segurança (comentados)

## Observações
- Atualizar Spring Boot para versão mais recente
- Ativar dependência Spring Security
- Implementar documentação REST
