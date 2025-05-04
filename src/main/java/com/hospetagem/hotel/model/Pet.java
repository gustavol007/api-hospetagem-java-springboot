package com.hospetagem.hotel.model;

import com.hospetagem.hotel.model.enums.PortePet;
import com.hospetagem.hotel.model.enums.SexoPet;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "O nome do pet é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false)
    private String nome;
    
    @Min(value = 0, message = "A idade não pode ser negativa")
    @Max(value = 50, message = "A idade máxima permitida é 50 anos")
    private Integer idade;
    
    @Size(max = 50, message = "A raça deve ter no máximo 50 caracteres")
    private String raca;
    
    @NotBlank(message = "A espécie é obrigatória")
    @Size(max = 50, message = "A espécie deve ter no máximo 50 caracteres")
    @Column(nullable = false)
    private String especie;
    
    @Enumerated(EnumType.STRING)
    private SexoPet sexo = SexoPet.INDEFINIDO;
    
    @Enumerated(EnumType.STRING)
    private PortePet porte = PortePet.INDEFINIDO;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public SexoPet getSexo() {
        return sexo;
    }

    public void setSexo(SexoPet sexo) {
        this.sexo = sexo != null ? sexo : SexoPet.INDEFINIDO;
    }

    public PortePet getPorte() {
        return porte;
    }

    public void setPorte(PortePet porte) {
        this.porte = porte != null ? porte : PortePet.INDEFINIDO;
    }
}