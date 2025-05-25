package com.hospetagem.hotel.model;

import com.hospetagem.hotel.model.enums.PortePet;
import com.hospetagem.hotel.model.enums.SexoPet;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

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
    
    @Size(max = 50, message = "A raça deve ter no máximo 50 caracteres")
    private String raca;

    @Size(max = 50, message = "A espécie deve ter no máximo 50 caracteres")
    @Column(nullable = false)
    private String peso;


    private String temperamento;

    @Enumerated(EnumType.STRING)
    private SexoPet sexo;

    private String observacaoPet;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = true)
    private Reserva reserva;
}