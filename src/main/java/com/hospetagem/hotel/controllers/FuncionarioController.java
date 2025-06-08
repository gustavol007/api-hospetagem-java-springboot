package com.hospetagem.hotel.controllers;

import com.hospetagem.hotel.dto.EnderecoDTO;
import com.hospetagem.hotel.dto.FuncionarioDTO;
import com.hospetagem.hotel.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    // Criar um novo funcionário a partir de um DTO
    @PostMapping(value = "/criarFuncionario", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FuncionarioDTO> criarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
        FuncionarioDTO novoFuncionario = funcionarioService.salvarFuncionario(funcionarioDTO);
        return ResponseEntity.status(201).body(novoFuncionario);
    }

    // Listar todos os funcionários como DTOs
    @GetMapping(value = "/listarFuncionarios", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FuncionarioDTO>> listarFuncionarios() {
        return ResponseEntity.ok(funcionarioService.listarFuncionarios());
    }

    // Consultar funcionário por ID e retornar DTO
    @GetMapping("consultarFuncionarioPorId/{id}")
    public ResponseEntity<FuncionarioDTO> buscarFuncionarioPorId(@PathVariable Long id) {
        Optional<FuncionarioDTO> funcionarioDTO = funcionarioService.buscarFuncionarioPorId(id);
        return funcionarioDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar um funcionário a partir de um DTO
    @PutMapping("atualizarFuncionario/{id}")
    public ResponseEntity<FuncionarioDTO> atualizarFuncionario(
            @PathVariable Long id,
            @RequestBody FuncionarioDTO funcionarioDTO
    ) {
        try {
            FuncionarioDTO funcionarioAtualizado = funcionarioService.atualizarFuncionario(id, funcionarioDTO);
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar um funcionário (mantém sem DTO, pois é apenas uma ação de exclusão)
    @DeleteMapping("deletarFuncionario/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
        funcionarioService.deletarFuncionario(id);
        return ResponseEntity.noContent().build();
    }

    // Alterar o status de um funcionário (ATIVO para INATIVO e vice-versa)
    @PutMapping("alterarStatus/{id}")
    public ResponseEntity<FuncionarioDTO> alterarStatus(@PathVariable Long id) {
        FuncionarioDTO funcionarioAtualizado = funcionarioService.alterarStatus(id);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    // Retorna o endereço de um funcionário
    @GetMapping("/{id}/endereco")
    public ResponseEntity<EnderecoDTO> getEnderecoByFuncionarioId(@PathVariable Long id) {
        try {
            EnderecoDTO endereco = funcionarioService.getEnderecoByFuncionarioId(id);
            return ResponseEntity.ok(endereco);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualiza ou adiciona o endereço de um funcionário
    @PutMapping("/{id}/endereco")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        try {
            EnderecoDTO enderecoAtualizado = funcionarioService.salvarOuAtualizarEndereco(id, enderecoDTO);
            return ResponseEntity.ok(enderecoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para solicitar recuperação de senha
    @PreAuthorize("hasRole('FUNCIONARIO')")
    @PostMapping("/esquecer-senha")
    public ResponseEntity<String> esquecerSenha(@RequestParam String email) {
        funcionarioService.enviarLinkRecuperacao(email);
        return ResponseEntity.ok("Link de recuperação enviado para o e-mail.");
    }

    // Endpoint para redefinir senha
    @PreAuthorize("hasRole('FUNCIONARIO')")
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(
            @RequestParam String codigo,
            @RequestParam String novaSenha) {
        funcionarioService.redefinirSenha(codigo, novaSenha);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }


}