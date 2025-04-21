package com.hospetagem.hotel.mapper;

import com.hospetagem.hotel.dto.EnderecoDTO;
import com.hospetagem.hotel.model.Endereco;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnderecoMapper {

    // Converte de Entidade para DTO
    public static EnderecoDTO toDTO(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoDTO(
                endereco.getId_endereco(), // Ensure Endereco has a getId() method, or replace with appropriate field or logic
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                formatarCEP(endereco.getCep()) // Formata o CEP para exibição
        );
    }

    // Converte de DTO para Entidade
    public static Endereco toEntity(EnderecoDTO enderecoDTO) {
        if (enderecoDTO == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setLogradouro(enderecoDTO.logradouro().trim());
        endereco.setNumero(enderecoDTO.numero().trim());
        endereco.setComplemento(enderecoDTO.complemento() != null ? enderecoDTO.complemento().trim() : null);
        endereco.setBairro(enderecoDTO.bairro().trim());
        endereco.setCidade(enderecoDTO.cidade().trim());
        endereco.setEstado(enderecoDTO.estado().toUpperCase()); // Estado sempre em maiúsculas
        endereco.setCep(removerFormatacaoCEP(enderecoDTO.cep())); // Remove formatação do CEP antes de persistir
        return endereco;
    }

    /**
     * Formata o CEP para o formato padrão (XXXXX-XXX).
     */
    private static String formatarCEP(String cep) {
        if (cep == null || cep.length() != 8) {
            return cep; // Retorna sem formatação se for inválido
        }
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }

    /**
     * Remove qualquer formatação do CEP (mantém apenas números).
     */
    private static String removerFormatacaoCEP(String cep) {
        if (cep == null) {
            return null;
        }
        return cep.replaceAll("[^\\d]", ""); // Remove tudo que não for dígito
    }

    public static List<EnderecoDTO> toDTOList(List<Endereco> enderecos) {
        return enderecos.stream().map(EnderecoMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Endereco> toEntityList(List<EnderecoDTO> enderecoDTOs) {
        return enderecoDTOs.stream().map(EnderecoMapper::toEntity).collect(Collectors.toList());
    }

}