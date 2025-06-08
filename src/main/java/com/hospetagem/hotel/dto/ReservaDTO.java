package com.hospetagem.hotel.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservaDTO(
        Long id,
        LocalDate dataEntrada,
        LocalDate dataSaida,
        LocalTime horaEntrada,
        LocalTime horaSaida,
        Long clienteId,
        List<Long> servicosIds
) {}