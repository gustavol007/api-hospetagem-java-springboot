package com.hospetagem.hotel.service;

import com.hospetagem.hotel.dto.ReservaDTO;
import com.hospetagem.hotel.mapper.ReservaMapper;
import com.hospetagem.hotel.model.*;
import com.hospetagem.hotel.model.enums.StatusReserva;
import com.hospetagem.hotel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private ServicoService servicoService;

    @Transactional
    public Reserva criarReserva(ReservaDTO reservaDTO, List<Long> petIds) {
        Cliente cliente = clienteRepository.findById(reservaDTO.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        List<Pet> pets = petIds != null && !petIds.isEmpty()
                ? petRepository.findAllById(petIds)
                : Collections.emptyList();

        if (petIds != null && pets.size() != petIds.size()) {
            throw new RuntimeException("Um ou mais pets não encontrados");
        }

        List<Long> servicosIds = reservaDTO.servicosIds() != null ? reservaDTO.servicosIds() : Collections.emptyList();
        List<Servico> servicos = !servicosIds.isEmpty()
                ? servicoRepository.findAllById(servicosIds)
                : Collections.emptyList();

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setPets(pets);
        reserva.setDataEntrada(reservaDTO.dataEntrada());
        reserva.setDataSaida(reservaDTO.dataSaida());
        reserva.setHoraEntrada(reservaDTO.horaEntrada());
        reserva.setHoraSaida(reservaDTO.horaSaida());
        reserva.setStatusReserva(StatusReserva.PENDENTE);
        reserva.setServicos(servicos);
        reserva.setPrecoTotal(servicoService.calcularPrecoTotal(servicosIds));

        return reservaRepository.save(reserva);
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> listarReservasDoCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return cliente.getReservas().stream()
                .map(reservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        // Em relacionamento ManyToMany, não é necessário setar pet.setReserva(null)
        reserva.setStatusReserva(StatusReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    @Transactional
    public void confirmarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        reserva.setStatusReserva(StatusReserva.CONFIRMADA);
        reservaRepository.save(reserva);
    }
}