package com.proper.service_inventario.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.proper.service_inventario.model.Inventario;
import com.proper.service_inventario.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventario")
@Tag(name = "Inventario", description = "Controlador para el seguimiento de existencias y control de stock en bodegas")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Listar todo el inventario", description = "Retorna el estado de stock de todos los productos almacenados")
    @ApiResponse(responseCode = "200", description = "Inventario listado con éxito")
    public List<Inventario> listar() {
        return inventarioService.obtenerInventario();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ver registro de inventario por ID", description = "Busca una cartola de inventario específica usando su ID único")
    @ApiResponse(responseCode = "200", description = "Registro encontrado con éxito")
    @ApiResponse(responseCode = "404", description = "Registro de inventario no localizado")
    public Inventario verInventario(@PathVariable Long id) {
        return inventarioService.obtenerInventarioPorId(id);
    }

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener inventario por ID de Producto", description = "Busca y lista los registros de stock filtrados por el ID de un producto en específico")
    @ApiResponse(responseCode = "200", description = "Información de stock recuperada")
    public List<Inventario> obtenerPorProductO(@PathVariable Long productoId) {
        return inventarioService.obtenerPorProducto(productoId);
    }

    @PostMapping
    @Operation(summary = "Crear registro de inventario", description = "Inicializa el control de stock para un nuevo ítem en el almacén")
    @ApiResponse(responseCode = "200", description = "Registro de inventario creado con éxito")
    @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
    public Inventario crear(@Valid @RequestBody Inventario inventario) {
        return inventarioService.guardarInventario(inventario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar niveles de inventario", description = "Modifica los valores de stock actual, mínimo o fechas de auditoría")
    @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos mal formados o campos obligatorios faltantes")
    public Inventario actualizarInventario(@PathVariable Long id, @Valid @RequestBody Inventario inventario) {
        return inventarioService.actualizarInventario(id, inventario);
    }

    @PutMapping("/producto/{productoId}/descontar")
    @Operation(summary = "Descontar stock de producto por Pedido", description = "Disminuye el stock disponible en bodega cuando un pedido se procesa (permite números negativos si requiere manufactura)")
    @ApiResponse(responseCode = "200", description = "Stock procesado con éxito")
    @ApiResponse(responseCode = "400", description = "Error en los parámetros o formato de la solicitud")
    public Inventario descontar(@PathVariable Long productoId, 
                                @RequestParam Integer cantidad, 
                                @RequestParam Long pedidoId) {
        return inventarioService.descontarStock(productoId, cantidad, pedidoId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro de inventario por ID", description = "Elimina permanentemente una ficha de stock basándose en su ID")
    @ApiResponse(responseCode = "200", description = "Ficha eliminada del sistema")
    public void eliminar(@PathVariable Long id) {
        inventarioService.eliminarInventario(id);
    }

    @DeleteMapping("/producto/{productoId}")
    @Operation(summary = "Eliminar inventario por ID de Producto", description = "Remueve de las bodegas todo el registro de control de un producto específico")
    @ApiResponse(responseCode = "200", description = "Registros de stock del producto borrados")
    public void eliminarPorProducto(@PathVariable Long productoId) {
        inventarioService.eliminarPorProducto(productoId);
    }
}