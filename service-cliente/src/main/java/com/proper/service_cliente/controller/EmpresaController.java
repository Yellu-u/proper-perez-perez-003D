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

@RestController
@RequestMapping("/api/v1/clientes/empresas")
public class EmpresaController 
{
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Empresa> listar()
    {
        return clienteService.listarTodasEmpresas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtener(@PathVariable Long id)
    {
            return clienteService.buscarEmpresaPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa empresa)
    {
        return ResponseEntity.ok(clienteService.crearEmpresa(empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id)
    {
        clienteService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Empresa actualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa)
    {
        return clienteService.actualizarEmpresa(id, empresa);
    }
}