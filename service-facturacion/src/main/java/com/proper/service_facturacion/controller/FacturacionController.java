package com.proper.service_facturacion.controller;

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

import com.proper.service_facturacion.model.Facturacion;
import com.proper.service_facturacion.service.FacturacionService;

@RestController
@RequestMapping("/api/v1/facturacion")
public class FacturacionController {

    @Autowired
    private FacturacionService facturacionService;

    @GetMapping
    public List<Facturacion> listar()
    {
        return facturacionService.obtenerFacturacion();
    }

    @GetMapping("/{id}")
    public Facturacion buscar(@PathVariable Long id)
    {
        return facturacionService.obtenerFacturacionid(id);
    }

    @PostMapping
    public Facturacion guardar(
            @RequestBody Facturacion facturacion){

        return facturacionService.guardarFacturacion(facturacion);
    }

    @PutMapping("/{id}")
    public Facturacion actualizar(
            @PathVariable Long id,
            @RequestBody Facturacion facturacion){

        return facturacionService.actualizarFactura(id, facturacion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id)
    {
        facturacionService.eliminarFactura(id);
    }

}
