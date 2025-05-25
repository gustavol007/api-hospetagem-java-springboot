package com.hospetagem.hotel.model;

import com.hospetagem.hotel.model.enums.TipoCartao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCartao;

    private String nomeTitular;

    @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{4}$", message = "A data de validade deve estar no formato MM/aaaa.")
    private String dataValidade;


    private String codigoSeguranca;

    @Enumerated(EnumType.STRING)
    private TipoCartao tipoCartao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
