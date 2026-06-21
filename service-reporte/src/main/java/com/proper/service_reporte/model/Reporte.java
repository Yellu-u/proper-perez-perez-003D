package com.proper.service_reporte.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "reportes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa el consolidado y KPIs de un Reporte Gerencial")
public class Reporte 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "ID único autoincremental del reporte", example = "1")
        private Long reporteId;

        @NotNull(message = "La fecha de inicio del rango es obligatoria")
        @Schema(description = "Fecha inicial del rango evaluado en el reporte", example = "2026-06-01", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDate fechaInicio;

        @NotNull(message = "La fecha de fin del rango es obligatoria")
        @Schema(description = "Fecha final del rango evaluado en el reporte", example = "2026-06-30", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDate fechaFin;

        @NotNull(message = "La fecha de generación es obligatoria")
        @Schema(description = "Fecha y hora exacta en la que se calculó el reporte", example = "2026-07-01", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDate fechaGeneracion;

        @NotNull(message = "El total de pedidos es obligatorio")
        @Schema(description = "Cantidad total de pedidos registrados en el período", example = "145", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer totalPedidos;

        @NotNull(message = "El total de ventas es obligatorio")
        @Schema(description = "Monto acumulado por concepto de ventas totales", example = "12500000.00", requiredMode = Schema.RequiredMode.REQUIRED)
        private Double totalVentas;

        @NotNull(message = "La cantidad de pagos pendientes es obligatoria")
        @Schema(description = "Número de transacciones en estado pendiente de pago", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer pagosPendientes;

        @NotNull(message = "La cantidad de pagos completados es obligatoria")
        @Schema(description = "Número de transacciones liquidadas con éxito", example = "133", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer pagosPagados;

        @NotNull(message = "La cantidad de despachos pendientes es obligatoria")
        @Schema(description = "Cantidad de órdenes en proceso logístico o de transporte", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer despachosPendientes;

        @NotNull(message = "La cantidad de despachos entregados es obligatoria")
        @Schema(description = "Cantidad de órdenes entregadas satisfactoriamente al cliente", example = "140", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer despachosEntregados;

        @NotNull(message = "El total de bonificaciones es obligatorio")
        @Schema(description = "Suma total de incentivos financieros asignados al equipo", example = "350000.00", requiredMode = Schema.RequiredMode.REQUIRED)
        private Double totalBonificaciones;

        @OneToMany(mappedBy = "reporte", cascade = CascadeType.ALL, orphanRemoval = true)
        @Schema(description = "Lista detallada con el rendimiento estadístico individual por cada vendedor")
        private List<EstadisticaVendedor> estadisticasVendedores;
}