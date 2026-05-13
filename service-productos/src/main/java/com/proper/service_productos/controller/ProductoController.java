package com.proper.service_productos.controller;

import java.util.List;

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

import com.proper.service_productos.model.Producto;
import com.proper.service_productos.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController 
{
        @Autowired
        private ProductoService productoService;

        @GetMapping
        public List<Producto> listar()
        {
                return productoService.listarTodosProductos();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Producto> obtener(@PathVariable Long id)
        {
                return productoService.buscarProductoPorId(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<Producto> crear(@RequestBody Producto producto)
        {
                return ResponseEntity.ok(productoService.guardarProducto(producto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminar(@PathVariable Long id)
        {
                productoService.eliminarProducto(id);
                return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto)
        {
                return productoService.actualizarProducto(id, producto);
        }
}