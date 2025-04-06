package com.hospetagem.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@MappedSuperclass // OU use @Entity para a estratégia JOINED
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private Date data_nascimento;

    @Enumerated(EnumType.STRING)
    private Pessoa.Sexo sexo;

    public enum Sexo {
        MASCULINO,
        FEMININO,
        OUTRO
    }

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    public enum Status {
        ATIVO,
        INATIVO;
    }
}