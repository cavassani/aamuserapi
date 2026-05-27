# Progresso do Projeto AAM User API

## Legenda
- ✅ Completo
- ⚠️ Parcial
- ❌ Pendente

---

## 1. Autenticação JWT ✅

**O que foi feito:**
- Adicionada dependência `jjwt` (api, impl, jackson) no `pom.xml`
- Criado `JwtService` — gera e valida tokens JWT com HMAC-SHA256
- Criado `JwtAuthenticationFilter` — filtro `OncePerRequestFilter` que lê header `Authorization: Bearer <token>` e autentica a requisição
- Criado `UserDetailsServiceImpl` — carrega usuário por email do banco para o Spring Security
- Criado `UserPrincipal` — adaptador que embrulha entidade `User` como `UserDetails`
- Criado `AuthController` — `POST /auth/login` e `POST /auth/register` retornam JWT
- Criados DTOs: `LoginRequest` (email + password), `LoginResponse` (token + type + expiresIn)
- Atualizado `SecurityConfig` — substituído `WebSecurityCustomizer` (ignorava tudo) por `SecurityFilterChain` com JWT
- Atualizado `UserServiceImpl` — senha agora usa `BCryptPasswordEncoder`, e `createUserFromDTO` converte `List<Role>` para `List<UserRole>`
- Adicionado `findByEmail` no `UserRepository`
- Adicionada config `app.jwt.secret` e `app.jwt.expiration` no `application.properties`

**Endpoints:**
- `POST /auth/register` — público
- `POST /auth/login` — público
- Demais endpoints exigem `Authorization: Bearer <token>`
- `GET /api/stores/**` — público (vitrine)

---

## 2. Vínculo Usuário ↔ Loja ✅

**O que foi feito:**
- Adicionado campo `User owner` com `@ManyToOne` e join column `owner_id` na entidade `Store`
- Adicionado `findByOwnerId(Long)` no `StoreRepository`
- `StoreService.createStore` agora recebe `User owner` como parâmetro
- Adicionado `getStoresByOwner(User)` no `StoreService`
- `StoreController.createStore` usa `@AuthenticationPrincipal` para vincular ao dono autenticado
- Novo endpoint `GET /api/stores/me` — lista lojas do usuário logado

**Endpoint:**
- `GET /api/stores/me` — autenticado

---

## 3. WhatsApp / Telegram / Telefone na Loja ❌

**Pendente:**
- Adicionar campo `telegram` (username) na entidade `Store`
- Adicionar campo `whatsapp` ou usar `cellphone` com link `wa.me`
- Definir canal preferido
- Botão "Pedir via..." no frontend gera link `wa.me` / `t.me` / `tel:`

---

## 4. Upload de Imagens ❌

**Pendente:**
- Configurar armazenamento local ou S3
- Adicionar campo `imageUrl` no `Product`
- Endpoint para upload de imagem
- Servir imagens estaticamente

---

## 5. Categorias ✅

**O que foi feito:**
- Criada entidade `Category` (id, name, description, active, creationDate)
- Criado `CategoryRepository` com busca por nome e active
- Criado `CategoryDTO`
- Criado `CategoryService` + `CategoryServiceImpl` com CRUD
- Criado `CategoryController` com endpoints REST
- Adicionado `@ManyToOne Category category` no `Product`
- Adicionado `categoryId` no `ProductDTO`
- Adicionado `findByCategoryId(Long)` no `ProductRepository`
- `ProductServiceImpl.createProduct` e `updateProduct` associam categoria
- `GET /api/categories/**` público na SecurityConfig

**Endpoints:**
- `GET /api/categories` — público
- `GET /api/categories/{id}` — público
- `POST /api/categories` — autenticado
- `PUT /api/categories/{id}` — autenticado
- `DELETE /api/categories/{id}` — autenticado

---

## 6. Frontend Web (vitrine pública) ❌

**Pendente:**
- Criar frontend web (React, Vue, ou HTML+JS puro)
- Página pública para listar lojas
- Página pública para ver produtos de uma loja
- Página de login/cadastro para lojistas
- Dashboard do lojista para gerenciar loja/produtos/promoções

---

## 7. Promoções/Descontos ✅

**O que foi feito:**
- Criada entidade `Promotion` (id, product, promotionalPrice, startDate, endDate, description, active, creationDate)
- Criado `PromotionRepository` com busca por productId e productId+active
- Criado `PromotionDTO` com productId, promotionalPrice, startDate ISO, endDate ISO, active
- Criado `PromotionService` + `PromotionServiceImpl` com CRUD
- Criado `PromotionController` com endpoints REST

**Endpoints:**
- `GET /api/promotions/product/{productId}` — lista promoções de um produto (autenticado)
- `GET /api/promotions/{id}` — autenticado
- `POST /api/promotions` — autenticado
- `PUT /api/promotions/{id}` — autenticado
- `DELETE /api/promotions/{id}` — autenticado

---

## 8. Botão "Pedir via WhatsApp / Telegram / Telefone" ❌

**Pendente:**
- Frontend: gerar link `wa.me/55...` com mensagem padrão
- Frontend: gerar link `t.me/...`
- Frontend: gerar link `tel:+55...`
- Exibir os contatos na página da loja

---

## 9. App Mobile (iOS/Android) ❌

**Pendente:**
- Criar PWA primeiro (mais rápido)
- Depois app nativo com React Native ou Flutter

---

## 10. Relatórios ⚠️

**Status:** Stub — `ReportServiceImpl` lança `UnsupportedOperationException`

**Pendente:**
- Implementar geração de relatórios com JasperReports (conforme README)

---

## 11. Busca de Lojas e Produtos ✅

**O que foi feito:**
- Adicionado `searchByName(String)` no `StoreService` + `StoreServiceImpl` usando `findByNameContaining`
- Adicionado `searchByName(String)` e `getProductsByCategory(Long)` no `ProductService` + `ProductServiceImpl`
- Adicionado `findByStoreIdAndCategoryId(Long, Long)` no `ProductRepository`
- Criado `ProductController` com busca global de produtos
- Adicionado `GET /api/stores/search?q=termo` no `StoreController`
- Adicionado filtro opcional `categoryId` em `GET /api/stores/{storeId}/products`
- `GET /api/products/**` público na SecurityConfig

**Endpoints públicos:**
- `GET /api/stores/search?q=termo` — busca lojas por nome
- `GET /api/stores/{storeId}/products?categoryId=X` — filtra produtos por categoria na loja
- `GET /api/products/search?q=termo` — busca produtos global por nome
- `GET /api/products/{id}` — produto por ID
- `GET /api/products/category/{categoryId}` — produtos por categoria

---

## 12. Documentação (Swagger) ✅

**O que foi feito:**
- Adicionada dependência `springdoc-openapi-starter-webmvc-ui` no `pom.xml`
- Criado `OpenApiConfig` com info (título, descrição, versão, contato, licença) e configuração de segurança Bearer JWT
- Liberados caminhos `/swagger-ui/**` e `/v3/api-docs/**` no `SecurityConfig`

**Acesso:**
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

---

## 13. CI/CD ❌

**Pendente:**
- Configurar GitHub Actions
- Build automático
- Deploy

---

## Resumo

| Item | Status |
|---|---|
| 1. Autenticação JWT | ✅ |
| 2. Vínculo Usuário↔Loja | ✅ |
| 3. WhatsApp/Telegram/Telefone na Loja | ❌ |
| 4. Upload de Imagens | ❌ |
| 5. Categorias | ✅ |
| 6. Frontend Web (vitrine) | ❌ |
| 7. Promoções/Descontos | ✅ |
| 8. Botão "Pedir via..." | ❌ |
| 9. App Mobile | ❌ |
| 10. Relatórios | ⚠️ |
| 11. Busca | ✅ |
| 12. Documentação (Swagger) | ✅ |
| 13. CI/CD | ❌ |

**Testes:** 79/79 passando
