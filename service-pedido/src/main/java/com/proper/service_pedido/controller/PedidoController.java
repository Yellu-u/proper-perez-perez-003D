package com.proper.service_pedido.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.proper.service_pedido.model.Pedido;
import com.proper.service_pedido.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedido")
@Tag(name = "Pedidos", description = "Controlador para la generación y flujo de órdenes de pedido")
public class PedidoController 
{
        @Autowired
        private PedidoService pedidoService;

        @PostMapping
        @Operation(summary = "Crear un nuevo pedido", description = "Registra una orden de pedido completa con su respectiva lista de detalles")
        @ApiResponse(responseCode = "200", description = "Pedido registrado y guardado con éxito")
        @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
        public Pedido crear(@Valid @RequestBody Pedido pedido)
        {
                return pedidoService.guardarPedido(pedido);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Actualizar un pedido existente", description = "Modifica los estados o detalles de un pedido basado en su ID")
        @ApiResponse(responseCode = "200", description = "Pedido actualizado con éxito")
        @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
        public Pedido actualizarPedido(@PathVariable Long id, @Valid @RequestBody Pedido pedido)
        {
                return pedidoService.actualizarPedido(id, pedido);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener un pedido por ID", description = "Retorna de manera estructurada los datos de un pedido junto a sus detalles")
        @ApiResponse(responseCode = "200", description = "Pedido encontrado con éxito")
        @ApiResponse(responseCode = "404", description = "El pedido solicitado no fue localizado")
        public Pedido verPedido(@PathVariable Long id)
        {
                return pedidoService.obtenerPedidoPorId(id);
        }

        @GetMapping
        @Operation(summary = "Listar todos los pedidos", description = "Obtiene un listado completo con el historial de pedidos del sistema")
        @ApiResponse(responseCode = "200", description = "Lista de órdenes recuperada con éxito")
        public List<Pedido> listar()
        {
                return pedidoService.obtenerPedidos();
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar u omitir un pedido por ID", description = "Elimina de forma permanente un pedido de la base de datos")
        @ApiResponse(responseCode = "200", description = "Pedido procesado o removido con éxito")
        public void eliminar(@PathVariable Long id)
        {
                pedidoService.eliminarPedido(id);
        }
}