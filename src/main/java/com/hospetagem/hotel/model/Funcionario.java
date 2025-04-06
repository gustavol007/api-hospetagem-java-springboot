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
}
