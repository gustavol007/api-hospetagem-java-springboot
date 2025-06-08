package com.hospetagem.hotel.service;


import com.hospetagem.hotel.dto.PetDTO;
import com.hospetagem.hotel.mapper.PetMapper;
import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.model.Pet;
import com.hospetagem.hotel.model.Reserva;
import com.hospetagem.hotel.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import com.hospetagem.hotel.repository.ClienteRepository;
import com.hospetagem.hotel.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {


   @Autowired
   PetRepository petRepository;

   @Autowired
   ClienteRepository clienteRepository;

   @Autowired
   PetMapper petMapper;

   @Autowired
   ReservaRepository reservaRepository;

   @Transactional
   public PetDTO criarPet(Long clienteId, PetDTO petDTO) {
       Cliente cliente = clienteRepository.findById(clienteId)
               .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
       Pet pet = petMapper.toEntity(petDTO);
       pet.setCliente(cliente);
       Pet salvo = petRepository.save(pet);
       return petMapper.toDTO(salvo);
   }

   @Transactional(readOnly = true)
   public List<PetDTO> listarPetsDoCliente(Long clienteId) {
       Cliente cliente = clienteRepository.findById(clienteId)
               .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
       return cliente.getPets().stream()
               .map(petMapper::toDTO)
               .collect(Collectors.toList());
   }

   @Transactional(readOnly = true)
   public PetDTO buscarPorId(Long petId) {
       Pet pet = petRepository.findById(petId)
               .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
       return petMapper.toDTO(pet);
   }

   @Transactional
   public void deletarPet(Long petId) {
       petRepository.deleteById(petId);
   }

}