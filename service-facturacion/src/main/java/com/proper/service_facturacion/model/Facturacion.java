package com.proper.service_facturacion.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@Table(name = "facturacion")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa el Registro de Facturación legal de las transacciones")
public class Facturacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del registro de facturación", example = "1")
    private Long idFacturacion;

    @NotNull(message = "El monto total de la factura es obligatorio")
    @Schema(description = "Monto total facturado con impuestos incluidos", example = "535500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double total;

    @NotNull(message = "La fecha de emisión es obligatoria")
    @Schema(description = "Fecha de emisión de la factura", example = "2026-06-21", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaFacturacion;

    @NotBlank(message = "El estado legal/contable de la factura es obligatorio")
    @Schema(description = "Estado legal y contable de la factura", example = "EMITIDA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String estado;

    @NotNull(message = "El ID del pedido es obligatorio")
    @Schema(description = "ID del Pedido de origen (Referencia al microservicio de Pedidos)", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long pedidoId;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del Cliente receptor (Referencia al microservicio de Clientes)", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long clienteId;

    @NotNull(message = "El ID del pago es obligatorio")
    @Schema(description = "ID del Pago relacionado (Referencia al microservicio de Pagos)", example = "25", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long pagoId;
}