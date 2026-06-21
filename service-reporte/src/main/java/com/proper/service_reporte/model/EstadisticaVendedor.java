package com.proper.service_reporte.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

        @Schema(description = "ID del Vendedor evaluado (Referencia al microservicio de Vendedores)", example = "4")
        private Long vendedorId;

        @Schema(description = "Nombre completo del ejecutivo comercial", example = "Ana María Silva")
        private String nombreVendedor;

        @Schema(description = "Cantidad de pedidos cerrados por el vendedor en el rango de fechas", example = "35")
        private Integer totalPedidos;

        @Schema(description = "Volumen económico total vendido por el ejecutivo", example = "4200000.00")
        private Double totalVentas;

        @Schema(description = "Monto acumulado de bonificaciones obtenidas por el vendedor", example = "85000.00")
        private Double totalBonificaciones;

        @Schema(description = "Ticket promedio de venta calculado (Total Ventas / Total Pedidos)", example = "120000.00")
        private Double promedioVenta;

        @ManyToOne
        @JoinColumn(name = "reporte_id")
        @JsonBackReference
        @Schema(description = "Reporte maestro al que pertenece este renglón estadístico")
        private Reporte reporte;
}