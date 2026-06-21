package com.proper.service_vendedor.controller;

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

import com.proper.service_vendedor.model.Vendedor;
import com.proper.service_vendedor.service.VendedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vendedores")
@Tag(name = "Vendedores", description = "Controlador para la gestión e historial del equipo de vendedores")
public class VendedorController 
{
    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    @Operation(summary = "Listar todos los vendedores", description = "Retorna una lista completa con la información de todos los vendedores de la plantilla")
    @ApiResponse(responseCode = "200", description = "Lista de vendedores obtenida exitosamente")
    public List<Vendedor> listar()
    {
        return vendedorService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un vendedor por ID", description = "Busca y retorna la información detallada de un vendedor mediante su ID único")
    @ApiResponse(responseCode = "200", description = "Vendedor encontrado con éxito")
    @ApiResponse(responseCode = "404", description = "El vendedor solicitado no existe")
    public ResponseEntity<Vendedor> obtener(@PathVariable Long id)
    {
        return vendedorService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo vendedor", description = "Registra un nuevo vendedor en el sistema y retorna el registro guardado")
    @ApiResponse(responseCode = "200", description = "Vendedor creado con éxito")
    @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
    public ResponseEntity<Vendedor> crear(@Valid @RequestBody Vendedor vendedor)
    {
        return ResponseEntity.ok(vendedorService.guardar(vendedor));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un vendedor por ID", description = "Remueve permanentemente el registro de un vendedor basándose en su ID")
    @ApiResponse(responseCode = "204", description = "Vendedor eliminado con éxito")
    public ResponseEntity<Void> eliminar(@PathVariable  Long id)
    {
        vendedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un vendedor existente", description = "Modifica los atributos del vendedor en base a su identificador único")
    @ApiResponse(responseCode = "200", description = "Vendedor actualizado con éxito")
    @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
    public Vendedor actualizar(@PathVariable Long id, @Valid @RequestBody Vendedor vendedor)
    {
        return vendedorService.actualizarVendedor(id, vendedor);
    }
}