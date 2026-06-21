package com.proper.service_despacho.controller;

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

import com.proper.service_despacho.model.Despacho;
import com.proper.service_despacho.service.DespachoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/despachos")
@Tag(name = "Despachos", description = "Controlador para el seguimiento logístico, estados de envío y entregas")
public class DespachoController 
{
        @Autowired
        private DespachoService despachoService;

        @GetMapping
        @Operation(summary = "Listar todos los despachos", description = "Retorna una lista completa del estado logístico de todas las órdenes")
        @ApiResponse(responseCode = "200", description = "Lista de despachos obtenida con éxito")
        public List<Despacho> listar()
        {
                return despachoService.listarTodos();
        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener despacho por ID", description = "Busca y devuelve una orden de entrega específica mediante su ID único")
        @ApiResponse(responseCode = "200", description = "Despacho localizado correctamente")
        @ApiResponse(responseCode = "404", description = "La orden de despacho solicitada no existe")
        public Optional<Despacho> obtener(@PathVariable Long id)
        {
                return despachoService.buscarPorId(id);
        }

        @PostMapping
        @Operation(summary = "Crear una nueva orden de despacho", description = "Inicializa el flujo logístico de entrega para un pedido de venta")
        @ApiResponse(responseCode = "200", description = "Despacho programado con éxito")
        public Despacho crear(@RequestBody Despacho despacho)
        {
                return despachoService.guardarDespacho(despacho);
        }

        @PutMapping("/pedido/{pedidoId}/estado")
        @Operation(summary = "Actualizar estado por ID de Pedido", description = "Modifica las etapas de entrega (pendiente, en camino, entregado) filtrando por el ID del pedido")
        @ApiResponse(responseCode = "200", description = "Estado de despacho modificado con éxito")
        public Despacho actualizarEstadoPorPedido(@PathVariable Long pedidoId, @RequestBody Map<String, Object> datos)
        {
                String estadoDespacho = datos.get("estadoDespacho").toString();

                return despachoService.actualizarEstadoPorPedidoId(pedidoId, estadoDespacho);
        }        

        @DeleteMapping("/pedido/{pedidoId}")
        @Operation(summary = "Eliminar despacho por ID de Pedido", description = "Cancela o remueve permanentemente el registro de entrega vinculado a un pedido")
        @ApiResponse(responseCode = "204", description = "Orden de despacho removida con éxito")
        public ResponseEntity<Void> eliminarPorPedidoId(@PathVariable Long pedidoId)
        {
                despachoService.eliminarPorPedidoId(pedidoId);

                return ResponseEntity.noContent().build();
        }
}