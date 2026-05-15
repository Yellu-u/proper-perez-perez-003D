package com.proper.service_bonificacion.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.proper.service_bonificacion.model.Bonificacion;
import com.proper.service_bonificacion.service.BonificacionService;

@RestController
@RequestMapping("/api/v1/bonificaciones")
public class BonificacionController 
{
    @Autowired
    private BonificacionService bonificacionService;

    //Se utiliza para buscar todas las bonificaciones existentes
    @GetMapping
    public List<Bonificacion>listar()
    {
        return bonificacionService.listarTodas();
    }

    //Se utiliza para buscar una bonificación por su id
    @GetMapping("/{id}")
    public Optional<Bonificacion> obtener(@PathVariable Long id)
    {
        return bonificacionService.buscarPorId(id);
    }

    //Se utiliza para crear
    @PostMapping
    public Bonificacion crear(@RequestBody Bonificacion bonificacion)
    {
        return bonificacionService.guardarBonificacion(bonificacion);
    }

    @PutMapping("/pedido/{pedidoId}")
    public Bonificacion actualizarPorPedido(@PathVariable Long pedidoId, @RequestBody Map<String, Object> datos)
    {
        Double monto = Double.valueOf(datos.get("monto").toString());

        return bonificacionService.actualizarPorPedidoId(pedidoId, monto);
    }

    //ELIMINAR  
    @DeleteMapping("/pedido/{pedidoId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long pedidoId)
    {
        bonificacionService.eliminar(pedidoId);
        return ResponseEntity.noContent().build();
    }
}