package com.hospetagem.hotel.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import com.hospetagem.hotel.dto.ClienteDTO;
import com.hospetagem.hotel.dto.EnderecoDTO;
import com.hospetagem.hotel.mapper.ClienteMapper;
import com.hospetagem.hotel.mapper.EnderecoMapper;
import com.hospetagem.hotel.model.Endereco;
import com.hospetagem.hotel.model.enums.Role;
import com.hospetagem.hotel.repository.EnderecoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    EnderecoMapper enderecoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = ClienteMapper.toEntity(clienteDTO);
        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);
        cliente.setStatus(Cliente.Status.ATIVO);
        cliente.setRole(Role.ROLE_CLIENTE);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return ClienteMapper.toDTO(clienteSalvo);
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


    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado com o e-mail: " + email));
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

    public ClienteDTO adicionarEndereco(Long clienteId, EnderecoDTO enderecoDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Endereco endereco = EnderecoMapper.toEntity(enderecoDTO);
        endereco.setCliente(cliente);
        cliente.getEnderecos().add(endereco);

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return ClienteMapper.toDTO(clienteAtualizado);
    }

    public List<EnderecoDTO> getEnderecosByClienteId(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Usa o mapper para converter a lista de entidades para DTO
        return enderecoMapper.toDTOList(cliente.getEnderecos());
    }


    public ClienteDTO removerEndereco(Long clienteId, Long enderecoId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Endereco endereco = cliente.getEnderecos().stream()
                .filter(e -> e.getId_endereco() == enderecoId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        cliente.getEnderecos().remove(endereco);
        enderecoRepository.delete(endereco);

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return ClienteMapper.toDTO(clienteAtualizado);
    }

    public ClienteDTO atualizarEndereco(Long clienteId, Long enderecoId, @Valid EnderecoDTO enderecoDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Endereco enderecoExistente = cliente.getEnderecos().stream()
                .filter(e -> e.getId_endereco() == enderecoId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        enderecoExistente.setLogradouro(enderecoDTO.logradouro());
        enderecoExistente.setNumero(enderecoDTO.numero());
        enderecoExistente.setComplemento(enderecoDTO.complemento());
        enderecoExistente.setBairro(enderecoDTO.bairro());
        enderecoExistente.setCidade(enderecoDTO.cidade());
        enderecoExistente.setEstado(enderecoDTO.estado().toUpperCase());
        enderecoExistente.setCep(enderecoDTO.cep());

        clienteRepository.save(cliente);
        return ClienteMapper.toDTO(cliente);
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

    public boolean autenticarCliente(String email, String senha) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(email);

        if (clienteOpt.isEmpty()) {
            System.out.println("Cliente não encontrado com o email: " + email);
            return false; // Cliente não encontrado
        }

        Cliente cliente = clienteOpt.get();
        System.out.println("Cliente encontrado: " + cliente.getEmail());

        // Compara a senha
        boolean senhaValida = passwordEncoder.matches(senha, cliente.getSenha());
        System.out.println("Senha válida? " + senhaValida);

        return senhaValida;
    }

    // Envia o link de recuperação de senha
    public void enviarLinkRecuperacao(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail não encontrado"));



        String codigoRecuperacao = gerarCodigoRecuperacao();
        // Gera o código único
        cliente.setCodigoRecuperacao(codigoRecuperacao);
        cliente.setCodigoExpiracao(LocalDateTime.now().plusMinutes(15)); // 15 minutos de validade

        clienteRepository.save(cliente);



        emailService.enviarEmail(email, "Recuperação de senha",
                "Seu código de recuperação é: " + codigoRecuperacao);

    }

    private String gerarCodigoRecuperacao() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000); // Gera um número entre 100000 e 999999
        return String.valueOf(codigo);
    }


    // Redefine a senha baseada no código
    public void redefinirSenha(String codigoRecuperacao, String novaSenha) {
        Cliente cliente = clienteRepository.findByCodigoRecuperacao(codigoRecuperacao)
                .orElseThrow(() -> new RuntimeException("Código de recuperação inválido"));

        // Verifica se o código expirou
        if (cliente.getCodigoExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Código de recuperação expirou");
        }

        // Atualiza a senha e limpa os campos de recuperação
        cliente.setSenha(passwordEncoder.encode(novaSenha)); // Codifica a nova senha
        cliente.setCodigoRecuperacao(null);
        cliente.setCodigoExpiracao(null);

        clienteRepository.save(cliente);
    }




}