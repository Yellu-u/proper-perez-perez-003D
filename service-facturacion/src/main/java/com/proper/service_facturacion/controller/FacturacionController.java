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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/facturacion")
@Tag(name = "Facturación", description = "Controlador para la emisión de documentos tributarios y facturas")
public class FacturacionController {

    @Autowired
    private FacturacionService facturacionService;

    @GetMapping
    @Operation(summary = "Listar todas las facturas", description = "Retorna una lista completa de las facturas generadas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida con éxito")
    public List<Facturacion> listar()
    {
        return facturacionService.obtenerFacturacion();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar factura por ID", description = "Busca y devuelve un documento tributario mediante su identificador único")
    @ApiResponse(responseCode = "200", description = "Factura localizada correctamente")
    @ApiResponse(responseCode = "404", description = "La factura solicitada no existe")
    public Facturacion buscar(@PathVariable Long id)
    {
        return facturacionService.obtenerFacturacionid(id);
    }

    @PostMapping
    @Operation(summary = "Generar nueva factura", description = "Crea y emite un nuevo registro contable de facturación")
    @ApiResponse(responseCode = "200", description = "Factura guardada con éxito")
    @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
    public Facturacion guardar(@Valid @RequestBody Facturacion facturacion){
        return facturacionService.guardarFacturacion(facturacion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar factura existente", description = "Modifica los campos tributarios o de estados de una factura específica")
    @ApiResponse(responseCode = "200", description = "Factura actualizada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
    public Facturacion actualizar(@PathVariable Long id, @Valid @RequestBody Facturacion facturacion){
        return facturacionService.actualizarFactura(id, facturacion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar o anular factura por ID", description = "Remueve permanentemente una factura basándose en su ID")
    @ApiResponse(responseCode = "200", description = "Documento removido del sistema")
    public void eliminar(@PathVariable Long id)
    {
        facturacionService.eliminarFactura(id);
    }
}