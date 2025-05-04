package com.hospetagem.hotel.dto;

import com.hospetagem.hotel.model.enums.PortePet;
import com.hospetagem.hotel.model.enums.SexoPet;
import jakarta.validation.constraints.*;

public record PetDTO(
        Long id,
        
        @NotBlank(message = "O nome do pet é obrigatório")
        @Size(max = 100, message = "O nome do pet deve ter no máximo 100 caracteres")
        String nome,
        
        @Min(value = 0, message = "A idade não pode ser negativa")
        @Max(value = 50, message = "A idade máxima permitida é 50 anos")
        Integer idade,
        
        @Size(max = 50, message = "A raça deve ter no máximo 50 caracteres")
        String raca,
        
        @NotBlank(message = "A espécie é obrigatória")
        @Size(max = 50, message = "A espécie deve ter no máximo 50 caracteres")
        String especie,
        
        SexoPet sexo,
        
        PortePet porte
) {
    public PetDTO {
        // Capitaliza o nome
        if (nome != null) {
            nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
        }
        
        // Garante que raça e espécie comecem com letra maiúscula
        if (raca != null && !raca.isEmpty()) {
            raca = raca.substring(0, 1).toUpperCase() + raca.substring(1).toLowerCase();
        }
        
        if (especie != null && !especie.isEmpty()) {
            especie = especie.substring(0, 1).toUpperCase() + especie.substring(1).toLowerCase();
        }
    }
}