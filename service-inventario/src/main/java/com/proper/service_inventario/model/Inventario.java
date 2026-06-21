package com.proper.service_inventario.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "inventario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa el control de existencias, stock y almacenamiento de productos")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del registro de inventario", example = "1")
    private Long idInventario;

    @Schema(description = "Unidades físicas disponibles actualmente en bodega", example = "150")
    private int stockActual;

    @Schema(description = "Nivel mínimo permitido antes de generar una alerta de reabastecimiento", example = "20")
    private int stockMinimo;

    @Schema(description = "Última fecha en la que se modificaron las existencias", example = "2026-06-21")
    private LocalDate fechaActualizacion;

    @Schema(description = "ID del Producto (Referencia lógica al microservicio de Productos)", example = "12")
    private Long productoId;

    @Schema(description = "ID del Pedido que provocó el último movimiento (Opcional)", example = "101")
    private Long pedidoId;
}