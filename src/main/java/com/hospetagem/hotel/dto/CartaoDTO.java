package com.hospetagem.hotel.dto;

import com.hospetagem.hotel.model.enums.TipoCartao;

public record CartaoDTO(
        Long id,
        String numeroCartao,
        String nomeTitular,
        String dataValidade,
        TipoCartao tipoCartao,
        String codigoSeguranca,
        Long clienteId // Representa apenas o ID do cliente associado

) {
}
