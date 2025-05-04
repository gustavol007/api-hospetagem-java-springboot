package com.hospetagem.hotel.repository;

import com.hospetagem.hotel.model.Pet;
import com.hospetagem.hotel.model.enums.PortePet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNomeContainingIgnoreCase(String nome);
    List<Pet> findByEspecieIgnoreCase(String especie);
    List<Pet> findByRacaIgnoreCase(String raca);
    List<Pet> findByPorte(PortePet porte);
}