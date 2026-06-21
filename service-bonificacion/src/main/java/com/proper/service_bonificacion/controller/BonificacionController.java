package com.proper.service_bonificacion.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proper.service_bonificacion.model.Bonificacion;
import com.proper.service_bonificacion.service.BonificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/bonificaciones")
@Tag(name = "Bonificaciones", description = "Controlador para el cálculo e incentivos financieros de los vendedores")
public class BonificacionController 
{
    @Autowired
    private BonificacionService bonificacionService;

    @GetMapping
    @Operation(summary = "Listar todas las bonificaciones", description = "Retorna una lista completa de todas las bonificaciones registradas")
    @ApiResponse(responseCode = "200", description = "Lista de bonificaciones obtenida con éxito")
    public List<Bonificacion> listar()
    {
        return bonificacionService.listarTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una bonificación por ID", description = "Busca y devuelve una bonificación mediante su identificador único")
    @ApiResponse(responseCode = "200", description = "Bonificación encontrada exitosamente")
    @ApiResponse(responseCode = "404", description = "La bonificación solicitada no existe")
    public Optional<Bonificacion> obtener(@PathVariable Long id)
    {
        return bonificacionService.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva bonificación", description = "Registra una bonificación y la asocia a un pedido y vendedor")
    @ApiResponse(responseCode = "200", description = "Bonificación creada con éxito")
    public Bonificacion crear(@RequestBody Bonificacion bonificacion)
    {
        return bonificacionService.guardarBonificacion(bonificacion);
    }

    @PutMapping("/pedido/{pedidoId}")
    @Operation(summary = "Actualizar bonificación por ID de Pedido", description = "Modifica el monto de la bonificación asociada a un pedido en específico")
    @ApiResponse(responseCode = "200", description = "Bonificación del pedido actualizada con éxito")
    public Bonificacion actualizarPorPedido(@PathVariable Long pedidoId, @RequestBody Map<String, Object> datos)
    {
        Double monto = Double.valueOf(datos.get("monto").toString());
        return bonificacionService.actualizarPorPedidoId(pedidoId, monto);
    }

    @DeleteMapping("/pedido/{pedidoId}")
    @Operation(summary = "Eliminar bonificación por ID de Pedido", description = "Remueve permanentemente la bonificación vinculada a un pedido específico")
    @ApiResponse(responseCode = "204", description = "Bonificación eliminada con éxito")
    public ResponseEntity<Void> eliminar(@PathVariable Long pedidoId)
    {
        bonificacionService.eliminar(pedidoId);
        return ResponseEntity.noContent().build();
    }
}