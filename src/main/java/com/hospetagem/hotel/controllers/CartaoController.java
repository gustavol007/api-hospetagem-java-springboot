package com.hospetagem.hotel.controllers;

import com.hospetagem.hotel.dto.CartaoDTO;
import com.hospetagem.hotel.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cartao/")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<CartaoDTO> criarCartao(@PathVariable Long clienteId, @RequestBody CartaoDTO cartaoDTO) {
        // Define o clienteId no objeto CartaoDTO
        CartaoDTO novoCartao = cartaoService.criarCartao(new CartaoDTO(
                cartaoDTO.id(),
                cartaoDTO.numeroCartao(),
                cartaoDTO.nomeTitular(),
                cartaoDTO.dataValidade(),
                cartaoDTO.tipoCartao(),
                cartaoDTO.codigoSeguranca(),
                clienteId // Passa o clienteId da URL para o DTO
        ));
        return ResponseEntity.ok(novoCartao);
    }

    // Endpoint para listar todos os cartões
    @GetMapping
    public ResponseEntity<List<CartaoDTO>> listarCartoes() {
        List<CartaoDTO> cartoes = cartaoService.listarCartoes();
        return ResponseEntity.ok(cartoes);
    }

    // Endpoint para buscar um cartão por ID
    @GetMapping("/{id}")
    public ResponseEntity<CartaoDTO> buscarPorId(@PathVariable Long id) {
        CartaoDTO cartao = cartaoService.buscarPorId(id);
        return ResponseEntity.ok(cartao);
    }

    // Endpoint para atualizar um cartão existente
    @PutMapping("/{id}/cliente/{clienteId}")
    public ResponseEntity<CartaoDTO> atualizarCartao(
            @PathVariable Long id,
            @PathVariable Long clienteId,
            @RequestBody CartaoDTO cartaoDTO) {

        // Define o clienteId no objeto DTO
        CartaoDTO atualizadoCartao = cartaoService.atualizarCartao(id, new CartaoDTO(
                cartaoDTO.id(),
                cartaoDTO.numeroCartao(),
                cartaoDTO.nomeTitular(),
                cartaoDTO.dataValidade(),
                cartaoDTO.tipoCartao(),
                cartaoDTO.codigoSeguranca(),
                clienteId));
        return ResponseEntity.ok(atualizadoCartao);
    }

    // Endpoint para excluir um cartão por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCartao(@PathVariable Long id) {
        cartaoService.excluirCartao(id);
        return ResponseEntity.noContent().build();
    }

}
