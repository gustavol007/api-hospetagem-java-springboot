package com.hospetagem.hotel.service;

import com.hospetagem.hotel.dto.CartaoDTO;
import com.hospetagem.hotel.mapper.CartaoMapper;
import com.hospetagem.hotel.model.Cartao;
import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.repository.CartaoRepository;
import com.hospetagem.hotel.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    // Serviço para criar um novo cartão
    public CartaoDTO criarCartao(CartaoDTO cartaoDTO) {
        // Buscar cliente pelo ID fornecido no DTO
        Cliente cliente = clienteRepository.findById(cartaoDTO.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Converter DTO para entidade
        Cartao cartao = CartaoMapper.toEntity(cartaoDTO, cliente);

        // Salvar no banco de dados
        cartao = cartaoRepository.save(cartao);

        // Retornar o DTO do cartão salvo
        return CartaoMapper.toDTO(cartao);
    }

    // Serviço para buscar todos os cartões
    public List<CartaoDTO> listarCartoes() {
        return cartaoRepository.findAll().stream()
                .map(CartaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Serviço para buscar cartão por ID
    public CartaoDTO buscarPorId(Long id) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        return CartaoMapper.toDTO(cartao);
    }

    // Serviço para atualizar um cartão existente
    public CartaoDTO atualizarCartao(Long id, CartaoDTO cartaoDTO) {
        if (cartaoDTO.clienteId() == null) {
            throw new IllegalArgumentException("O ID do cliente não pode ser nulo.");
        }

        // Buscar o cliente associado ao cartão
        Cliente cliente = clienteRepository.findById(cartaoDTO.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Buscar cartão no banco de dados
        Cartao cartaoExistente = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        // Atualizar os dados do cartão existente
        cartaoExistente.setNumeroCartao(cartaoDTO.numeroCartao());
        cartaoExistente.setNomeTitular(cartaoDTO.nomeTitular());
        cartaoExistente.setDataValidade(cartaoDTO.dataValidade());
        cartaoExistente.setTipoCartao(cartaoDTO.tipoCartao());
        cartaoExistente.setCliente(cliente);

        // Salvar a entidade atualizada no banco de dados
        cartaoExistente = cartaoRepository.save(cartaoExistente);

        return CartaoMapper.toDTO(cartaoExistente);
    }
    // Serviço para deletar um cartão pelo ID
    public void excluirCartao(Long id) {
        // Verificar se o cartão existe
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        // Deletar o cartão
        cartaoRepository.delete(cartao);
    }

}
