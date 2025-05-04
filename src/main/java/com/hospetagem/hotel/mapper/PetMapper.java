package com.hospetagem.hotel.mapper;

import com.hospetagem.hotel.dto.PetDTO;
import com.hospetagem.hotel.model.Pet;
import com.hospetagem.hotel.model.enums.PortePet;
import com.hospetagem.hotel.model.enums.SexoPet;

public class PetMapper {

    public static PetDTO toDTO(Pet pet) {
        if (pet == null) {
            return null;
        }
        
        return new PetDTO(
                pet.getId(),
                pet.getNome(),
                pet.getIdade(),
                pet.getRaca(),
                pet.getEspecie(),
                pet.getSexo(),
                pet.getPorte()
        );
    }

    public static Pet toEntity(PetDTO petDTO) {
        if (petDTO == null) {
            return null;
        }
        
        Pet pet = new Pet();
        updateEntityFromDTO(petDTO, pet);
        return pet;
    }

    public static void updateEntityFromDTO(PetDTO petDTO, Pet pet) {
        if (petDTO == null || pet == null) {
            return;
        }
        
        if (petDTO.id() != null) {
            pet.setId(petDTO.id());
        }
        if (petDTO.nome() != null) {
            pet.setNome(petDTO.nome());
        }
        if (petDTO.idade() != null) {
            pet.setIdade(petDTO.idade());
        }
        if (petDTO.raca() != null) {
            pet.setRaca(petDTO.raca());
        }
        if (petDTO.especie() != null) {
            pet.setEspecie(petDTO.especie());
        }
        if (petDTO.sexo() != null) {
            pet.setSexo(petDTO.sexo());
        }
        if (petDTO.porte() != null) {
            pet.setPorte(petDTO.porte());
        }
    }
}