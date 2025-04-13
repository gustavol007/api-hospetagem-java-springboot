package com.hospetagem.hotel.controllers;

import com.hospetagem.hotel.model.Endereco;
import com.hospetagem.hotel.model.Funcionario;
import com.hospetagem.hotel.service.FuncionarioService;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;
    
    @PostMapping(value = "/criarFuncionario")
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody Funcionario funcionario) {
        Funcionario novoFuncionario = funcionarioService.salvarFuncionario(funcionario);
        return ResponseEntity.ok(novoFuncionario);
    }

    @GetMapping(value = "/listarFuncionarios")
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {
        return ResponseEntity.ok(funcionarioService.listarFuncionarios());
    }

    @GetMapping("consultarFuncionarioPorId/{id}")
    public ResponseEntity<Funcionario> buscarFuncionarioPorId(@PathVariable Long id) {
        Optional<Funcionario> funcionario = funcionarioService.buscarFuncionarioPorId(id);
        return funcionario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("atualizarFuncionario/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        try {
            Funcionario funcionarioAtualizado = funcionarioService.atualizarFuncionario(id, funcionario);
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("deletarFuncionario/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
        funcionarioService.deletarFuncionario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("alterarStatus/{id}")
    public ResponseEntity<Funcionario> alterarStatus(@PathVariable Long id){
        Funcionario funcionarioAtualizado = funcionarioService.alterarStatus(id);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    // Endpoint para adicionar endereço ao funcionário
    @PostMapping("/endereco/{id}")
    public ResponseEntity<Funcionario> adicionarEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
        Funcionario funcionario = funcionarioService.adicionarEndereco(id, endereco);
        return ResponseEntity.ok(funcionario);
    }

    // Endpoint para remover endereço do funcionário
    @DeleteMapping(value = "/{id}/endereco/{enderecoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Funcionario removerEndereco(@PathVariable Long id, @PathVariable Long enderecoId) {
        return funcionarioService.removerEndereco(id, enderecoId);
    }

    // Endpoint para atualizar endereço do funcionário
    @PutMapping(value = "/{id}/endereco/{enderecoId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Funcionario atualizarEndereco(@PathVariable Long id, @PathVariable Long enderecoId, @RequestBody Endereco novoEndereco) {
        return funcionarioService.atualizarEndereco(id, enderecoId, novoEndereco);
    }

    // Endpoint para buscar funcionário por email
    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Funcionario buscarFuncionarioPorEmail(@PathVariable String email) {
        return funcionarioService.buscarFuncionarioPorEmail(email);
    }
}

