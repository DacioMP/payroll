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
