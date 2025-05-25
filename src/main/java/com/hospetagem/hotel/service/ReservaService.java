package com.hospetagem.hotel.service;

import com.hospetagem.hotel.dto.ReservaDTO;
import com.hospetagem.hotel.mapper.ReservaMapper;
import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.model.Pet;
import com.hospetagem.hotel.model.Reserva;
import com.hospetagem.hotel.model.enums.StatusReserva;
import com.hospetagem.hotel.repository.ClienteRepository;
import com.hospetagem.hotel.repository.PetRepository;
import com.hospetagem.hotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ReservaMapper reservaMapper;


    /**
     * Cria uma reserva vinculada a um cliente e aos seus pets
     */
    @Transactional
    public Reserva criarReserva(ReservaDTO reservaDTO, List<Long> petIds) {
        Cliente cliente = clienteRepository.findById(reservaDTO.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        List<Pet> pets = petRepository.findAllById(petIds);
        if (pets.size() != petIds.size()) {
            throw new RuntimeException("Um ou mais pets não encontrados");
        }

        // Validações de conflito de datas podem ser feitas aqui

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setPets(pets);
        reserva.setDataEntrada(reservaDTO.dataEntrada());
        reserva.setDataSaida(reservaDTO.dataSaida());
        reserva.setHoraEntrada(reservaDTO.horaEntrada());
        reserva.setHoraSaida(reservaDTO.horaSaida());
        reserva.setStatusReserva(StatusReserva.PENDENTE);

        return reservaRepository.save(reserva);
    }


    public Reserva adicionarPetsNaReserva(Long reservaId, List<Long> petIds) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        // Recuperar os Pets e associá-los à reserva
        List<Pet> pets = petRepository.findAllById(petIds);
        for (Pet pet : pets) {
            pet.setReserva(reserva); // Associar a reserva ao pet
        }

        reserva.getPets().addAll(pets); // Adicionar pets à lista já existente
        return reservaRepository.save(reserva);
    }

    /**
     * Lista todas as reservas de um cliente
     */
    @Transactional(readOnly = true)
    public List<ReservaDTO> listarReservasDoCliente(Long clienteId) {
        // Busca o cliente no banco de dados
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Retorna as reservas do cliente convertidas para DTO
        return cliente.getReservas().stream()
                .map(reservaMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza a informação de uma reserva existente
     */
    @Transactional
    public ReservaDTO atualizarReserva(Long reservaId, ReservaDTO reservaDTO) {
        // Busca a reserva no banco de dados
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        // Atualiza os dados da reserva
        reserva.setDataEntrada(reservaDTO.dataEntrada());
        reserva.setDataSaida(reservaDTO.dataSaida());
        reserva.setHoraEntrada(reservaDTO.horaEntrada());
        reserva.setHoraSaida(reservaDTO.horaSaida());

        // Salva a reserva atualizada
        Reserva reservaAtualizada = reservaRepository.save(reserva);

        // Retorna a reserva atualizada convertida para DTO
        return reservaMapper.toDTO(reservaAtualizada);
    }

    /**
     * Remove uma reserva pelo ID
     */
    @Transactional
    public void cancelarReserva(Long reservaId) {
        // Busca a reserva no banco de dados
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        // Remove a associação dos pets à reserva
        List<Pet> pets = reserva.getPets();
        pets.forEach(pet -> pet.setReserva(null));
        petRepository.saveAll(pets);

        // Remove a reserva do banco de dados

        reserva.setStatusReserva(StatusReserva.CANCELADA);
    }

    @Transactional
    public void confirmarReserva(Long reservaId) {
        // Busca a reserva no banco de dados
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));


        reserva.setStatusReserva(StatusReserva.CONFIRMADA);
    }

}