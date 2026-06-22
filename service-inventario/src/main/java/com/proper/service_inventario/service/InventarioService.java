package com.proper.service_inventario.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_inventario.model.Inventario;
import com.proper.service_inventario.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private InventarioRepository inventarioRepository;

    // Obtener Inventario Completo
    public List<Inventario> obtenerInventario() {
        return inventarioRepository.findAll();
    }

    // Buscar inventario por producto
    public List<Inventario> obtenerPorProducto(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    // Obtener Inventario de producto por Id
    public Inventario obtenerInventarioPorId(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    // Crear Inventario de Producto
    public Inventario guardarInventario(Inventario inventario) {
        // Verificar si el producto existe
        Object producto = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/api/v1/productos/" + inventario.getProductoId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            
        if (producto == null) {
            return null;
        }
        inventario.setFechaActualizacion(LocalDate.now());
        return inventarioRepository.save(inventario);
    }

    // Actualizar el Inventario por producto y Id
    public Inventario actualizarInventario(Long id, Inventario inventario) {
        Inventario inventarioCreado = inventarioRepository.findById(id).orElse(null);
        if (inventarioCreado != null) {
            inventarioCreado.setFechaActualizacion(LocalDate.now());
            inventarioCreado.setStockActual(inventario.getStockActual());
            inventarioCreado.setStockMinimo(inventario.getStockMinimo());
            inventarioCreado.setProductoId(inventario.getProductoId());

            return inventarioRepository.save(inventarioCreado);
        }
        return null;
    }

    // Descontar stock tras una compra (se permite stock negativo por modelo de fabricación propia)
    @Transactional
    public Inventario descontarStock(Long productoId, Integer cantidad, Long pedidoId) {
        List<Inventario> registros = inventarioRepository.findByProductoId(productoId);
        
        if (registros.isEmpty()) 
        {
            throw new RuntimeException("No existe un registro de inventario para el Producto ID: " + productoId);
        }
        
        Inventario inventario = registros.get(0);
        int nuevoStock = inventario.getStockActual() - cantidad;
        
        // Alertas lógicas para planta de producción o reabastecimiento
        if (nuevoStock < 0) 
        {
            int unidadesAFabricar = Math.abs(nuevoStock);
            System.out.println("🏭 ALERTA DE PRODUCCIÓN: Stock en negativo para Producto ID " + productoId + 
                               ". Se deben fabricar " + unidadesAFabricar + " unidades para cubrir el pedido ID " + pedidoId);
        } 
        else if (nuevoStock <= inventario.getStockMinimo()) 
        {
            System.out.println("⚠️ ALERTA: El producto " + productoId + " bajó del stock mínimo estipulado.");
        }
        
        inventario.setStockActual(nuevoStock);
        inventario.setFechaActualizacion(LocalDate.now());
        inventario.setPedidoId(pedidoId);
        
        return inventarioRepository.save(inventario);
    }

    // Eliminar Inventario por ID
    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }

    // Eliminar inventario asociado a un producto
    public void eliminarPorProducto(Long productoId) {
        List<Inventario> inventarios = inventarioRepository.findByProductoId(productoId);
        System.out.println("Inventarios encontrados: " + inventarios.size());
        inventarioRepository.deleteAll(inventarios);
    }

    
}