# Payroll

## Descrição do Projeto
Projeto baseado no tutorial [Building REST services with Spring](https://spring.io/guides/tutorials/rest) da documentação oficial do Spring Framework, com o objetivo de converter uma API não REST em uma API REST. 
O projeto envolve duas entidades: Employee e Order. A versão final oferece uma API REST que segue os princípios de *Hypermedia as the Engine of Application State (HATEOAS)*, 
implementados através do módulo Spring HATEOAS.

## Tecnologias Utilizadas
O projeto foi desenvolvido utilizando as seguintes tecnologias:

- Java 21
- Spring Boot 3.4
- Spring Web
- Spring Data JPA
- H2 Database
- Spring HATEOAS

## Além Projeto Original

O tutorial [Building REST services with Spring](https://spring.io/guides/tutorials/rest) foca no desenvolvimento da API em si, deixando ao desenvolvedor a responsabilidade de organizar o projeto. 
Como resultado, foram adicionadas duas camadas ao projeto: *services* e *repositories*. Ao encapsular as regras de negócio na camada service, a separação de responsabilidades foi aprimorada, tornando-a mais bem definida.

## Pré-requisitos
Antes de rodar o projeto, certifique-se de que você possui os seguintes pré-requisitos instalados:

- Java 21+

## Como executar

1. **Clone este repositório:**

   ```bash
   git clone https://github.com/DacioMP/payroll.git
   cd payroll
   ```
2. **Execute o projeto**
   ```bash
   ./mvnw clean spring-boot:run
   ```
   
## Endpoints (Employee)

### 1. **Recuperar todos os Employees**

- **Método:** GET
- **URL:** `localhost:8080/employees`
- **Descrição:** Recupera a lista de todos os employees.

### 2. **Recuperar um Employee por ID**

- **Método:** GET
- **URL:** `localhost:8080/employees/{id}`
- **Descrição:** Recupera os dados de um employee específico pelo seu ID.

### 3. **Criar um novo Employee**

- **Método:** POST
- **URL:** `localhost:8080/employees`
- **Corpo da Requisição:** 
  ```json
  {"name": "Samwise Gamgee", "role": "gardener"}
- **Descrição:** Cria um novo employee com as informações fornecidas no corpo da requisição.

### 4. **Atualizar um Employee**

- **Método:** PUT
- **URL:** `localhost:8080/employees/{id}`
- **Corpo da Requisição:** 
  ```json
  {"name": "Samwise Gamgee", "role": "ring bearer"}
- **Descrição:** Atualiza os dados de um employee existente pelo ID, com as novas informações fornecidas no corpo da requisição.

### 5. **Deletar um Employee**

- **Método:** DELETE
- **URL:** `localhost:8080/employees/{id}`
- **Descrição:** Deleta um employee específico pelo seu ID.

## Endpoints (Order)

### 1. **Recuperar todos os Pedidos**

- **Método:** GET
- **URL:** `localhost:8080/orders`
- **Descrição:** Recupera a lista de todos os pedidos.

### 2. **Recuperar um Pedido por ID**

- **Método:** GET
- **URL:** `localhost:8080/orders/{id}`
- **Descrição:** Recupera os dados de um pedido específico pelo seu ID.

### 3. **Criar um novo Pedido**

- **Método:** POST
- **URL:** `localhost:8080/orders`
- **Corpo da Requisição:** 
  ```json
  {"description": "Samsung S24"}
- **Descrição:** Cria um novo pedido com as informações fornecidas no corpo da requisição.

### 4. **Completar um Pedido**

- **Método:** PUT
- **URL:** `localhost:8080/orders/{id}/complete`
- **Descrição:** Atualiza o status de pedido para *COMPLETED* caso seu status atual seja *IN_PROGRESS*.

### 5. **Cancelar um Pedido**

- **Método:** DELETE
- **URL:** `localhost:8080/orders/{id}/cancel`
- **Descrição:** Atualiza o status de um pedido para *CANCELLED* caso seu status atual seja *IN_PROGRESS*.
