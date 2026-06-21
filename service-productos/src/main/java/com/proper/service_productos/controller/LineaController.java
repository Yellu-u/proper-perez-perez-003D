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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productos/linea")
@Tag(name = "Líneas de Productos", description = "Controlador para la administración de las categorías o líneas")
public class LineaController 
{
        private final ProductoService productoService;
        @Autowired
        private LineaRepository repository;

        LineaController(ProductoService productoService) {
                this.productoService = productoService;
        }

        @GetMapping
        @Operation(summary = "Listar todas las líneas", description = "Retorna una lista completa de todas las categorías o líneas de productos")
        @ApiResponse(responseCode = "200", description = "Lista de líneas obtenida con éxito")
        public List<Linea> listar()
        {
                return repository.findAll();
        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener una línea por ID", description = "Busca y devuelve los detalles de una categoría mediante su ID")
        @ApiResponse(responseCode = "200", description = "Línea encontrada con éxito")
        @ApiResponse(responseCode = "404", description = "La línea solicitada no existe")
        public ResponseEntity<Linea> obtener(@PathVariable Long id)
        {
                return productoService.buscarLineaPorId(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        @Operation(summary = "Crear una nueva línea", description = "Registra una nueva categoría en la base de datos")
        @ApiResponse(responseCode = "200", description = "Línea creada con éxito")
        @ApiResponse(responseCode = "400", description = "Datos mal formados o faltantes")
        public Linea crear(@Valid @RequestBody Linea linea)
        {
                return repository.save(linea);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar una línea por ID", description = "Elimina permanentemente una categoría del catálogo")
        @ApiResponse(responseCode = "204", description = "Línea eliminada con éxito")
        public ResponseEntity<Void> eliminarLinea(@PathVariable Long id)
        {
                productoService.eliminarLinea(id);
                return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        @Operation(summary = "Actualizar una línea existente", description = "Modifica los campos de una categoría comercial según su ID")
        @ApiResponse(responseCode = "200", description = "Línea actualizada con éxito")
        @ApiResponse(responseCode = "400", description = "Datos mal formados o faltantes")
        public Linea actualizarLinea(@PathVariable Long id, @Valid @RequestBody Linea linea)
        {
                return productoService.actualizarLinea(id, linea);
        }
}