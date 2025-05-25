package com.hospetagem.hotel.mapper;

import com.hospetagem.hotel.dto.CartaoDTO;
import com.hospetagem.hotel.model.Cartao;
import com.hospetagem.hotel.model.Cliente;

public class CartaoMapper {
    // Método para converter de Cartao para CartaoDTO
    public static CartaoDTO toDTO(Cartao cartao) {
        if (cartao == null) {
            return null;
        }

        return new CartaoDTO(
                cartao.getId(),
                cartao.getNumeroCartao(),
                cartao.getNomeTitular(),
                cartao.getDataValidade(),
                cartao.getTipoCartao(),
                cartao.getCodigoSeguranca(),
                cartao.getCliente() != null ? cartao.getCliente().getId() : null
        );
    }

    // Método para converter de CartaoDTO para Cartao (entidade)
    public static Cartao toEntity(CartaoDTO dto, Cliente cliente) {
        if (dto == null) {
            return null;
        }

        Cartao cartao = new Cartao();
        cartao.setId(dto.id()); // Assume que o ID pode ser definido (como em um caso de update)
        cartao.setNumeroCartao(dto.numeroCartao());
        cartao.setNomeTitular(dto.nomeTitular());
        cartao.setDataValidade(dto.dataValidade());
        cartao.setTipoCartao(dto.tipoCartao());
        cartao.setCodigoSeguranca(dto.codigoSeguranca());
        cartao.setCliente(cliente); // O cliente deve ser carregado previamente (não gerado a partir do DTO)

        return cartao;
    }

}
