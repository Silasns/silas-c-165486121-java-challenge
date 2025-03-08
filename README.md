# 🛍️ Loja Virtual API

API REST para gerenciamento de uma loja virtual, construída com **Java 17** e **Spring Boot 3.4.3**.

---

## 🚀 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.4.3**
- **Maven**
- **Docker**
- **Lombok**
- **SpringDoc OpenAPI (Swagger UI)**

---

## 📥 Pré-requisitos
Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:

- [JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop/)
- [Git](https://git-scm.com/downloads)

---

## 📌 Como Rodar o Projeto Localmente

### 1️⃣ Clone o Repositório

```
git clone https://github.com/seu-usuario/loja-virtual-api.git
cd loja-virtual-api
```
---

### 2️⃣ Rodar a Aplicação Localmente
### Compilar e Construir o JAR

```
mvn clean package
```
Isso gerará um JAR na pasta target/ chamado loja-virtual-api-0.0.1-SNAPSHOT.jar.
--
Se quiser rodar sem Docker, use:

```
mvn spring-boot:run
```

ou

```
java -jar target/loja-virtual-api-0.0.1-SNAPSHOT.jar

```
A API estará disponível em: http://localhost:8080.

---

### 🐳 Rodando com Docker
A aplicação já está preparada para rodar via Docker.

1️⃣ Estar com o docker aberto
2️⃣ Construir a Imagem Docker
Na Raiz do projeto, onde estar o arquivo Dockerfile, execute:

```
docker build -t app .
```

3️⃣ Apos a imagem ser gerada, rode o container utilizando:

```
docker run -p 8080:8080 app
```

---
### 📖 Documentação da API
A API possui documentação automática via Swagger UI.

Após rodar a aplicação, acesse: 
Swagger UI: http://localhost:8080/swagger-ui/index.html

---

✅ Testes
A API possui testes unitários utilizando JUnit 5 e Mockito.

---

## 🔧 Endpoints Principais

### 📦 Produtos
| Método   | Rota                                    | Descrição                                       |
|----------|----------------------------------------|-------------------------------------------------|
| `POST`   | `/api/v1/produtos`                    | Cadastra um novo produto                        |
| `GET`    | `/api/v1/produtos`                    | Lista todos os produtos cadastrados            |
| `GET`    | `/api/v1/produtos/{id}`               | Busca um produto pelo ID                        |
| `DELETE` | `/api/v1/produtos/{id}`               | Remove um produto pelo ID                       |
| `POST`   | `/api/v1/produtos/{idProduto}/categorias/{nomeCategoria}` | Associa um produto a uma categoria             |
| `DELETE` | `/api/v1/produtos/{idProduto}/categorias/{nomeCategoria}` | Desassocia um produto de uma categoria         |

---

### 🏷️ Categorias
| Método   | Rota                                    | Descrição                                       |
|----------|----------------------------------------|-------------------------------------------------|
| `POST`   | `/api/v1/categorias`                  | Cadastra uma nova categoria                     |
| `GET`    | `/api/v1/categorias`                  | Lista todas as categorias cadastradas           |
| `GET`    | `/api/v1/categorias/{nome}`           | Busca uma categoria pelo nome                   |
| `DELETE` | `/api/v1/categorias/{nome}`           | Remove uma categoria pelo nome                  |

---

### 🛒 Carrinho de Compras
| Método   | Rota                                    | Descrição                                       |
|----------|----------------------------------------|-------------------------------------------------|
| `POST`   | `/api/v1/carrinho`                    | Adiciona um produto ao carrinho                 |
| `GET`    | `/api/v1/carrinho`                    | Lista todos os produtos no carrinho             |
| `DELETE` | `/api/v1/carrinho`                    | Remove um produto do carrinho                   |
