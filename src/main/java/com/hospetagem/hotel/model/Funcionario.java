package com.hospetagem.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "funcionarios")
public class Funcionario extends Pessoa{
    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    private Double salario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id_endereco")
    private Endereco endereco;

    public void formatarCampos() {
        super.formatarCampos();
        this.cargo = (cargo != null) ? cargo.toUpperCase() : null;
    }
}
