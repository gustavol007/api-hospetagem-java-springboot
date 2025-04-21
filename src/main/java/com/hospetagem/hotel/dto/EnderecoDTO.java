package com.hospetagem.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(

        Long id_endereco,

        @NotBlank(message = "O logradouro é obrigatório") // Validação para campos obrigatórios
        String logradouro,

        @NotBlank(message = "O número é obrigatório")
        String numero,

        String complemento, // Campo opcional

        @NotBlank(message = "O bairro é obrigatório")
        String bairro,

        @NotBlank(message = "A cidade é obrigatória")
        String cidade,

        @NotBlank(message = "O estado é obrigatório")
        @Size(min = 2, max = 2, message = "O estado deve ter exatamente 2 caracteres")
        String estado, // Sigla do estado (SP, RJ, etc.)

        @NotBlank(message = "O CEP é obrigatório")
        @Size(min = 8, max = 8, message = "O CEP deve ter exatamente 8 dígitos")
        String cep // Formato esperado: "12345678" (apenas números)

) {
}
