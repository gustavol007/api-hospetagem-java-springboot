package com.hospetagem.hotel.model;

import com.hospetagem.hotel.model.enums.Role;
import com.hospetagem.hotel.model.enums.Sexo;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

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
    private Sexo sexo;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime criado_em;

    @UpdateTimestamp
    private LocalDateTime atualizado_em;

    @Column(name = "codigo_recuperacao", unique = true)
    private String codigoRecuperacao;

    @Column(name = "codigo_expiracao")
    private LocalDateTime codigoExpiracao;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    public enum Status {
        ATIVO,
        INATIVO;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    @PreUpdate
    public void formatarCampos() {
        this.name = (name != null) ? name.toUpperCase() : null;
        this.email = (email != null) ? email.toUpperCase() : null;
    }
}