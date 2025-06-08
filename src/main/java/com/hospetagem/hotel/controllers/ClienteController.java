package com.hospetagem.hotel.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hospetagem.hotel.dto.ClienteDTO;
import com.hospetagem.hotel.dto.EnderecoDTO;
import com.hospetagem.hotel.mapper.ClienteMapper;
import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.service.ClienteService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:5501", allowCredentials = "true")
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Retorna todos os clientes como uma lista de ClienteDTO.
     */
//    @PreAuthorize("hasRole('FUNCIONARIO')")
    @GetMapping(value = "/lista", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        List<ClienteDTO> clienteDTOs = clientes.stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clienteDTOs);
    }


    // Exemplo de endpoint para retornar o usuário autenticado
    @GetMapping("/me")
    public ResponseEntity<?> getClienteLogado(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Não autenticado");
        }
        String email = authentication.getName();
        Cliente cliente = clienteService.buscarPorEmail(email);
        if (cliente == null) {
            return ResponseEntity.status(404).body("Cliente não encontrado");
        }
        // Retorne os dados necessários
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", cliente.getName());
        dados.put("cpf", cliente.getCpf());
        dados.put("email", cliente.getEmail());
        dados.put("telefone", cliente.getTelefone());
        dados.put("data_nascimento", cliente.getData_nascimento());
        dados.put("sexo", cliente.getSexo());
        // ...adicione outros campos conforme necessário
        return ResponseEntity.ok(dados);
    }
    /**
     * Cria um novo cliente a partir de um ClienteDTO.
     */
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        ClienteDTO clienteCriado = clienteService.createCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }


    /**
     * Atualiza informações de um cliente existente.
     */

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> updateCliente(
            @PathVariable Long id,
            @RequestBody @Valid ClienteDTO clienteDTO) {
        Cliente cliente = ClienteMapper.toEntity(clienteDTO);
        cliente.setId(id); // Garante que o ID está sendo atualizado corretamente
        Cliente clienteAtualizado = clienteService.updateCliente(cliente);
        return ResponseEntity.ok(ClienteMapper.toDTO(clienteAtualizado));
    }

    /**
     * Exclui um cliente pelo seu ID.
     */
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Consulta um cliente específico pelo seu ID.
     */
//    @PreAuthorize("hasRole('FUNCIONARIO')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> consultarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(ClienteMapper.toDTO(cliente));
    }

    /**
     * Retorna os endereços do cliente
     */

    @GetMapping(value = "/{id}/enderecos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EnderecoDTO>> getEnderecosByClienteId(@PathVariable Long id) {
        List<EnderecoDTO> enderecos = clienteService.getEnderecosByClienteId(id);
        return ResponseEntity.ok(enderecos);
    }


    /**
     * Atualiza o status de um cliente (Ativo/Inativo).
     */
    @PutMapping(value = "/alterarStatus/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> alterarStatus(@PathVariable Long id) {
        Cliente clienteAtualizado = clienteService.alterarStatus(id);
        return ResponseEntity.ok(clienteAtualizado);
    }

    /**
     * Adiciona um endereço a um cliente específico.
     */
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping(value = "/{id}/enderecos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> adicionarEndereco(
            @PathVariable Long id,
            @RequestBody @Valid EnderecoDTO enderecoDTO) {
        ClienteDTO clienteDTO = clienteService.adicionarEndereco(id, enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDTO);
    }

    /**
     * Remove um endereço específico de um cliente.
     */
    @DeleteMapping(value = "/{id}/enderecos/{enderecoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> removerEndereco(
            @PathVariable Long id,
            @PathVariable Long enderecoId) {
        ClienteDTO clienteDTO = clienteService.removerEndereco(id, enderecoId);
        return ResponseEntity.ok(clienteDTO);
    }

    /**
     * Atualiza um endereço específico de um cliente.
     */
    @PutMapping(value = "/{id}/enderecos/{enderecoId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> atualizarEndereco(
            @PathVariable Long id,
            @PathVariable Long enderecoId,
            @RequestBody @Valid EnderecoDTO enderecoAtualizadoDTO) {
        ClienteDTO clienteDTO = clienteService.atualizarEndereco(id, enderecoId, enderecoAtualizadoDTO);
        return ResponseEntity.ok(clienteDTO);
    }

    // Endpoint para solicitar recuperação de senha
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/esquecer-senha")
    public ResponseEntity<String> esquecerSenha(@RequestParam String email) {
        clienteService.enviarLinkRecuperacao(email);
        return ResponseEntity.ok("Link de recuperação enviado para o e-mail.");
    }

    // Endpoint para redefinir senha

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(
            @RequestParam String codigo,
            @RequestParam String novaSenha) {
        clienteService.redefinirSenha(codigo, novaSenha);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }

}