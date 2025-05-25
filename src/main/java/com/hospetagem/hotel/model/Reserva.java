package com.hospetagem.hotel.model;

import com.hospetagem.hotel.model.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_entrada")
    private LocalDate dataEntrada;

    @Column(name = "data_saida")
    private LocalDate dataSaida;

    @Column(name = "hora_entrada")
    private LocalTime horaEntrada;

    @Column(name = "hora_saida")
    private LocalTime horaSaida;

    @Enumerated(EnumType.STRING)
    private StatusReserva statusReserva = StatusReserva.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();
}
