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

@Entity
@Table(name = "estadistica_vendedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticaVendedor 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long estadisticaId;

        private Long vendedorId;
        private String nombreVendedor;
        private Integer totalPedidos;
        private Double totalVentas;
        private Double totalBonificaciones;
        private Double promedioVenta;

        @ManyToOne
        @JoinColumn(name = "reporte_id")
        private Reporte reporte;
}
