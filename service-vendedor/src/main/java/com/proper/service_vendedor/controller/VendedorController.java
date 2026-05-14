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

@RestController
@RequestMapping("/api/v1/vendedores")
public class VendedorController 
{
    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public List<Vendedor> listar()
    {
        return vendedorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> obtener(@PathVariable Long id)
    {
        return vendedorService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vendedor> crear(@RequestBody Vendedor vendedor)
    {
        return ResponseEntity.ok(vendedorService.guardar(vendedor));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable  Long id)
    {
        vendedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Vendedor actualizar(@PathVariable Long id,@RequestBody Vendedor vendedor)
    {
        return vendedorService.actualizarVendedor(id, vendedor);
    }
}
