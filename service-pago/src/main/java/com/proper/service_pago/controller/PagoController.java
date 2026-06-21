package com.proper.service_pago.controller;

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

import com.proper.service_pago.model.Pago;
import com.proper.service_pago.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Controlador para el registro de recaudación y auditoría de transacciones financieras")
public class PagoController 
{
        @Autowired
        private PagoService pagoService;

        @GetMapping
        @Operation(summary = "Listar todas las transacciones", description = "Retorna el historial completo de pagos registrados en el sistema")
        @ApiResponse(responseCode = "200", description = "Listado de pagos recuperado con éxito")
        public List<Pago> listar()
        {
                return pagoService.listarTodos();
        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener pago por ID", description = "Busca y devuelve los detalles de una transacción mediante su ID único")
        @ApiResponse(responseCode = "200", description = "Pago localizado correctamente")
        @ApiResponse(responseCode = "404", description = "El registro de pago no existe")
        public Optional<Pago> obtener(@PathVariable Long id)
        {
                return pagoService.buscarPorId(id);
        }

        @PostMapping
        @Operation(summary = "Registrar un nuevo pago", description = "Crea un registro de pago inicial asociado a un pedido de venta")
        @ApiResponse(responseCode = "200", description = "Transacción de pago creada con éxito")
        @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
        public Pago crear(@Valid @RequestBody Pago pago)
        {
                return pagoService.guardarPago(pago);
        }

        @PutMapping("/pedido/{pedidoId}/estado")
        @Operation(summary = "Actualizar estado y método por Pedido", description = "Modifica los estados de conciliación de pago filtrando por el ID del pedido")
        @ApiResponse(responseCode = "200", description = "Estado de pago actualizado correctamente")
        public Pago actualizarEstadoPorPedido(@PathVariable Long pedidoId, @RequestBody Map<String, Object> datos)
        {
                String estadoPago = datos.get("estadoPago").toString();
                String metodoPago = datos.get("metodoPago").toString();

                return pagoService.actualizarEstadoPorPedidoId(pedidoId, estadoPago, metodoPago);
        }

        @PutMapping("/pedido/{pedidoId}/monto")
        @Operation(summary = "Actualizar monto por ID de Pedido", description = "Modifica el valor económico a saldar basándose en el ID de su pedido")
        @ApiResponse(responseCode = "200", description = "Monto de la transacción actualizado con éxito")
        public Pago actualizarMontoPorPedido(@PathVariable Long pedidoId, @RequestBody Map<String, Object> datos)
        {
                Double monto = Double.valueOf(datos.get("monto").toString());

                return pagoService.actualizarMontoPorPedidoId(pedidoId, monto);
        }

        @DeleteMapping("/pedido/{pedidoId}")
        @Operation(summary = "Eliminar transacciones de un Pedido", description = "Remueve permanentemente el registro de cobro vinculado a un pedido específico")
        @ApiResponse(responseCode = "204", description = "Registro de pago eliminado con éxito")
        public ResponseEntity<Void> eliminarPorPedido(@PathVariable Long pedidoId)
        {
                pagoService.eliminarPorPedidoId(pedidoId);

                return ResponseEntity.noContent().build();
        }
}