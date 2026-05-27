# Aamuserapi — API do Projeto Alto Alegre Mercado

API REST para vitrine de pequenos negócios. Lojistas cadastram lojas, produtos, categorias e promoções. Clientes acessam a vitrine e entram em contato via WhatsApp, Telegram ou telefone para fazer pedidos.

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
| Segurança | Spring Security + JWT (jjwt 0.12.6) |
| Documentação | Springdoc OpenAPI (Swagger UI) |
| Testes | JUnit 5, Mockito, MockMvc |

---

## Estrutura do projeto

```
src/main/java/br/com/altoalegremercado/aamuserapi/
├── config/
│   ├── SecurityConfig.java          SecurityFilterChain com JWT
│   ├── JwtService.java              Geração/validação de tokens
│   ├── JwtAuthenticationFilter.java  Filtro de autenticação
│   ├── UserDetailsServiceImpl.java   Carrega usuário por email
│   ├── UserPrincipal.java            Adaptador UserDetails
│   └── OpenApiConfig.java            Configuração do Swagger
├── controller/
│   ├── AuthController.java           /auth (login/register)
│   ├── UserController.java           /users
│   ├── StoreController.java          /api/stores
│   ├── ProductController.java        /api/products (busca global)
│   ├── CategoryController.java       /api/categories
│   ├── PromotionController.java      /api/promotions
│   ├── PaymentController.java        /api/payments
│   ├── ReportController.java         /api/reports (stub)
│   ├── GlobalExceptionHandler.java   Tratamento global de erros
│   └── dto/
│       ├── LoginRequest.java
│       ├── LoginResponse.java
│       ├── UserDTO.java
│       ├── StoreDTO.java
│       ├── ProductDTO.java
│       ├── CategoryDTO.java
│       ├── PromotionDTO.java
│       └── PaymentDTO.java
├── service/
│   ├── UserService / UserServiceImpl
│   ├── StoreService / StoreServiceImpl
│   ├── ProductService / ProductServiceImpl
│   ├── CategoryService / CategoryServiceImpl
│   ├── PromotionService / PromotionServiceImpl
│   ├── PaymentService / PaymentServiceImpl
│   └── ReportService / ReportServiceImpl
├── repository/
│   ├── UserRepository.java
│   ├── StoreRepository.java
│   ├── ProductRepository.java
│   ├── CategoryRepository.java
│   ├── PromotionRepository.java
│   └── PaymentRepository.java
├── domain/model/
│   ├── User.java, Person.java, Role.java, UserRole.java
│   ├── Address.java
│   ├── Store.java, Product.java
│   ├── Category.java
│   ├── Promotion.java
│   ├── Payment.java, PaymentStatus.java
└── validation/
    ├── CPF.java, CPFValidator.java
    ├── CNPJ.java, CNPJValidator.java
```

```
src/test/
└── java/br/com/altoalegremercado/aamuserapi/
    ├── controller/
    │   ├── AuthControllerTest.java
    │   ├── UserControllerTest.java
    │   ├── StoreControllerTest.java
    │   ├── ProductControllerTest.java
    │   ├── CategoryControllerTest.java
    │   ├── PromotionControllerTest.java
    │   ├── PaymentControllerTest.java
    │   ├── ReportControllerTest.java
    │   └── GlobalExceptionHandlerTest.java
    ├── repository/
    │   ├── UserRepositoryTest.java
    │   ├── StoreRepositoryTest.java
    │   ├── ProductRepositoryTest.java
    │   ├── CategoryRepositoryTest.java
    │   ├── PromotionRepositoryTest.java
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

## Documentação (Swagger)

Com a aplicação rodando, acesse:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

O Swagger UI permite explorar e testar todos os endpoints. Use o botão **Authorize** para configurar o token JWT (`Bearer <token>`).

---

## Autenticação

A maioria dos endpoints exige token JWT no header:

```
Authorization: Bearer <seu-token>
```

### Obter token

```bash
# Registrar novo usuário
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"João","email":"joao@email.com","password":"123456","roles":["SHOP"]}'

# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"joao@email.com","password":"123456"}'
```

Ambos retornam: `{ "token": "eyJ...", "type": "Bearer", "expiresIn": 86400000 }`

---

## Endpoints

### Autenticação (`/auth`) — público

| Método | Path | Descrição |
|---|---|---|
| POST | `/auth/login` | Login (email + password) → JWT |
| POST | `/auth/register` | Cadastro → JWT |

### Lojas (`/api/stores`)

| Método | Path | Descrição | Auth |
|---|---|---|---|
| GET | `/api/stores` | Listar todas | — |
| GET | `/api/stores/search?q=termo` | Buscar por nome | — |
| GET | `/api/stores/{id}` | Buscar por ID | — |
| GET | `/api/stores/me` | Minhas lojas | JWT |
| POST | `/api/stores` | Criar loja | JWT |
| PUT | `/api/stores/{id}` | Atualizar | JWT |
| DELETE | `/api/stores/{id}` | Remover | JWT |
| GET | `/api/stores/{id}/products` | Listar produtos | — |
| GET | `/api/stores/{id}/products?categoryId=X` | Filtrar por categoria | — |
| POST | `/api/stores/{id}/products` | Criar produto | JWT |

### Produtos (`/api/products`) — público

| Método | Path | Descrição |
|---|---|---|
| GET | `/api/products/{id}` | Buscar por ID |
| GET | `/api/products/search?q=termo` | Buscar por nome |
| GET | `/api/products/category/{categoryId}` | Listar por categoria |

### Categorias (`/api/categories`)

| Método | Path | Descrição | Auth |
|---|---|---|---|
| GET | `/api/categories` | Listar todas | — |
| GET | `/api/categories/{id}` | Buscar por ID | — |
| POST | `/api/categories` | Criar | JWT |
| PUT | `/api/categories/{id}` | Atualizar | JWT |
| DELETE | `/api/categories/{id}` | Remover | JWT |

### Promoções (`/api/promotions`)

| Método | Path | Descrição | Auth |
|---|---|---|---|
| GET | `/api/promotions/product/{productId}` | Listar promoções de um produto | JWT |
| GET | `/api/promotions/{id}` | Buscar por ID | JWT |
| POST | `/api/promotions` | Criar | JWT |
| PUT | `/api/promotions/{id}` | Atualizar | JWT |
| DELETE | `/api/promotions/{id}` | Remover | JWT |

### Usuários (`/users`) — JWT

| Método | Path | Descrição |
|---|---|---|
| GET | `/users` | Listar todos |
| GET | `/users/{name}` | Buscar por nome |
| GET | `/users/cpf/{cpf}` | Buscar por CPF |
| GET | `/users/cnpj/{cnpj}` | Buscar por CNPJ |
| GET | `/users/role/{role}` | Buscar por role |
| POST | `/users` | Criar |
| PUT | `/users/{id}` | Atualizar |
| DELETE | `/users/{id}` | Remover |

### Pagamentos (`/api/payments`) — JWT

| Método | Path | Descrição |
|---|---|---|
| GET | `/api/payments` | Listar todos |
| GET | `/api/payments/{id}` | Buscar por ID |
| POST | `/api/payments` | Processar |
| PUT | `/api/payments/{id}/cancel` | Cancelar |
| GET | `/api/payments/store/{storeId}` | Por loja |
| GET | `/api/payments/status/{status}` | Por status |

### Relatórios (`/api/reports`) — JWT (stub)

| Método | Path | Descrição |
|---|---|---|
| POST | `/api/reports/sales/{storeId}` | Relatório de vendas |
| POST | `/api/reports/payments/{storeId}` | Relatório de pagamentos |

---

## Testes

### Rodar todos os testes

```bash
./mvnw clean test
```

### Rodar uma classe específica

```bash
./mvnw test -Dtest=AuthControllerTest
./mvnw test -Dtest=StoreControllerTest
./mvnw test -Dtest=CategoryControllerTest
```

### Cobertura

**Total: 79 testes** (unitários e de integração)

| Grupo | Quantidade | Descrição |
|---|---|---|
| Controller | 62 | Testes com MockMvc e mocks dos serviços |
| Repository | 15 | Testes com JPA/H2 |
| DTO | 2 | Validação de campos |
| Application | 1 | Contexto da aplicação |

---

## Observações técnicas

### Segurança

- Todas as senhas são armazenadas com **hash BCrypt**.
- Autenticação via **JWT** (HMAC-SHA256) com token de 24h de validade.
- Endpoints públicos: `/auth/**`, `GET /api/stores/**`, `GET /api/products/**`, `GET /api/categories/**`, Swagger UI.
- Durante os testes o Spring Security é desabilitado via `spring.autoconfigure.exclude`.

### Banco de dados

- **Produção/desenvolvimento:** MySQL 8.0 (`src/main/resources/application.properties`)
- **Testes:** H2 em memória com compatibilidade MySQL (`src/test/resources/application-test.properties`)

### Compilação

- Projeto exige **Java 21**.
- Use o wrapper `./mvnw` incluso.

---
