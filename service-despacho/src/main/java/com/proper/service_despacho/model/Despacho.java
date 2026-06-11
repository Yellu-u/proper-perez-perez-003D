package com.proper.service_despacho.model;

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
@Table(name = "despacho")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Despacho 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long despachoId;

        private String direccionEntrega;
        private LocalDate fechaEstimada;
        private LocalDate fechaEntrega;
        private String estadoDespacho; //ej: pendiente, en_camino, entregado

        private Long pedidoId;

        @Transient
        private Object pedido;
}