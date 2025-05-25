package com.hospetagem.hotel.repository;

import com.hospetagem.hotel.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
