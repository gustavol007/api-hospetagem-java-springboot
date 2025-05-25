package com.hospetagem.hotel.controllers;


import com.hospetagem.hotel.dto.PetDTO;
import com.hospetagem.hotel.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    PetService petService;

    // Adiciona um pet a um cliente específico
    @PostMapping("/{clienteId}")
    public ResponseEntity<PetDTO> adicionarPetAoCliente(
            @PathVariable Long clienteId,
            @Validated @RequestBody PetDTO petDTO) {
        PetDTO petSalvo = petService.adicionarPetAoCliente(clienteId, petDTO);
        return ResponseEntity.ok(petSalvo);
    }

    // Lista todos os pets de um cliente específico
    @GetMapping("/{clienteId}")
    public ResponseEntity<List<PetDTO>> listarPetsDoCliente(@PathVariable Long clienteId) {
        List<PetDTO> pets = petService.listarPetsDoCliente(clienteId);
        return ResponseEntity.ok(pets);
    }

    // Atualiza um pet de um cliente específico
    @PutMapping("/{clienteId}")
    public ResponseEntity<PetDTO> atualizarPet(
            @PathVariable Long clienteId,
            @Validated @RequestBody PetDTO petDTO) {
        PetDTO petAtualizado = petService.atualizarPet(clienteId, petDTO);
        return ResponseEntity.ok(petAtualizado);
    }

    // Remove um pet pelo ID
    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> removerPet(@PathVariable Long petId) {
        petService.removerPet(petId);
        return ResponseEntity.noContent().build();
    }

    // Remove um pet de um cliente específico
    @DeleteMapping("/{clienteId}/{petId}")
    public ResponseEntity<Void> removerPetDoCliente(
            @PathVariable Long clienteId,
            @PathVariable Long petId) {
        petService.removerPet(clienteId, petId);
        return ResponseEntity.noContent().build();
    }
}