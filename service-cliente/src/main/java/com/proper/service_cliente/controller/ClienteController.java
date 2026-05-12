package com.proper.service_cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id)
    {
        return clienteService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente)
    {
        return ResponseEntity.ok(clienteService.crear(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id)
    {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
