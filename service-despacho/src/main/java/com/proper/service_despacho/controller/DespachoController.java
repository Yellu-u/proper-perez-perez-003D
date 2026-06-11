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

@RestController
@RequestMapping("/api/v1/despachos")
public class DespachoController 
{
        @Autowired
        private DespachoService despachoService;

        //Obtener todos los despachos
        @GetMapping
        public List<Despacho> listar()
        {
                return despachoService.listarTodos();
        }

        //Obtener despacho por Id
        @GetMapping("/{id}")
        public Optional<Despacho> obtener(@PathVariable Long id)
        {
                return despachoService.buscarPorId(id);
        }

        //Crear despacho
        @PostMapping
        public Despacho crear(@RequestBody Despacho despacho)
        {
                return despachoService.guardarDespacho(despacho);
        }

        //Actualizar estado por pedido Id
        @PutMapping("/pedido/{pedidoId}/estado")
        public Despacho actualizarEstadoPorPedido(@PathVariable Long pedidoId, @RequestBody Map<String, Object> datos)
        {
                String estadoDespacho = datos.get("estadoDespacho").toString();

                return despachoService.actualizarEstadoPorPedidoId(pedidoId, estadoDespacho);
        }        

        //Eliminar despacho por pedido Id
        @DeleteMapping("/pedido/{pedidoId}")
        public ResponseEntity<Void> eliminarPorPedidoId(@PathVariable Long pedidoId)
        {
                despachoService.eliminarPorPedidoId(pedidoId);

                return ResponseEntity.noContent().build();
        }
}
