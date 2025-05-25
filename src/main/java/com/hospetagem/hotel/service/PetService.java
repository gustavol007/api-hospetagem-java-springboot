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

    public Pet criarPet(Pet pet) {
        pet.setReserva(null); // Garantir que o pet seja criado sem reserva
        return petRepository.save(pet);
    }

    public Optional<Pet> buscarPorId(Long id) {
        return petRepository.findById(id);
    }


    @Transactional
    public PetDTO adicionarPetAoCliente(Long clienteId, PetDTO petDTO) {
        // Busca o cliente no banco de dados
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Converte o DTO para entidade Pet
        Pet pet = petMapper.toEntity(petDTO);
        pet.setCliente(cliente); // Associa o cliente ao pet

        // Salva o pet no banco de dados
        Pet petSalvo = petRepository.save(pet);

        // Retorna o DTO do pet salvo
        return petMapper.toDTO(petSalvo);
    }

    @Transactional
    public void removerPet(Long petId) {
        // Busca o pet no banco de dados
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        // Remove o pet do banco de dados
        petRepository.delete(pet);
    }

    @Transactional(readOnly = true)
    public List<PetDTO> listarPetsDoCliente(Long clienteId) {
        // Busca o cliente no banco de dados
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Converte os pets do cliente para DTOs e retorna
        return cliente.getPets().stream()
                .map(petMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PetDTO atualizarPet (Long clienteId, PetDTO petDTO) {
        // Busca o cliente no banco de dados
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Busca o pet no banco de dados
        Pet petExistente = petRepository.findById(petDTO.id())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        // Verifica se o pet pertence ao cliente informado
        if (!petExistente.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("O pet não pertence ao cliente informado");
        }

        // Atualiza os dados do pet com os valores do DTO
        petExistente.setNome(petDTO.nome());
        petExistente.setIdade(petDTO.idade());
        petExistente.setPeso(petDTO.peso());
        petExistente.setRaca(petDTO.raca());
        petExistente.setSexo(petDTO.sexo());
        petExistente.setTemperamento(petDTO.temperamento());
        petExistente.setObservacaoPet(petDTO.observacaoPet());

        // Salva o pet atualizado no banco de dados
        Pet petAtualizado = petRepository.save(petExistente);

        // Converte a entidade atualizada para DTO e retorna
        return petMapper.toDTO(petAtualizado);
    }

    @Transactional
    public PetDTO atualizarReservaDoPet(Long petId, Long reservaId) {
        // Buscar o pet pelo ID
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        if (reservaId != null) {
            // Buscar a reserva pelo ID e vincular ao pet
            Reserva reserva = reservaRepository.findById(reservaId)
                    .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
            pet.setReserva(reserva);
        } else {
            // Se reservaId for nulo, desvincular a reserva do pet
            pet.setReserva(null);
        }

        Pet petAtualizado = petRepository.save(pet);
        return petMapper.toDTO(petAtualizado);
    }

    @Transactional
    public void removerPet(Long clienteId, Long petId) {
        // Busca o pet no banco de dados
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));

        // Verifica se o pet pertence ao cliente informado
        if (!pet.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("O pet não pertence ao cliente informado");
        }

        // Remove o pet do banco de dados
        petRepository.delete(pet);
    }

}