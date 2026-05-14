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

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController 
{
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listar()
    {
        return clienteService.listarTodosClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id)
    {
            return clienteService.buscarClientePorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente)
    {
        return ResponseEntity.ok(clienteService.crearCliente(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id)
    {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente)
    {
        return clienteService.actualizarCliente(id, cliente);
    }
}