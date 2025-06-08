package com.hospetagem.hotel.mapper;

import com.hospetagem.hotel.dto.ReservaDTO;
import com.hospetagem.hotel.model.Reserva;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservaMapper {
    public ReservaDTO toDTO(Reserva reserva) {
        if (reserva == null) return null;
        return new ReservaDTO(
                reserva.getId(),
                reserva.getDataEntrada(),
                reserva.getDataSaida(),
                reserva.getHoraEntrada(),
                reserva.getHoraSaida(),
                reserva.getCliente() != null ? reserva.getCliente().getId() : null,
                reserva.getServicos() != null
                        ? reserva.getServicos().stream().map(s -> s.getId()).toList()
                        : List.of()
        );
    }

    public Reserva toEntity(ReservaDTO dto) {
        // O mapeamento dos pets e serviços deve ser feito no Service, pois precisa buscar pelo id
        Reserva reserva = new Reserva();
        reserva.setId(dto.id());
        reserva.setDataEntrada(dto.dataEntrada());
        reserva.setDataSaida(dto.dataSaida());
        reserva.setHoraEntrada(dto.horaEntrada());
        reserva.setHoraSaida(dto.horaSaida());
        return reserva;
    }
}