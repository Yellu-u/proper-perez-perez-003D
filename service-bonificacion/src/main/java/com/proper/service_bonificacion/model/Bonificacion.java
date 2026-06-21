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

    @Schema(description = "Monto económico de la bonificación recibida", example = "25000.50")
    private Double monto;

    @Schema(description = "Fecha de asignación del beneficio", example = "2026-06-21")
    private LocalDate fecha;

    @Schema(description = "ID del Vendedor (Referencia al microservicio de Vendedores)", example = "1")
    private Long vendedorId;

    @Schema(description = "ID del Pedido origen (Referencia al microservicio de Pedidos)", example = "101")
    private Long pedidoId;

    @Transient
    @Schema(description = "Objeto completo del Vendedor, cargado en tiempo de ejecución (No se persiste)", hidden = true)
    private Object vendedor;

    @Transient
    @Schema(description = "Objeto completo del Pedido, cargado en tiempo de ejecución (No se persiste)", hidden = true)
    private Object pedido;
}