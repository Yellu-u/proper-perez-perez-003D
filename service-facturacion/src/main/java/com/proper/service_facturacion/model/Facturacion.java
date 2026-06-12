package com.proper.service_facturacion.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "facturacion")
@AllArgsConstructor
@NoArgsConstructor
public class Facturacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacturacion;

    private Double total;

    private LocalDate fechaFacturacion;

    private String estado;

    private Long pedidoId;

    private Long clienteId;

    private Long pagoId;

}
