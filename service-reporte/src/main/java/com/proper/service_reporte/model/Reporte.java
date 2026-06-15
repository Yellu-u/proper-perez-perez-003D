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

@Entity
@Table(name = "reportes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporte 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long reporteId;

        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private LocalDate fechaGeneracion;

        private Integer totalPedidos;
        private Double totalVentas;

        private Integer pagosPendientes;
        private Integer pagosPagados;

        private Integer despachosPendientes;
        private Integer despachosEntregados;

        private Double totalBonificaciones;

        @OneToMany(mappedBy = "reporte", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<EstadisticaVendedor> estadisticasVendedores;
}
