package com.hospetagem.hotel.dto;

import java.sql.Date;
import java.util.List;

import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.model.Endereco;
import com.hospetagem.hotel.model.Pessoa.Status;

import com.hospetagem.hotel.model.enums.Sexo;
import jakarta.validation.constraints.*;

public record ClienteDTO(
        Long id,

        @NotBlank(message = "O nome não pode estar vazio")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,

        @NotBlank(message = "O CPF é obrigatório")
        @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 caracteres")
        String cpf,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotNull(message = "A data de nascimento é obrigatória")
        Date data_nascimento,

        @NotNull(message = "O sexo é obrigatório")
        Sexo sexo,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).*$", message = "A senha deve conter ao menos uma letra maiúscula, uma letra minúscula e um caractere especial")
        String senha
) {
}