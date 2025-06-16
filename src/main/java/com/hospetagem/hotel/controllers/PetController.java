package com.hospetagem.hotel.controllers;


import com.hospetagem.hotel.dto.PetDTO;
import com.hospetagem.hotel.service.PetService;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    PetService petService;
    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<PetDTO> criarPet(@PathVariable Long clienteId, @RequestBody @Validated PetDTO petDTO) {
        PetDTO dto = petService.criarPet(clienteId, petDTO);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PetDTO>> listarPetsDoCliente(@PathVariable Long clienteId) {
        List<PetDTO> pets = petService.listarPetsDoCliente(clienteId);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> buscarPorId(@PathVariable Long petId) {
        PetDTO pet = petService.buscarPorId(petId);
        return ResponseEntity.ok(pet);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletarPet(@PathVariable Long petId) {
        petService.deletarPet(petId);
        return ResponseEntity.noContent().build();
    }
}