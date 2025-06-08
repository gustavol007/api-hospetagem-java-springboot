package com.hospetagem.hotel.model;

import com.hospetagem.hotel.model.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

   @Column(name = "preco_total", precision = 10, scale = 2)
   private BigDecimal precoTotal;

   @ManyToOne
@JoinColumn(name = "cliente_id", nullable = false)
private Cliente cliente;

@ManyToMany
@JoinTable(
    name = "reserva_pets",
    joinColumns = @JoinColumn(name = "reserva_id"),
    inverseJoinColumns = @JoinColumn(name = "pet_id")
)
private List<Pet> pets = new ArrayList<>();

   @ManyToMany
   @JoinTable(
           name = "reserva_servicos",
           joinColumns = @JoinColumn(name = "reserva_id"),
           inverseJoinColumns = @JoinColumn(name = "servico_id")
   )
   private List<Servico> servicos = new ArrayList<>();

}