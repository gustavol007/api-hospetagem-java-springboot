package com.hospetagem.hotel.dto;


import com.hospetagem.hotel.model.enums.PortePet;
import com.hospetagem.hotel.model.enums.SexoPet;
import com.hospetagem.hotel.model.enums.SimNao;
import com.hospetagem.hotel.model.enums.Temperamento;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PetDTO(
        Long id,
        String nome,
        Integer idade,
        String especie,
        PortePet porte,
        String raca,
        String peso,
        Temperamento temperamento,
        SexoPet sexo,
        SimNao castrado,
        SimNao historicoDoencaAlergia,
        SimNao convivenciaAnimais,
        String contato_emergencia,
        String observacaoPet
){}