package com.proper.service_reporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proper.service_reporte.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long>
{

}