package com.proper.service_reporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proper.service_reporte.model.EstadisticaVendedor;

@Repository
public interface EstadisticaVendedorRepository extends JpaRepository<EstadisticaVendedor, Long>
{
        List<EstadisticaVendedor> findByReporteReporteId(Long reporteId);
}