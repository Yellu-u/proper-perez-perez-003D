package com.proper.service_inventario.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_inventario.model.Inventario;
import com.proper.service_inventario.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private InventarioRepository inventarioRepository;



    //Obtener Inventario Completo
    public List<Inventario> obtenerInventario()
    {
        return inventarioRepository.findAll();
    }

    //Buscar inventario por producto
    public List<Inventario> obtenerPorProducto(Long productoId)
    {
        return inventarioRepository.findByProductoId(productoId);
        
    }

    //Obtener Inventario de producto por Id
    public Inventario obtenerInventarioPorId(Long id)
    {
        return inventarioRepository.findById(id).orElse(null);
    }


    //Crear Inventario de Producto
    public  Inventario guardarInventario(Inventario inventario)
    {
        //Verificar si el producto existe
        Object producto = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/api/v1/productos/" + inventario.getProductoId())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            
        if(producto == null){
            return null;
        }
        inventario.setFechaActualizacion(LocalDate.now());
        return inventarioRepository.save(inventario);
    }



    //Actualizar el Inventario por producto y Id
    public Inventario actualizarInventario(Long id, Inventario inventario)
    {
        Inventario invetarioCreado = inventarioRepository.findById(id).orElse(inventario);
        if(invetarioCreado != null)
        {
            invetarioCreado.setFechaActualizacion(inventario.getFechaActualizacion());
            invetarioCreado.setStockActual(inventario.getStockActual());
            invetarioCreado.setStockMinimo(inventario.getStockMinimo());
            invetarioCreado.setProductoId(inventario.getProductoId());

            return inventarioRepository.save(invetarioCreado);
        }

        return null;
    }


    //Eliminar Inventario por ID
    public void eliminarInventario(Long id)
    {
        inventarioRepository.deleteById(id);
    }

    //Eliminar inventario asociado a un producto
    public void eliminarPorProducto(Long productoId)
    {
        List<Inventario> inventarios =
                inventarioRepository.findByProductoId(productoId);

        System.out.println("Inventarios encontrados: " + inventarios.size());

        inventarioRepository.deleteAll(inventarios);
    }



}





