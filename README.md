# Aamuserapi — API do Projeto Alto Alegre Mercado

API REST para gerenciamento de usuários, lojas, produtos e pagamentos do sistema Alto Alegre Mercado.

---

## Stack

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.0 |
| Maven | 3.13+ (wrapper incluso) |
| Banco principal | MySQL 8.0 |
| Banco de testes | H2 (em memória) |
| ORM | JPA / Hibernate 6 |
| Segurança | Spring Security + BCrypt |
| Pagamentos | Stripe SDK |
| Relatórios | JasperReports |
| Testes | JUnit 5, Mockito, MockMvc |

---

## Estrutura do projeto

```
src/main/java/br/com/altoalegremercado/aamuserapi/
├── config/
│   └── SecurityConfig.java            PasswordEncoder (BCrypt)
├── controller/
│   ├── UserController.java            /users
│   ├── StoreController.java           /api/stores
│   ├── PaymentController.java         /api/payments
│   ├── ReportController.java          /api/reports
│   ├── GlobalExceptionHandler.java    Tratamento global de erros
│   └── dto/
│       ├── UserDTO.java
│       ├── StoreDTO.java
│       ├── ProductDTO.java
│       └── PaymentDTO.java
├── service/
│   ├── UserService / UserServiceImpl
│   ├── StoreService / StoreServiceImpl
│   ├── ProductService / ProductServiceImpl
│   ├── PaymentService / PaymentServiceImpl
│   └── ReportService / ReportServiceImpl
├── repository/
│   ├── UserRepository.java
│   ├── StoreRepository.java
│   ├── ProductRepository.java
│   └── PaymentRepository.java
├── domain/model/
│   ├── User.java, Person.java, Role.java, UserRole.java
│   ├── Address.java
│   ├── Store.java, Product.java
│   ├── Payment.java, PaymentStatus.java
└── validation/
    ├── CPF.java, CPFValidator.java
    ├── CNPJ.java, CNPJValidator.java
```

```
src/test/
└── java/br/com/altoalegremercado/aamuserapi/
    ├── controller/
    │   ├── UserControllerTest.java
    │   ├── StoreControllerTest.java
    │   ├── PaymentControllerTest.java
    │   ├── ReportControllerTest.java
    │   └── GlobalExceptionHandlerTest.java
    ├── repository/
    │   ├── UserRepositoryTest.java
    │   ├── StoreRepositoryTest.java
    │   ├── ProductRepositoryTest.java
    │   └── PaymentRepositoryTest.java
    └── dto/
        └── UserDTOTest.java
```

---

## Pré-requisitos

- **Java 21** (JDK instalado e configurado no `JAVA_HOME`)
- **Maven** (ou use o wrapper `./mvnw` incluso)
- **MySQL 8.0** rodando (apenas para execução da aplicação; testes usam H2)

---

## Como rodar a aplicação

### Sem Docker (local)

```bash
# 1. Configure o banco MySQL
# Crie um database chamado "aam" (ou altere em application.properties)
# Edite src/main/resources/application.properties com suas credenciais

# 2. Compile e rode
./mvnw clean install -DskipTests
./mvnw spring-boot:run

# A API estará disponível em http://localhost:8080
```

### Com Docker

```bash
# Build e iniciar (MySQL + aplicação)
docker compose up --build

# Para rodar em background
docker compose up --build -d

# Para parar
docker compose down

# Para parar e remover volumes (dados do banco)
docker compose down -v
```

A API estará em `http://localhost:8080`.

---

## Testes

### Rodar todos os testes

```bash
./mvnw clean test
```

### Rodar uma classe específica

```bash
./mvnw test -Dtest=UserControllerTest
./mvnw test -Dtest=StoreControllerTest
./mvnw test -Dtest=PaymentControllerTest
```

### Sobre a configuração de testes

Os testes usam duas camadas de configuração:

1. **`src/test/resources/application.properties`** — Desabilita o Spring Security em todos os contextos de teste (`@WebMvcTest`, `@SpringBootTest`, etc.) para evitar bloqueios de autenticação durante os testes.
2. **`src/test/resources/application-test.properties`** — Configura o banco H2 em memória e o Dialect do Hibernate para os testes que usam o perfil `test`.

**Total: 44 testes** (unitários e de integração)

| Grupo | Quantidade | Descrição |
|---|---|---|
| Controller | 28 | Testes com MockMvc e mocks dos serviços |
| Repository | 13 | Testes com JPA/H2 |
| DTO | 2 | Validação de campos |
| Application | 1 | Contexto da aplicação |

---

## Endpoints

### Usuários (`/users`)

| Método | Path | Descrição |
|---|---|---|
| GET | `/users` | Listar todos |
| GET | `/users/{name}` | Buscar por nome |
| GET | `/users/cpf/{cpf}` | Buscar por CPF |
| GET | `/users/cnpj/{cnpj}` | Buscar por CNPJ |
| GET | `/users/role/{role}` | Buscar por role (ADMIN, CUSTOMER, SHOP) |
| POST | `/users` | Criar usuário |
| PUT | `/users/{id}` | Atualizar |
| DELETE | `/users/{id}` | Remover |

### Lojas (`/api/stores`)

| Método | Path | Descrição |
|---|---|---|
| GET | `/api/stores` | Listar todas |
| GET | `/api/stores/{id}` | Buscar por ID |
| POST | `/api/stores` | Criar loja |
| PUT | `/api/stores/{id}` | Atualizar |
| DELETE | `/api/stores/{id}` | Remover |
| GET | `/api/stores/{id}/products` | Listar produtos da loja |
| POST | `/api/stores/{id}/products` | Criar produto na loja |

### Pagamentos (`/api/payments`)

| Método | Path | Descrição |
|---|---|---|
| GET | `/api/payments` | Listar todos |
| GET | `/api/payments/{id}` | Buscar por ID |
| POST | `/api/payments` | Processar pagamento |
| PUT | `/api/payments/{id}/cancel` | Cancelar pagamento |
| GET | `/api/payments/store/{storeId}` | Por loja |
| GET | `/api/payments/status/{status}` | Por status |

### Relatórios (`/api/reports`)

| Método | Path | Descrição |
|---|---|---|
| POST | `/api/reports/sales/{storeId}` | Relatório de vendas |
| POST | `/api/reports/payments/{storeId}` | Relatório de pagamentos |

---

## Observações técnicas

### Segurança

- As senhas são armazenadas com **hash BCrypt** via `PasswordEncoder`.
- O Spring Security está presente como dependência, mas durante os testes ele é **desabilitado** via `spring.autoconfigure.exclude` no `application.properties` de teste.

### Compilação

- O projeto exige **Java 21**.
- O `pom.xml` já está configurado com `maven-compiler-plugin` versão `3.13.0` e `source`/`target`/`release` apontando para `21`.
- Use o wrapper `./mvnw` incluso — ele baixa a versão correta do Maven automaticamente.

### Banco de dados

- **Produção/desenvolvimento:** MySQL 8.0 (configurado em `src/main/resources/application.properties`).
- **Testes:** H2 em memória (configurado em `src/test/resources/application-test.properties`).

---
