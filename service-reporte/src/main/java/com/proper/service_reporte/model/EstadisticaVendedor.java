package com.proper.service_reporte.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "estadistica_vendedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que almacena los KPIs consolidados de un vendedor específico dentro de un período")
public class EstadisticaVendedor 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "ID único autoincremental de la métrica", example = "50")
        private Long estadisticaId;

        @NotNull(message = "El ID del vendedor es obligatorio")
        @Schema(description = "ID del Vendedor evaluado (Referencia al microservicio de Vendedores)", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long vendedorId;

        @NotBlank(message = "El nombre del vendedor es obligatorio")
        @Schema(description = "Nombre completo del ejecutivo comercial", example = "Ana María Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        private String nombreVendedor;

        @NotNull(message = "El total de pedidos es obligatorio")
        @Schema(description = "Cantidad de pedidos cerrados por el vendedor en el rango de fechas", example = "35", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer totalPedidos;

        @NotNull(message = "El total de ventas es obligatorio")
        @Schema(description = "Volumen económico total vendido por el ejecutivo", example = "4200000.00", requiredMode = Schema.RequiredMode.REQUIRED)
        private Double totalVentas;

        @NotNull(message = "El total de bonificaciones es obligatorio")
        @Schema(description = "Monto acumulado de bonificaciones obtenidas por el vendedor", example = "85000.00", requiredMode = Schema.RequiredMode.REQUIRED)
        private Double totalBonificaciones;

        @NotNull(message = "El promedio de venta es obligatorio")
        @Schema(description = "Ticket promedio de venta calculado (Total Ventas / Total Pedidos)", example = "120000.00", requiredMode = Schema.RequiredMode.REQUIRED)
        private Double promedioVenta;

        @ManyToOne
        @JoinColumn(name = "reporte_id")
        @JsonBackReference
        @Schema(description = "Reporte maestro al que pertenece este renglón estadístico")
        private Reporte reporte;
}