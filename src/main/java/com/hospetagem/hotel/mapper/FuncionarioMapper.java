package com.hospetagem.hotel.mapper;

import com.hospetagem.hotel.dto.FuncionarioDTO;
import com.hospetagem.hotel.model.Funcionario;

public class FuncionarioMapper {

    public static FuncionarioDTO toDTO(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getName(),
                funcionario.getEmail(),
                null, // Não incluir a senha no DTO por motivos de segurança
                formatarCPF(funcionario.getCpf()),
                formatarTelefone(funcionario.getTelefone()),
                funcionario.getData_nascimento(),
                funcionario.getSexo(),
                funcionario.getCargo(),
                funcionario.getSalario()
        );
    }

    public static Funcionario toEntity(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(funcionarioDTO.id());
        funcionario.setName(funcionarioDTO.name());
        funcionario.setEmail(funcionarioDTO.email());
        funcionario.setSenha(funcionarioDTO.senha());
        funcionario.setCpf(removerFormatacaoCPF(funcionarioDTO.cpf()));
        funcionario.setTelefone(removerFormatacaoTelefone(funcionarioDTO.telefone()));
        funcionario.setData_nascimento(funcionarioDTO.data_nascimento());
        funcionario.setSexo(funcionarioDTO.sexo());
        funcionario.setCargo(funcionarioDTO.cargo());
        funcionario.setSalario(funcionarioDTO.salario());
        return funcionario;
    }

    /**
     * Remove a formatação do CPF (pontos e traços).
     */
    private static String removerFormatacaoCPF(String cpf) {
        if (cpf == null) {
            return null;
        }
        return cpf.replaceAll("[^\\d]", ""); // Remove tudo que não for número
    }

    /**
     * Formata o CPF no padrão XXX.XXX.XXX-XX.
     */
    private static String formatarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) { // CPF deve ter 11 números
            return cpf; // Retorna como está se for inválido
        }
        return String.format("%s.%s.%s-%s",
                cpf.substring(0, 3),
                cpf.substring(3, 6),
                cpf.substring(6, 9),
                cpf.substring(9, 11));
    }

    /**
     * Remove a formatação do telefone (parênteses, traços e espaços).
     */
    private static String removerFormatacaoTelefone(String telefone) {
        if (telefone == null) {
            return null;
        }
        return telefone.replaceAll("[^\\d]", ""); // Remove tudo que não for número
    }

    /**
     * Formata o telefone no padrão (XX) XXXXX-XXXX.
     */
    private static String formatarTelefone(String telefone) {
        if (telefone == null || telefone.length() < 10) {
            return telefone; // Retorna como está se for inválido ou menor que 10 dígitos
        }
        String ddd = telefone.substring(0, 2);
        String parte1 = telefone.substring(2, telefone.length() - 4);
        String parte2 = telefone.substring(telefone.length() - 4);
        return String.format("(%s) %s-%s", ddd, parte1, parte2);
    }
}