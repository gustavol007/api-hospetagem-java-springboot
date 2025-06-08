package com.hospetagem.hotel.model;

import com.hospetagem.hotel.model.enums.PortePet;
import com.hospetagem.hotel.model.enums.SexoPet;
import com.hospetagem.hotel.model.enums.SimNao;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tb_pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false)
    private String nome;
    
    @Min(value = 0, message = "A idade não pode ser negativa")
    @Max(value = 50, message = "A idade máxima permitida é 50 anos")
    private Integer idade;

    private String especie;

    @Enumerated(EnumType.STRING)
    private PortePet porte;
    
    @Size(max = 50, message = "A raça deve ter no máximo 50 caracteres")
    private String raca;

    @Size(max = 50, message = "A espécie deve ter no máximo 50 caracteres")
    @Column(nullable = false)
    private String peso;

    private String temperamento;

    @Enumerated(EnumType.STRING)
    private SexoPet sexo;

    @Enumerated(EnumType.STRING)
    private SimNao castrado;

    @Enumerated(EnumType.STRING)
    private SimNao historicoDoencaAlergia;

    @Enumerated(EnumType.STRING)
    private SimNao convivenciaAnimais;

    private String observacao;

    @ManyToMany(mappedBy = "pets")
private List<Reserva> reservas = new ArrayList<>();

@ManyToOne
@JoinColumn(name = "cliente_id", nullable = false)
private Cliente cliente;
}