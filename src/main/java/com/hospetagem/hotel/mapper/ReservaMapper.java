package com.hospetagem.hotel.mapper;

import com.hospetagem.hotel.dto.ReservaDTO;
import com.hospetagem.hotel.model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {
    public ReservaDTO toDTO(Reserva reserva) {
        if (reserva == null) {
            return null;
        }
        return new ReservaDTO(
                reserva.getId(),
                reserva.getDataEntrada(),
                reserva.getDataSaida(),
                reserva.getHoraEntrada(),
                reserva.getHoraSaida(),
                reserva.getId()
        );
    }

    public Reserva toEntity(ReservaDTO reservaDTO) {
        if (reservaDTO == null) {
            return null;
        }
        Reserva reserva = new Reserva();
        reserva.setId(reservaDTO.id());
        reserva.setDataEntrada(reservaDTO.dataEntrada());
        reserva.setDataSaida(reservaDTO.dataSaida());
        reserva.setHoraEntrada(reservaDTO.horaEntrada());
        reserva.setHoraSaida(reservaDTO.horaSaida());
        return reserva;
    }

}