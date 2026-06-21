package com.proper.service_cliente.controller;

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

import com.proper.service_cliente.model.Cliente;
import com.proper.service_cliente.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Controlador para la gestión y administración de clientes")
public class ClienteController 
{
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Retorna una lista completa con todos los clientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida con éxito")
    public List<Cliente> listar()
    {
        return clienteService.listarTodosClientes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cliente por ID", description = "Busca y retorna un cliente específico según su identificador único")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado con éxito")
    @ApiResponse(responseCode = "404", description = "El cliente solicitado no existe")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id)
    {
            return clienteService.buscarClientePorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Registra un nuevo cliente en el sistema y retorna el objeto creado")
    @ApiResponse(responseCode = "200", description = "Cliente creado con éxito")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente)
    {
        return ResponseEntity.ok(clienteService.crearCliente(cliente));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente por ID", description = "Remueve permanentemente a un cliente de la base de datos")
    @ApiResponse(responseCode = "204", description = "Cliente eliminado con éxito")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id)
    {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente existente", description = "Modifica los atributos de un cliente basado en su ID")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado con éxito")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente)
    {
        return clienteService.actualizarCliente(id, cliente);
    }
}