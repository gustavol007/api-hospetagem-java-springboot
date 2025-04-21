package com.hospetagem.hotel.mapper;

import java.util.List;

import com.hospetagem.hotel.dto.ClienteDTO;
import com.hospetagem.hotel.model.Cliente;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getName(),
                cliente.getEmail(),
                formatarCPF(cliente.getCpf()),
                formatarTelefone(cliente.getTelefone()),
                cliente.getData_nascimento(),
                cliente.getSexo(),
                cliente.getSenha()
        );
    }

    public static Cliente toEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.id());
        cliente.setName(clienteDTO.name());
        cliente.setEmail(clienteDTO.email());
        cliente.setCpf(removerFormatacaoCPF(clienteDTO.cpf()));
        cliente.setTelefone(removerFormatacaoTelefone(clienteDTO.telefone()));
        cliente.setData_nascimento(clienteDTO.data_nascimento());
        cliente.setSexo(clienteDTO.sexo());
        cliente.setSenha(clienteDTO.senha());
        return cliente;
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
                cpf.substring(0, 3), // Primeiros 3 dígitos
                cpf.substring(3, 6), // Próximos 3 dígitos
                cpf.substring(6, 9), // Próximos 3 dígitos
                cpf.substring(9));  // Últimos 2 dígitos
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
        String ddd = telefone.substring(0, 2); // Os dois primeiros dígitos (DDD)
        String parte1 = telefone.substring(2, telefone.length() - 4); // Parte inicial do número
        String parte2 = telefone.substring(telefone.length() - 4); // Últimos 4 dígitos
        return String.format("(%s) %s-%s", ddd, parte1, parte2);
    }

}