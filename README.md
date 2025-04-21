# 📦 Hotel Hospetagem

Api Java para um hotel de pet

## 🚀 Funcionalidades

- ✅ Validação de strings e caracteres
- ✅ Estrutura modular orientada a objetos
- ✅ Arqutetura MVC
- ✅ SOLID

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
  - Spring Data JPA
  - Spring Web
  - Spring Validation
- **Hibernate** (ORM)
- **MySQL** (Banco de Dados)
- **Lombok** (para reduzir boilerplate de código)
- **Maven** (gerenciador de dependências)

## 📁 Estrutura de Pastas

📦 src/main/java/com/hospetagem/hotel ├── controllers # Controladores da aplicação (REST APIs) │ ├── ClienteController.java │ ├── FuncionarioController.java │ └── (... outros controladores) ├── dto # Data Transfer Objects (DTOs) │ ├── ClienteDTO.java │ ├── EnderecoDTO.java │ ├── FuncionarioDTO.java │ └── (... outros DTOs) ├── mapper # Mapeadores para conversão de Entidades ↔ DTOs │ ├── ClienteMapper.java │ ├── EnderecoMapper.java │ └── FuncionarioMapper.java ├── model # Modelos/JPA Entities │ ├── Cliente.java │ ├── Endereco.java │ ├── Funcionario.java │ ├── Pessoa.java │ └── (... outras entidades) ├── repository # Interfaces de repositórios JPA │ ├── ClienteRepository.java │ ├── EnderecoRepository.java │ ├── FuncionarioRepository.java │ └── (... outros repositórios) ├── service # Camada de serviços (regras de negócio) │ ├── ClienteService.java │ ├── EnderecoService.java │ ├── FuncionarioService.java │ └── (... outros serviços) └── HotelApplication.java # Classe principal para inicialização do Spring Boot



---

### Diretórios do projeto

1. **controllers**:
   Contém as classes responsáveis pelas APIs REST. É onde as requisições HTTP são gerenciadas.
   
2. **dto**:
   Arquivos que representam objetos de transferência de dados. São usados para expor ou receber dados de forma simplificada, ao invés de manipular diretamente as entidades.

3. **mapper**:
   Contém as classes que convertem entre entidades do banco de dados (`model`) e os objetos de transferência de dados (`dto`).

4. **model**:
   Abriga as entidades JPA que representam as tabelas do banco de dados.

5. **repository**:
   Interfaces responsáveis por acessar e manipular os dados no banco por meio do JPA.

6. **service**:
   Camada de lógica de negócios. É aqui que ficam as regras de manipulação e validação antes de acessar o banco ou expor dados para as controllers.

7. **HotelApplication.java**:
   Classe principal para inicializar a aplicação Spring Boot.
