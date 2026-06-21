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
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "despacho")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa las órdenes de despacho y logística de entrega")
public class Despacho 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "ID único autoincremental del despacho", example = "1")
        private Long despachoId;

        @Schema(description = "Dirección física detallada para la entrega del pedido", example = "Av. Providencia 456, Depto 201, Santiago")
        private String direccionEntrega;

        @Schema(description = "Fecha estimada u objetivo para realizar la entrega", example = "2026-06-25")
        private LocalDate fechaEstimada;

        @Schema(description = "Fecha real en la que se concretó la entrega", example = "2026-06-24")
        private LocalDate fechaEntrega;

        @Schema(description = "Estado actual de la cadena logística de despacho", example = "EN_CAMINO")
        private String estadoDespacho; //ej: pendiente, en_camino, entregado

        @Schema(description = "ID del Pedido origen asociado (Referencia al microservicio de Pedidos)", example = "101")
        private Long pedidoId;

        @Transient
        @Schema(description = "Objeto completo del Pedido, cargado dinámicamente en tiempo de ejecución", hidden = true)
        private Object pedido;
}