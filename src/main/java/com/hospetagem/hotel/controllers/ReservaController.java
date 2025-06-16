package com.hospetagem.hotel.controllers;

import com.hospetagem.hotel.dto.ReservaDTO;
import com.hospetagem.hotel.mapper.ReservaMapper;
import com.hospetagem.hotel.model.Reserva;
import com.hospetagem.hotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaMapper reservaMapper;

    @PostMapping(consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<ReservaDTO> criarReserva(
            @RequestBody @Validated ReservaDTO reservaDTO) {

        Reserva novaReserva = reservaService.criarReserva(reservaDTO, reservaDTO.petsIds());
        ReservaDTO dto = reservaMapper.toDTO(novaReserva);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<ReservaDTO>> listarReservasDoCliente(@PathVariable Long clienteId) {
        List<ReservaDTO> reservas = reservaService.listarReservasDoCliente(clienteId);
        return ResponseEntity.ok(reservas);
    }

    @PostMapping("/confirmar/{reservaId}")
    public ResponseEntity<Void> confirmarReserva(@PathVariable Long reservaId) {
        reservaService.confirmarReserva(reservaId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reservaId}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long reservaId) {
        reservaService.cancelarReserva(reservaId);
        return ResponseEntity.noContent().build();
    }
}