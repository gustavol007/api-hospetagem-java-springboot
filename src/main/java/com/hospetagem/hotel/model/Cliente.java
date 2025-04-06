package com.hospetagem.hotel.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "TB_CLIENTES")
public class Cliente extends Pessoa{
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER )
    private List<Endereco> enderecos;

}
