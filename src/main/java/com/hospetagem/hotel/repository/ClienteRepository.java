package com.hospetagem.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospetagem.hotel.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmailAndSenha(String email, String senha);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCodigoRecuperacao(String codigoRecuperacao);
}