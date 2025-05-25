package com.hospetagem.hotel.service;

import com.hospetagem.hotel.dto.EnderecoDTO;
import com.hospetagem.hotel.dto.FuncionarioDTO;
import com.hospetagem.hotel.mapper.EnderecoMapper;
import com.hospetagem.hotel.mapper.FuncionarioMapper;
import com.hospetagem.hotel.model.Endereco;
import com.hospetagem.hotel.model.Funcionario;
import com.hospetagem.hotel.model.enums.Role;
import com.hospetagem.hotel.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Salvar um funcionário a partir de um DTO
    public FuncionarioDTO salvarFuncionario(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = FuncionarioMapper.toEntity(funcionarioDTO);
        funcionario.setStatus(Funcionario.Status.ATIVO);
        funcionario.setRole(Role.ROLE_FUNCIONARIO);
        funcionario.setSenha(passwordEncoder.encode(funcionario.getSenha()));
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
        return FuncionarioMapper.toDTO(funcionarioSalvo);
    }

    // Listar funcionários como DTOs
    public List<FuncionarioDTO> listarFuncionarios() {
        return funcionarioRepository.findAll().stream()
                .map(FuncionarioMapper::toDTO)
                .toList();
    }

    // Buscar funcionário por ID e retornar DTO
    public Optional<FuncionarioDTO> buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id)
                .map(FuncionarioMapper::toDTO);
    }

    // Atualizar funcionário a partir de um DTO
    public FuncionarioDTO atualizarFuncionario(Long id, FuncionarioDTO funcionarioDTO) {
        Optional<Funcionario> funcionarioExistente = funcionarioRepository.findById(id);
        if (funcionarioExistente.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado");
        }
        Funcionario funcionarioAtualizado = FuncionarioMapper.toEntity(funcionarioDTO);
        funcionarioAtualizado.setId(id); // Garante que estamos atualizando um funcionário existente
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionarioAtualizado);
        return FuncionarioMapper.toDTO(funcionarioSalvo);
    }

    // Método para deletar funcionário (mantém sem DTOs, pois apenas deleta)
    public void deletarFuncionario(Long id) {
        funcionarioRepository.deleteById(id);
    }

    // Alterar status do funcionário e retornar DTO
    public FuncionarioDTO alterarStatus(Long id) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if (optionalFuncionario.isEmpty()) {
            throw new RuntimeException("Funcionário não encontrado");
        }

        Funcionario funcionario = optionalFuncionario.get();
        funcionario.setStatus(funcionario.getStatus() == Funcionario.Status.ATIVO ? Funcionario.Status.INATIVO : Funcionario.Status.ATIVO);
        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionario);
        return FuncionarioMapper.toDTO(funcionarioAtualizado);
    }

    // Recupera o endereço do funcionário
    public EnderecoDTO getEnderecoByFuncionarioId(Long funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        if (funcionario.getEndereco() == null) {
            throw new RuntimeException("Funcionário não possui um endereço associado");
        }
        return enderecoMapper.toDTO(funcionario.getEndereco());
    }

    // Atualiza ou adiciona o endereço de um funcionário
    public EnderecoDTO salvarOuAtualizarEndereco(Long funcionarioId, EnderecoDTO enderecoDTO) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        Endereco enderecoAtualizado = enderecoMapper.toEntity(enderecoDTO);
        enderecoAtualizado.setFuncionario(funcionario); // Vincula o endereço ao funcionário
        funcionario.setEndereco(enderecoAtualizado);
        funcionarioRepository.save(funcionario);
        return enderecoMapper.toDTO(enderecoAtualizado);
    }

    public boolean autenticarFuncionario(String email, String senha) {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByEmail(email);
        if (funcionarioOpt.isEmpty()) {
            return false;
        }

        Funcionario funcionario = funcionarioOpt.get();
        boolean senhaValida = passwordEncoder.matches(senha, funcionario.getSenha());

        return senhaValida;
    }

    // Envia o link de recuperação de senha
    public void enviarLinkRecuperacao(String email) {
        Funcionario funcionario = funcionarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail não encontrado"));

        String codigoRecuperacao = UUID.randomUUID().toString(); // Gera o código único
        funcionario.setCodigoRecuperacao(codigoRecuperacao);
        funcionario.setCodigoExpiracao(LocalDateTime.now().plusMinutes(15)); // 15 minutos de validade

        funcionarioRepository.save(funcionario);

        // Monta e envia o e-mail com o link
        String linkRecuperacao = String.format("http://localhost:8080/api/funcionario/redefinir-senha?codigo=%s", codigoRecuperacao);
        String mensagem = "Clique no link abaixo para redefinir sua senha:\n" + linkRecuperacao;

        emailService.enviarEmail(funcionario.getEmail(), "Recuperação de Senha", mensagem);
    }

    // Redefine a senha baseada no código
    public void redefinirSenha(String codigoRecuperacao, String novaSenha) {
        Funcionario funcionario = funcionarioRepository.findByCodigoRecuperacao(codigoRecuperacao)
                .orElseThrow(() -> new RuntimeException("Código de recuperação inválido"));

        // Verifica se o código expirou
        if (funcionario.getCodigoExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Código de recuperação expirou");
        }

        // Atualiza a senha e limpa os campos de recuperação
        funcionario.setSenha(passwordEncoder.encode(novaSenha)); // Codifica a nova senha
        funcionario.setCodigoRecuperacao(null);
        funcionario.setCodigoExpiracao(null);

        funcionarioRepository.save(funcionario);
    }


}