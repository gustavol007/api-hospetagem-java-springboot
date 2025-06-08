package com.hospetagem.hotel.dto;

import com.hospetagem.hotel.model.enums.TipoServico;
import java.math.BigDecimal;

public record ServicoDTO(
       Long id,
       TipoServico tipoServico,
       BigDecimal preco,
       String descricao,
       String observacoes
) {}