# Aamuserapi - API do Projeto Alto Alegre Mercado

API REST para gerenciamento de usuários, lojas, produtos e pagamentos do sistema Alto Alegre Mercado.

## Stack

- **Java 21** + **Spring Boot 3.4.0**
- **MySQL 8.0** (JPA / Hibernate 6)
- **Maven**
- Testes: JUnit 5, Mockito, MockMvc, H2 (testes de repositório)
- Pagamentos: Stripe SDK
- Relatórios: JasperReports

## Estrutura

```
src/main/java/br/com/altoalegremercado/aamuserapi/
├── controller/
│   ├── UserController.java          /users
│   ├── StoreController.java         /api/stores
│   ├── PaymentController.java       /api/payments
│   ├── ReportController.java        /api/reports
│   ├── GlobalExceptionHandler.java
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

## Como rodar

### Sem Docker (local)

Pré-requisitos: Java 21, Maven, MySQL 8.0 rodando.

```bash
# 1. Configurar o banco MySQL
# Crie um database chamado "aam" ou use o existente
# Edite src/main/resources/application.properties se necessário

# 2. Compilar e rodar
./mvnw clean install -DskipTests
./mvnw spring-boot:run

# A API estará em http://localhost:8080
```

### Com Docker

Pré-requisitos: Docker e Docker Compose.

```bash
# 1. Build e iniciar (MySQL + aplicação)
docker compose up --build

# 2. Para rodar em background
docker compose up --build -d

# 3. Para parar
docker compose down

# 4. Para parar e remover volumes (dados do banco)
docker compose down -v
```

A API estará em `http://localhost:8080`.

## Endpoints

### Usuários (`/users`)
| Método | Path | Descrição |
|--------|------|-----------|
| GET | `/users` | Listar todos |
| GET | `/users/{name}` | Buscar por nome |
| GET | `/users/cpf/{cpf}` | Buscar por CPF |
| GET | `/users/cnpj/{cnpj}` | Buscar por CNPJ |
| GET | `/users/role/{role}` | Buscar por role |
| POST | `/users` | Criar usuário |
| PUT | `/users/{id}` | Atualizar |
| DELETE | `/users/{id}` | Remover |

### Lojas (`/api/stores`)
| Método | Path | Descrição |
|--------|------|-----------|
| GET | `/api/stores` | Listar todas |
| GET | `/api/stores/{id}` | Buscar por ID |
| POST | `/api/stores` | Criar loja |
| PUT | `/api/stores/{id}` | Atualizar |
| DELETE | `/api/stores/{id}` | Remover |
| GET | `/api/stores/{id}/products` | Listar produtos da loja |
| POST | `/api/stores/{id}/products` | Criar produto na loja |

### Pagamentos (`/api/payments`)
| Método | Path | Descrição |
|--------|------|-----------|
| GET | `/api/payments` | Listar todos |
| GET | `/api/payments/{id}` | Buscar por ID |
| POST | `/api/payments` | Processar pagamento |
| PUT | `/api/payments/{id}/cancel` | Cancelar pagamento |
| GET | `/api/payments/store/{storeId}` | Por loja |
| GET | `/api/payments/status/{status}` | Por status |

### Relatórios (`/api/reports`)
| Método | Path | Descrição |
|--------|------|-----------|
| POST | `/api/reports/sales/{storeId}` | Relatório de vendas |
| POST | `/api/reports/payments/{storeId}` | Relatório de pagamentos |

## Testes

```bash
# Rodar todos os testes
./mvnw test

# Rodar uma classe específica
./mvnw test -Dtest=UserControllerTest
```

44 testes unitários (controllers com MockMvc + repositórios com H2).

## Validação

CPF e CNPJ validados com dígitos verificadores (algoritmo brasileiro):

- `@CPF` — 11 dígitos, cálculo dos DV
- `@CNPJ` — 14 dígitos, cálculo dos DV com pesos
