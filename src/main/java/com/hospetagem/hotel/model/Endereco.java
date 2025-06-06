package com.hospetagem.hotel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    @Name("id_endereco")
    private long id_endereco;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String Bairro;

    @Column
    private String complemento;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String cep;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    @JsonIgnore
    private Cliente cliente;

    @OneToOne(mappedBy = "endereco", cascade = CascadeType.ALL)
    private Funcionario funcionario;

    @PrePersist
    @PreUpdate
    public void formatarCampos() {
        this.logradouro = (logradouro != null) ? logradouro.toUpperCase() : null;
        this.numero = (numero != null) ? numero.toUpperCase() : null;
        this.Bairro = (Bairro != null) ? Bairro.toUpperCase() : null;
        this.cidade = (cidade != null) ? cidade.toUpperCase() : null;
        this.estado = (estado != null) ? estado.toUpperCase() : null;
        this.cep = (cep != null) ? cep.toUpperCase() : null;
    }
}
