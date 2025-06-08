package com.hospetagem.hotel.service;

import com.hospetagem.hotel.dto.ServicoDTO;
import com.hospetagem.hotel.model.Servico;
import com.hospetagem.hotel.model.enums.TipoServico;
import com.hospetagem.hotel.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public Servico criarServico(ServicoDTO servicoDTO) {
        Servico servico = new Servico();
        servico.setNome(servicoDTO.tipoServico() != null ? servicoDTO.tipoServico().getDescricao() : ""); // ou use setTipoServico se existir
        if (servicoDTO.preco() != null) {
            // Se o campo preco for BigDecimal no DTO, converta para Double
            servico.setPreco(servicoDTO.preco().doubleValue());
        }
        // Adicione os campos abaixo se existirem na entidade Servico
        // servico.setDescricao(servicoDTO.descricao());
        // servico.setObservacoes(servicoDTO.observacoes());
        return servicoRepository.save(servico);
    }

    public List<Servico> listarTodosServicos() {
        return servicoRepository.findAll();
    }

    public BigDecimal calcularPrecoTotal(List<Long> servicosIds) {
        List<Servico> servicos = servicoRepository.findAllById(servicosIds);
        // Converte Double para BigDecimal para somar corretamente
        return servicos.stream()
                .map(s -> s.getPreco() != null ? BigDecimal.valueOf(s.getPreco()) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}