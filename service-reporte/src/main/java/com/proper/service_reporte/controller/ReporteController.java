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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes e Inteligencia de Negocio", description = "Controlador para la consolidación de KPIs, auditoría comercial y estadísticas")
public class ReporteController 
{
    @Autowired
    private ReporteService reporteService;

    @GetMapping
    @Operation(summary = "Listar historial de reportes", description = "Retorna una lista con todos los reportes gerenciales previamente calculados y guardados")
    @ApiResponse(responseCode = "200", description = "Historial recuperado exitosamente")
    public List<Reporte> listar()
    {
        return reporteService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte por ID", description = "Busca y devuelve el consolidado completo de un reporte junto a los detalles del equipo comercial por su ID único")
    @ApiResponse(responseCode = "200", description = "Reporte localizado con éxito")
    @ApiResponse(responseCode = "404", description = "El reporte solicitado no existe")
    public Optional<Reporte> obtener(@PathVariable Long id)
    {
        return reporteService.buscarPorId(id);
    }

    @PostMapping("/generar")
    @Operation(summary = "Generar reporte por rango de fechas", description = "Activa el motor de analítica para recopilar información de pedidos, finanzas y logística en un rango temporal y persistir los KPIs obtenidos")
    @ApiResponse(responseCode = "200", description = "Reporte analítico generado y guardado de forma exitosa")
    @ApiResponse(responseCode = "400", description = "Datos mal formados, formatos de fecha inválidos o parámetros faltantes")
    public Reporte generarReporte(@RequestBody Map<String, String> datos)
    {
        LocalDate fechaInicio = LocalDate.parse(datos.get("fechaInicio"));
        LocalDate fechaFin = LocalDate.parse(datos.get("fechaFin"));

        return reporteService.generarReporte(fechaInicio, fechaFin);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reporte por ID", description = "Borra permanentemente un informe y sus estadísticas asociadas de la base de datos")
    @ApiResponse(responseCode = "204", description = "Reporte eliminado del sistema de analítica")
    public ResponseEntity<Void> eliminar(@PathVariable Long id)
    {
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}