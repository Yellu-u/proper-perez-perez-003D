package com.proper.service_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proper.service_inventario.model.Inventario;
import com.proper.service_inventario.service.InventarioService;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;


    @GetMapping
    public List<Inventario> listar()
    {
        return inventarioService.obtenerInventario();
    }


    @GetMapping("/{id}")
    public Inventario verInventario(@PathVariable Long id)
    {
        return inventarioService.obtenerInventarioPorId(id);
    }

    @GetMapping("/producto/{productoId}")
    public List<Inventario> obtenerPorProductO(@PathVariable Long productoId)
    {
        return inventarioService.obtenerPorProducto(productoId);
    }



    @PostMapping
    public Inventario crear(@RequestBody Inventario inventario)
    {
        return inventarioService.guardarInventario(inventario);
    }


    @PutMapping("/{id}")
    public Inventario actualizarInventario(@PathVariable Long id, @RequestBody Inventario inventario)
    {
        return inventarioService.actualizarInventario(id, inventario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id)
    {
        inventarioService.eliminarInventario(id);
    }


    @DeleteMapping("/producto/{productoId}")
    public void eliminarPorProducto(@PathVariable Long productoId)
    {
        inventarioService.eliminarPorProducto(productoId);
    }

}
