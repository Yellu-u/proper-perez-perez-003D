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

import com.proper.service_pedido.model.Pedido;
import com.proper.service_pedido.service.PedidoService;

@RestController
@RequestMapping("/api/v1/pedido")
public class PedidoController 
{
        @Autowired
        private PedidoService pedidoService;

        @PostMapping
        public Pedido crear(@RequestBody Pedido pedido)
        {
                return pedidoService.guardarPedido(pedido);
        }

        @PutMapping("/{id}")
        public Pedido actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido)
        {
                return pedidoService.actualizarPedido(id, pedido);
        }

        @GetMapping("/{id}")
        public Pedido verPedido(@PathVariable Long id)
        {
                return pedidoService.obtenerPedidoPorId(id);
        }

        @GetMapping
        public List<Pedido> listar()
        {
                return pedidoService.obtenerPedidos();
        }

        @DeleteMapping("/{id}")
        public void eliminar(@PathVariable Long id)
        {
                pedidoService.eliminarPedido(id);
        }
}
