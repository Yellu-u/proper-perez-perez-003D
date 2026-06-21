package com.proper.service_cliente.controller;

import com.proper.service_cliente.service.ClienteService;
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

import com.proper.service_cliente.model.Empresa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clientes/empresas")
@Tag(name = "Empresas", description = "Controlador para la gestión corporativa de empresas de clientes")
public class EmpresaController 
{
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar todas las empresas", description = "Retorna una lista completa de todas las empresas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de empresas obtuvo con éxito")
    public List<Empresa> listar()
    {
        return clienteService.listarTodasEmpresas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una empresa por ID", description = "Busca y retorna los detalles de una empresa mediante su ID único")
    @ApiResponse(responseCode = "200", description = "Empresa encontrada con éxito")
    @ApiResponse(responseCode = "404", description = "La empresa solicitada no existe")
    public ResponseEntity<Empresa> obtener(@PathVariable Long id)
    {
            return clienteService.buscarEmpresaPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva empresa", description = "Registra una nueva empresa en el sistema")
    @ApiResponse(responseCode = "200", description = "Empresa creada con éxito")
    @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
    public ResponseEntity<Empresa> crearEmpresa(@Valid @RequestBody Empresa empresa)
    {
        return ResponseEntity.ok(clienteService.crearEmpresa(empresa));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una empresa por ID", description = "Elimina permanentemente una empresa de la base de datos")
    @ApiResponse(responseCode = "204", description = "Empresa eliminada con éxito")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id)
    {
        clienteService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una empresa existente", description = "Modifica los campos de una entidad empresa según su ID")
    @ApiResponse(responseCode = "200", description = "Empresa actualizada con éxito")
    @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
    public Empresa actualizarEmpresa(@PathVariable Long id, @Valid @RequestBody Empresa empresa)
    {
        return clienteService.actualizarEmpresa(id, empresa);
    }
}