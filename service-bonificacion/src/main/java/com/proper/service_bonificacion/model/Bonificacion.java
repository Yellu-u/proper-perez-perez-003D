package com.proper.service_bonificacion.model;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "bonificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bonificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bonificacionId;
    private Double monto;
    private LocalDate fecha;
    

    private Long vendedorId;

    private Long pedidoId;

    @Transient
    private Object vendedor;

    @Transient
    private Object pedido;
}