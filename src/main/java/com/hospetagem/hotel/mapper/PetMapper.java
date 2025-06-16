package com.hospetagem.hotel.mapper;


import com.hospetagem.hotel.dto.PetDTO;
import com.hospetagem.hotel.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    public PetDTO toDTO(Pet pet) {
        if (pet == null) return null;
        return new PetDTO(
                pet.getId(),
                pet.getNome(),
                pet.getIdade(),
                pet.getEspecie(),
                pet.getPorte(),
                pet.getRaca(),
                pet.getPeso(),
                pet.getTemperamento(),
                pet.getSexo(),
                pet.getCastrado(),
                pet.getHistoricoDoencaAlergia(),
                pet.getConvivenciaAnimais(),
                pet.getContato_emergencia(),
                pet.getObservacaoPet()
        );
    }

    public Pet toEntity(PetDTO dto) {
        if (dto == null) return null;
        Pet pet = new Pet();
        pet.setId(dto.id());
        pet.setNome(dto.nome());
        pet.setIdade(dto.idade());
        pet.setEspecie(dto.especie());
        pet.setPorte(dto.porte());
        pet.setRaca(dto.raca());
        pet.setPeso(dto.peso());
        pet.setTemperamento(dto.temperamento());
        pet.setSexo(dto.sexo());
        pet.setCastrado(dto.castrado());
        pet.setHistoricoDoencaAlergia(dto.historicoDoencaAlergia());
        pet.setConvivenciaAnimais(dto.convivenciaAnimais());
        pet.setContato_emergencia(dto.contato_emergencia());
        pet.setObservacaoPet(dto.observacaoPet());
        return pet;
    }

}