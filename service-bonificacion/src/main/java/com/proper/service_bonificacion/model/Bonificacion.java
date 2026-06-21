package com.proper.service_bonificacion.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "bonificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa las Bonificaciones asignadas a los vendedores por sus pedidos")
public class Bonificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental de la bonificación", example = "1")
    private Long bonificacionId;

    @NotNull(message = "El monto de la bonificación es obligatorio")
    @Schema(description = "Monto económico de la bonificación recibida", example = "25000.50", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double monto;

    @NotNull(message = "La fecha de asignación es obligatoria")
    @Schema(description = "Fecha de asignación del beneficio", example = "2026-06-21", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fecha;

    @NotNull(message = "El ID del vendedor es obligatorio")
    @Schema(description = "ID del Vendedor (Referencia al microservicio de Vendedores)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long vendedorId;

    @NotNull(message = "El ID del pedido es obligatorio")
    @Schema(description = "ID del Pedido origen (Referencia al microservicio de Pedidos)", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long pedidoId;

    @Transient
    @Schema(description = "Objeto completo del Vendedor, cargado en tiempo de ejecución (No se persiste)", hidden = true)
    private Object vendedor;

    @Transient
    @Schema(description = "Objeto completo del Pedido, cargado en tiempo de ejecución (No se persiste)", hidden = true)
    private Object pedido;
}