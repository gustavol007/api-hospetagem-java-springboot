package com.hospetagem.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Cliente cliente) {
        Cliente existingCliente = clienteRepository.findById(cliente.getId()).orElseThrow();
        existingCliente.setName(cliente.getName());
        existingCliente.setEmail(cliente.getEmail());
        existingCliente.setSenha(cliente.getSenha());
        existingCliente.setNumero(cliente.getNumero());
        existingCliente.setData_nascimento(cliente.getData_nascimento());
        existingCliente.setSexo(cliente.getSexo());
        return clienteRepository.save(existingCliente);
    }

    public Cliente deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        clienteRepository.delete(cliente);
        return cliente;
    }

    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id).orElseThrow();
    }

    public Cliente save(Cliente updateCliente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public Cliente login(String email, String senha) {
        return clienteRepository.findByEmailAndSenha(email, senha)
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos"));
    }
}