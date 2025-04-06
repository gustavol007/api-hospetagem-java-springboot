package com.hospetagem.hotel.service;

import java.util.List;
import java.util.Optional;

import com.hospetagem.hotel.model.Endereco;
import com.hospetagem.hotel.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

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
        existingCliente.setTelefone(cliente.getTelefone());
        existingCliente.setCpf(cliente.getCpf());
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

    // Método para alterar Status
    public Cliente alterarStatus(Long id) {
        // Busca o cliente pelo ID
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }

        Cliente cliente = clienteOptional.get();

        // Alterna o status
        if (cliente.getStatus() == Cliente.Status.ATIVO) {
            cliente.setStatus(Cliente.Status.INATIVO);
        } else {
            cliente.setStatus(Cliente.Status.ATIVO);
        }

        // Salva a alteração no banco de dados
        return clienteRepository.save(cliente);
    }

    public Cliente adicionarEnderco(Long clienteId, Endereco endereco){
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        endereco.setCliente(cliente);
        enderecoRepository.save(endereco);
        return cliente;
    }
    public Cliente removerEndereco(Long clienteId, Long enderecoId){
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        Endereco endereco = enderecoRepository.findById(enderecoId).orElseThrow();
        endereco.setCliente(null);
        return cliente;
    }

    public Cliente atualizarEndereco(Long idCliente, Long idEndereco, Endereco novoEndereco) {
        Cliente cliente = buscarClientePorId(idCliente);

        // Procurar o endereço a ser atualizado na lista de endereços do cliente
        Endereco enderecoExistente = cliente.getEnderecos().stream()
                .filter(endereco -> endereco.getId_endereco() == idEndereco)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com ID: " + idEndereco));

        // Atualizar os campos do endereço existente com os dados do novo endereço
        atualizarCamposEndereco(enderecoExistente, novoEndereco);

        // Persistir as alterações e retornar o cliente atualizado
        return clienteRepository.save(cliente);
    }

    private Cliente buscarClientePorId(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + idCliente));
    }

    private void atualizarCamposEndereco(Endereco enderecoExistente, Endereco novoEndereco) {
        enderecoExistente.setLogradouro(novoEndereco.getLogradouro());
        enderecoExistente.setNumero(novoEndereco.getNumero());
        enderecoExistente.setBairro(novoEndereco.getBairro());
        enderecoExistente.setComplemento(novoEndereco.getComplemento());
        enderecoExistente.setCidade(novoEndereco.getCidade());
        enderecoExistente.setEstado(novoEndereco.getEstado());
        enderecoExistente.setCep(novoEndereco.getCep());
    }

}