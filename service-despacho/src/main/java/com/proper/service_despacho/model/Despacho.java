package com.proper.service_despacho.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

        @NotBlank(message = "La dirección de entrega es obligatoria")
        @Schema(description = "Dirección física detallada para la entrega del pedido", example = "Av. Providencia 456, Depto 201, Santiago", requiredMode = Schema.RequiredMode.REQUIRED)
        private String direccionEntrega;

        @NotNull(message = "La fecha estimada de entrega es obligatoria")
        @Schema(description = "Fecha estimada u objetivo para realizar la entrega", example = "2026-06-25", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDate fechaEstimada;

        @Schema(description = "Fecha real en la que se concretó la entrega", example = "2026-06-24")
        private LocalDate fechaEntrega;

        @NotBlank(message = "El estado del despacho es obligatorio")
        @Schema(description = "Estado actual de la cadena logística de despacho", example = "EN_CAMINO", requiredMode = Schema.RequiredMode.REQUIRED)
        private String estadoDespacho; //ej: pendiente, en_camino, entregado

        @NotNull(message = "El ID del pedido asociado es obligatorio")
        @Schema(description = "ID del Pedido origen asociado (Referencia al microservicio de Pedidos)", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long pedidoId;

        @Transient
        @Schema(description = "Objeto completo del Pedido, cargado dinámicamente en tiempo de ejecución", hidden = true)
        private Object pedido;
}