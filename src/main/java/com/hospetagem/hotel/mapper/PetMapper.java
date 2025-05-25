package com.hospetagem.hotel.mapper;


import com.hospetagem.hotel.dto.PetDTO;
import com.hospetagem.hotel.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    // Converte de entidade Pet para DTO PetDTO
    public PetDTO toDTO(Pet pet) {
        if (pet == null) {
            return null;
        }
        return new PetDTO(
                pet.getId(),
                pet.getNome(),
                pet.getIdade(),
                pet.getRaca(),
                pet.getPeso(),
                pet.getTemperamento(),
                pet.getSexo(),
                pet.getObservacaoPet()
        );
    }

    // Converte de DTO PetDTO para entidade Pet
    public Pet toEntity(PetDTO petDTO) {
        if (petDTO == null) {
            return null;
        }
        Pet pet = new Pet();
        pet.setId(petDTO.id());
        pet.setNome(petDTO.nome());
        pet.setIdade(petDTO.idade());
        pet.setRaca(petDTO.raca());
        pet.setPeso(petDTO.peso());
        pet.setTemperamento(petDTO.temperamento());
        pet.setSexo(petDTO.sexo());
        pet.setObservacaoPet(petDTO.observacaoPet());
        return pet;
    }

}