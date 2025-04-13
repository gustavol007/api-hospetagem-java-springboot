package com.hospetagem.hotel.repository;

import com.hospetagem.hotel.model.Endereco;
import com.hospetagem.hotel.model.Funcionario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByEmailAndSenha(String email, String senha);

    Optional<Funcionario> findByEmail(String email);

    void save(Endereco enderecoFuncionario);
}
