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

        @Schema(description = "Fecha inicial del rango evaluado en el reporte", example = "2026-06-01")
        private LocalDate fechaInicio;

        @Schema(description = "Fecha final del rango evaluado en el reporte", example = "2026-06-30")
        private LocalDate fechaFin;

        @Schema(description = "Fecha y hora exacta en la que se calculó el reporte", example = "2026-07-01")
        private LocalDate fechaGeneracion;

        @Schema(description = "Cantidad total de pedidos registrados en el período", example = "145")
        private Integer totalPedidos;

        @Schema(description = "Monto acumulado por concepto de ventas totales", example = "12500000.00")
        private Double totalVentas;

        @Schema(description = "Número de transacciones en estado pendiente de pago", example = "12")
        private Integer pagosPendientes;

        @Schema(description = "Número de transacciones liquidadas con éxito", example = "133")
        private Integer pagosPagados;

        @Schema(description = "Cantidad de órdenes en proceso logístico o de transporte", example = "5")
        private Integer despachosPendientes;

        @Schema(description = "Cantidad de órdenes entregadas satisfactoriamente al cliente", example = "140")
        private Integer despachosEntregados;

        @Schema(description = "Suma total de incentivos financieros asignados al equipo", example = "350000.00")
        private Double totalBonificaciones;

        @OneToMany(mappedBy = "reporte", cascade = CascadeType.ALL, orphanRemoval = true)
        @Schema(description = "Lista detallada con el rendimiento estadístico individual por cada vendedor")
        private List<EstadisticaVendedor> estadisticasVendedores;
}