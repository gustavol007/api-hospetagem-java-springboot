package com.hospetagem.hotel.service;

import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.model.Funcionario;
import com.hospetagem.hotel.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario salvarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    public Funcionario atualizarFuncionario(Long id, Funcionario funcionarioAtualizado) {
        return funcionarioRepository.findById(id).map(funcionario -> {
            funcionario.setName(funcionarioAtualizado.getName());
            funcionario.setEmail(funcionarioAtualizado.getEmail());
            funcionario.setSenha(funcionarioAtualizado.getSenha());
            funcionario.setCpf(funcionarioAtualizado.getCpf());
            funcionario.setData_nascimento(funcionarioAtualizado.getData_nascimento());
            funcionario.setSexo(funcionarioAtualizado.getSexo());
            funcionario.setCargo(funcionarioAtualizado.getCargo());
            funcionario.setSalario(funcionarioAtualizado.getSalario());
            return funcionarioRepository.save(funcionario);
        }).orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }

    public void deletarFuncionario(Long id) {
        funcionarioRepository.deleteById(id);
    }

    public Funcionario alterarStatus(Long id){
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);

        if (funcionarioOptional.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }

        Funcionario funcionario = funcionarioOptional.get();

        // Alterna o status
        if (funcionario.getStatus() == Cliente.Status.ATIVO) {
            funcionario.setStatus(Cliente.Status.INATIVO);
        } else {
            funcionario.setStatus(Cliente.Status.ATIVO);
        }

        // Salva a alteração no banco de dados
        return funcionarioRepository.save(funcionario);
    }
}

