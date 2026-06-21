package com.proper.service_pago.model;

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
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa las transacciones de Pago asociadas a los Pedidos")
public class Pago 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "ID único autoincremental de la transacción de pago", example = "1")
        private Long pagoId;

        @Schema(description = "Monto total cancelado o por cancelar en la transacción", example = "450000.00")
        private Double monto;

        @Schema(description = "Método o canal utilizado para efectuar el pago", example = "TRANSFERENCIA")
        private String metodoPago; //ej: transferencia, cheque

        @Schema(description = "Estado actual del ciclo de pago", example = "PAGADO")
        private String estadoPago; //ej: pendiente, pagado o rechazado

        @Schema(description = "Fecha exacta en la que se efectúa o procesa el pago", example = "2026-06-21")
        private LocalDate fechaPago;

        @Schema(description = "ID del Pedido asociado (Referencia lógica al microservicio de Pedidos)", example = "101")
        private Long pedidoId;

        @Transient
        @Schema(description = "Objeto completo del Pedido, inyectado de forma dinámica (No se almacena)", hidden = true)
        private Object pedido;
}