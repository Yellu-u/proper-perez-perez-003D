package com.proper.service_bonificacion.controller;

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

import com.proper.service_bonificacion.model.Bonificacion;
import com.proper.service_bonificacion.service.BonificacionService;

@RestController
@RequestMapping("/api/v1/bonificaciones")
public class BonificacionController {
    
    @Autowired
    private BonificacionService bonificacionService;

    @GetMapping
    public List<Bonificacion>listar()
    {
        return bonificacionService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bonificacion> obtener(@PathVariable Long id)
    {
        return bonificacionService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    //CREAR
    @PostMapping
    public ResponseEntity<Bonificacion> crear(@RequestBody Bonificacion bonificacion)
    {
        return ResponseEntity.ok(bonificacionService.guardarBonificacion(bonificacion));
    }

    //ELIMINAR  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id)
    {
        bonificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
