package com.proper.service_productos.controller;

import com.proper.service_productos.service.ProductoService;
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

import com.proper.service_productos.model.Linea;
import com.proper.service_productos.repository.LineaRepository;

@RestController
@RequestMapping("/api/v1/productos/linea")
public class LineaController 
{
        private final ProductoService productoService;
        @Autowired
        private LineaRepository repository;

        LineaController(ProductoService productoService) {
                this.productoService = productoService;
        }

        @GetMapping
        public List<Linea> listar()
        {
                return repository.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Linea> obtener(@PathVariable Long id)
        {
                return productoService.buscarLineaPorId(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public Linea crear(@RequestBody Linea linea)
        {
                return repository.save(linea);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarLinea(@PathVariable Long id)
        {
                productoService.eliminarLinea(id);
                return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        public Linea actualizarLinea(@PathVariable Long id, @RequestBody Linea linea)
        {
                return productoService.actualizarLinea(id, linea);
        }
}