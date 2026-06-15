package com.proper.service_reporte.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proper.service_reporte.model.Reporte;
import com.proper.service_reporte.service.ReporteService;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController 
{
    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public List<Reporte> listar()
    {
        return reporteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Reporte> obtener(@PathVariable Long id)
    {
        return reporteService.buscarPorId(id);
    }

    @PostMapping("/generar")
    public Reporte generarReporte(@RequestBody Map<String, String> datos)
    {
        LocalDate fechaInicio = LocalDate.parse(datos.get("fechaInicio"));
        LocalDate fechaFin = LocalDate.parse(datos.get("fechaFin"));

        return reporteService.generarReporte(fechaInicio, fechaFin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id)
    {
        reporteService.eliminarReporte(id);

        return ResponseEntity.noContent().build();
    }
}