package com.hospetagem.hotel.service;

import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.model.Endereco;
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

    public Funcionario login(String email, String senha) {
        return funcionarioRepository.findByEmailAndSenha(email, senha)
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));
    }

    public Funcionario adicionarEndereco(Long id, Endereco endereco) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
    
        if (funcionarioOptional.isPresent()) {
            Funcionario funcionarioExistente = funcionarioOptional.get();
    
            // Associa o endereço ao funcionário
            endereco.setFuncionario(funcionarioExistente);
            funcionarioExistente.setEndereco(endereco);
    
            // Salva o funcionário com o endereço associado
            return funcionarioRepository.save(funcionarioExistente);
        } else {
            throw new RuntimeException("Funcionário não encontrado com ID: " + id);
        }
    }

    public Funcionario removerEndereco(Long id, Long enderecoId) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            Funcionario funcionarioExistente = funcionarioOptional.get();
            if (funcionarioExistente.getEndereco() != null && funcionarioExistente.getEndereco().getId_endereco() == enderecoId) {
                funcionarioExistente.setEndereco(null);
                return funcionarioRepository.save(funcionarioExistente);
            } else {
                throw new RuntimeException("Endereço não encontrado para o funcionário com ID: " + id);
            }
        } else {
            throw new RuntimeException("Funcionário não encontrado com ID: " + id);
        }
    }

    public Funcionario atualizarEndereco(Long idFuncionario, Long idEndereco, Endereco novoEndereco) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(idFuncionario);
    
        if (funcionarioOptional.isPresent()) {
            Funcionario funcionarioExistente = funcionarioOptional.get();
            Endereco enderecoExistente = funcionarioExistente.getEndereco();
    
            if (enderecoExistente != null && enderecoExistente.getId_endereco() == idEndereco) {
                // Atualiza os campos do endereço existente
                if (novoEndereco.getLogradouro() != null) enderecoExistente.setLogradouro(novoEndereco.getLogradouro());
                if (novoEndereco.getNumero() != null) enderecoExistente.setNumero(novoEndereco.getNumero());
                if (novoEndereco.getBairro() != null) enderecoExistente.setBairro(novoEndereco.getBairro());
                if (novoEndereco.getComplemento() != null) enderecoExistente.setComplemento(novoEndereco.getComplemento());
                if (novoEndereco.getCidade() != null) enderecoExistente.setCidade(novoEndereco.getCidade());
                if (novoEndereco.getEstado() != null) enderecoExistente.setEstado(novoEndereco.getEstado());
                if (novoEndereco.getCep() != null) enderecoExistente.setCep(novoEndereco.getCep());
    
                return funcionarioRepository.save(funcionarioExistente);
            } else {
                throw new RuntimeException("Endereço não encontrado para o funcionário com ID: " + idFuncionario);
            }
        } else {
            throw new RuntimeException("Funcionário não encontrado com ID: " + idFuncionario);
        }
    }
    
    public Funcionario buscarFuncionarioPorEmail(String email) {
        return funcionarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o email: " + email));
    }

}

