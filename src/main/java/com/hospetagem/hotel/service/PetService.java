package com.hospetagem.hotel.service;

import com.hospetagem.hotel.dto.PetDTO;
import com.hospetagem.hotel.exceptions.ResourceNotFoundException;
import com.hospetagem.hotel.mapper.PetMapper;
import com.hospetagem.hotel.model.Pet;
import com.hospetagem.hotel.model.enums.PortePet;
import com.hospetagem.hotel.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional(readOnly = true)
    public List<PetDTO> findAll() {
        return petRepository.findAll()
                .stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PetDTO findById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com ID: " + id));
        return PetMapper.toDTO(pet);
    }

    @Transactional
    public PetDTO create(PetDTO petDTO) {
        Pet pet = PetMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        return PetMapper.toDTO(pet);
    }

    @Transactional
    public PetDTO update(Long id, PetDTO petDTO) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com ID: " + id));
        
        PetMapper.updateEntityFromDTO(petDTO, existingPet);
        Pet updatedPet = petRepository.save(existingPet);
        
        return PetMapper.toDTO(updatedPet);
    }

    @Transactional
    public void delete(Long id) {
        if (!petRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pet não encontrado com ID: " + id);
        }
        petRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PetDTO> findByNome(String nome) {
        return petRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PetDTO> findByEspecie(String especie) {
        return petRepository.findByEspecieIgnoreCase(especie)
                .stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PetDTO> findByRaca(String raca) {
        return petRepository.findByRacaIgnoreCase(raca)
                .stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PetDTO> findByPorte(PortePet porte) {
        return petRepository.findByPorte(porte)
                .stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }
}

