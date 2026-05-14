package com.proper.service_cliente.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;
    private String runCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String telefonoCliente;
    private String correoCliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;
}