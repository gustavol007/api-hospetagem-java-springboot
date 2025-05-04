package com.hospetagem.hotel.controllers;

import com.hospetagem.hotel.dto.PetDTO;
import com.hospetagem.hotel.model.enums.PortePet;
import com.hospetagem.hotel.service.PetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")

public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> findAll() {
        List<PetDTO> pets = petService.findAll();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> findById(@PathVariable Long id) {
        PetDTO pet = petService.findById(id);
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    public ResponseEntity<PetDTO> create(@RequestBody PetDTO petDTO) {
        PetDTO createdPet = petService.create(petDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> update(@PathVariable Long id, @RequestBody PetDTO petDTO) {
        PetDTO updatedPet = petService.update(id, petDTO);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<PetDTO>> findByNome(@PathVariable String nome) {
        List<PetDTO> pets = petService.findByNome(nome);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<PetDTO>> findByEspecie(@PathVariable String especie) {
        List<PetDTO> pets = petService.findByEspecie(especie);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/raca/{raca}")
    public ResponseEntity<List<PetDTO>> findByRaca(@PathVariable String raca) {
        List<PetDTO> pets = petService.findByRaca(raca);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/porte/{porte}")
    public ResponseEntity<List<PetDTO>> findByPorte(@PathVariable PortePet porte) {
        List<PetDTO> pets = petService.findByPorte(porte);
        return ResponseEntity.ok(pets);
    }
}