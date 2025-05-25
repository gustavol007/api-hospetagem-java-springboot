package com.hospetagem.hotel.dto;


import com.hospetagem.hotel.model.enums.SexoPet;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PetDTO(
        Long id,

        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        @NotBlank(message = "O nome do pet é obrigatório")
        String nome,

        @Min(value = 0, message = "A idade não pode ser negativa")
        @Max(value = 50, message = "A idade máxima permitida é 50 anos")
        Integer idade,

        @Size(max = 50, message = "A raça deve ter no máximo 50 caracteres")
        String raca,

        @Size(max = 50, message = "A espécie deve ter no máximo 50 caracteres")
        @NotBlank(message = "O peso é obrigatório")
        String peso,

        String temperamento,

        SexoPet sexo,

        String observacaoPet
){}