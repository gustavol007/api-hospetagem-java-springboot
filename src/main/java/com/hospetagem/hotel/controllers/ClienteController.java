package com.hospetagem.hotel.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.hospetagem.hotel.dto.ClienteDTO;
import com.hospetagem.hotel.dto.EnderecoDTO;
import com.hospetagem.hotel.dto.LoginDTO;
import com.hospetagem.hotel.mapper.ClienteMapper;
import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.model.Endereco;
import com.hospetagem.hotel.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Retorna todos os clientes como uma lista de ClienteDTO.
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        List<ClienteDTO> clienteDTOs = clientes.stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clienteDTOs);
    }

    /**
     * Cria um novo cliente a partir de um ClienteDTO.
     */
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> createNewCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        // Mapeia o DTO para entidade
        Cliente cliente = ClienteMapper.toEntity(clienteDTO);

        // Cria o cliente no serviço
        Cliente clienteSalvo = clienteService.createCliente(cliente);

        // Retorna o DTO do cliente criado
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.toDTO(clienteSalvo));
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
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> consultarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(ClienteMapper.toDTO(cliente));
    }

    /**
     * Login de um cliente usando email e senha.
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        Cliente cliente = clienteService.login(loginDTO.email(), loginDTO.senha());
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
}