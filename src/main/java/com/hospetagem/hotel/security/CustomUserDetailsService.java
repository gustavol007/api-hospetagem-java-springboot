package com.hospetagem.hotel.security;

import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.model.Funcionario;
import com.hospetagem.hotel.repository.ClienteRepository;
import com.hospetagem.hotel.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Tenta buscar como funcionário
        Funcionario funcionario = funcionarioRepository.findByEmail(email).orElse(null);
        if (funcionario != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority(funcionario.getRole().name());
            return new User(funcionario.getEmail(), funcionario.getSenha(), Collections.singleton(authority));
        }

        // Tenta buscar como cliente
        Cliente cliente = clienteRepository.findByEmail(email).orElse(null);
        if (cliente != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority(cliente.getRole().name());
            return new User(cliente.getEmail(), cliente.getSenha(), Collections.singleton(authority));
        }

        throw new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email);
    }
}