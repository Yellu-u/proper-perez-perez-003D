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

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController 
{
        @Autowired
        private PagoService pagoService;

        @GetMapping
        public List<Pago> listar()
        {
                return pagoService.listarTodos();
        }

        @GetMapping("/{id}")
        public Optional<Pago> obtener(@PathVariable Long id)
        {
                return pagoService.buscarPorId(id);
        }

        @PostMapping
        public Pago crear(@RequestBody Pago pago)
        {
                return pagoService.guardarPago(pago);
        }

        @PutMapping("/pedido/{pedidoId}/estado")
        public Pago actualizarEstadoPorPedido(@PathVariable Long pedidoId, @RequestBody Map<String, Object> datos)
        {
                String estadoPago = datos.get("estadoPago").toString();
                String metodoPago = datos.get("metodoPago").toString();

                return pagoService.actualizarEstadoPorPedidoId(pedidoId, estadoPago, metodoPago);
        }

        @PutMapping("/pedido/{pedidoId}/monto")
        public Pago actualizarMontoPorPedido(@PathVariable Long pedidoId, @RequestBody Map<String, Object> datos)
        {
                Double monto = Double.valueOf(datos.get("monto").toString());

                return pagoService.actualizarMontoPorPedidoId(pedidoId, monto);
        }

        @DeleteMapping("/pedido/{pedidoId}")
        public ResponseEntity<Void> eliminarPorPedido(@PathVariable Long pedidoId)
        {
                pagoService.eliminarPorPedidoId(pedidoId);

                return ResponseEntity.noContent().build();
        }
}
