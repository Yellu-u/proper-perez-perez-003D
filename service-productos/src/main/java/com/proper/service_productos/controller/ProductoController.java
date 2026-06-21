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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Controlador para la gestión de productos del catálogo")
public class ProductoController 
{
        @Autowired
        private ProductoService productoService;

        @GetMapping
        @Operation(summary = "Listar todos los productos", description = "Retorna una lista completa de todos los productos registrados en el sistema")
        @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
        public List<Producto> listar()
        {
                return productoService.listarTodosProductos();
        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener un producto por ID", description = "Busca y retorna un producto específico según su identificador único")
        @ApiResponse(responseCode = "200", description = "Producto encontrado con éxito")
        @ApiResponse(responseCode = "404", description = "El producto con el ID solicitado no existe")
        public ResponseEntity<Producto> obtener(@PathVariable Long id)
        {
                return productoService.buscarProductoPorId(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        @Operation(summary = "Crear un nuevo producto", description = "Registra un producto en el sistema y retorna el objeto creado")
        @ApiResponse(responseCode = "200", description = "Producto creado con éxito")
        @ApiResponse(responseCode = "400", description = "Datos mal formados o faltantes")
        public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto)
        {
                return ResponseEntity.ok(productoService.guardarProducto(producto));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar un producto por ID", description = "Remueve permanentemente un producto del sistema")
        @ApiResponse(responseCode = "204", description = "Producto eliminado con éxito")
        public ResponseEntity<Void> eliminar(@PathVariable Long id)
        {
                productoService.eliminarProducto(id);
                return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        @Operation(summary = "Actualizar un producto existente", description = "Modifica los atributos de un producto según su ID")
        @ApiResponse(responseCode = "200", description = "Producto actualizado con éxito")
        @ApiResponse(responseCode = "400", description = "Datos mal formados o faltantes")
        public Producto actualizarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto)
        {
                return productoService.actualizarProducto(id, producto);
        }
}