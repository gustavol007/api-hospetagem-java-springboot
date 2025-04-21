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

src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── hospetagem/
│   │           └── hotel/
│   │               ├── controllers/         # Controladores REST
│   │               │   ├── ClienteController.java
│   │               │   ├── FuncionarioController.java
│   │               │
│   │               ├── model/              # Entidades JPA
│   │               │   ├── Cliente.java
│   │               │   ├── Endereco.java
│   │               │   ├── Funcionario.java
│   │               │   ├── Pessoa.java
│   │               │
│   │               ├── repository/         # Repositórios JPA
│   │               │   ├── ClienteRepository.java
│   │               │   ├── FuncionarioRepository.java
│   │               │   ├── EnderecoRepository.java
│   │               │
│   │               ├── service/            # Serviços de Negócio
│   │               │   ├── ClienteService.java
│   │               │   ├── FuncionarioService.java
│   │               │
│   │               └── HotelApplication.java   # Classe principal
│
├── resources/
│   ├── application.properties     # Configurações do Spring Boot
│   ├── data.sql                   # Dados iniciais (opcional)
│   ├── schema.sql                 # Script de criação do banco (opcional)
│
└── test/                          # Testes unitários e de integração
