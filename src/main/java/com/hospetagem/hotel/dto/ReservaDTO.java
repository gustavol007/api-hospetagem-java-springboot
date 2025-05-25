package com.hospetagem.hotel.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaDTO(
        Long id,
        @NotNull
        LocalDate dataEntrada,
        @NotNull
        LocalDate dataSaida,
        @NotNull
        LocalTime horaEntrada,
        @NotNull
        LocalTime horaSaida,
        @NotNull
        Long clienteId


){}