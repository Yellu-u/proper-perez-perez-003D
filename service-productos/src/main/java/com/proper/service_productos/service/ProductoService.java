package com.proper.service_productos.service;

import com.proper.service_productos.repository.LineaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proper.service_productos.model.Linea;
import com.proper.service_productos.model.Producto;
import com.proper.service_productos.repository.ProductoRepository;

@Service
public class ProductoService 
{
    @Autowired
    private LineaRepository lineaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    //Se buscan todos los productos existentes
    public List<Producto> listarTodosProductos()
    {
        return productoRepository.findAll();
    }

    //Se busca producto por id
    public Optional<Producto> buscarProductoPorId(Long id)
    {
        return productoRepository.findById(id);
    }

    //Se buscan todos las lineas existentes
    public List<Linea> listarTodasLineas()
    {
        return lineaRepository.findAll();
    }

    //Se busca linea por id
    public Optional<Linea> buscarLineaPorId(Long id)
    {
        return lineaRepository.findById(id);
    }

    //Se guarda producto
    public Producto guardarProducto(Producto producto)
    {
        return productoRepository.save(producto);
    }

    //Se guarda linea
    public Linea guardarLinea(Linea linea)
    {
        return lineaRepository.save(linea);
    }

    //Se elimina producto
    public void eliminarProducto(Long id)
    {
        productoRepository.deleteById(id);
    }

    //Se elimina linea
    public void eliminarLinea(Long id)
    {
        lineaRepository.deleteById(id);
    }

    //Se actuliza producto
    public Producto actualizarProducto(Long id, Producto producto)
    {
        Producto productoExistente = productoRepository.findById(id).orElse(null);

        if(productoExistente != null)
        {
            productoExistente.setProductoNombre(producto.getProductoNombre());
            productoExistente.setPrecio(producto.getPrecio());
            productoExistente.setLinea(producto.getLinea());

            return productoRepository.save(productoExistente);
        }
        return null;
    }

    //Se actuliza linea
    public Linea actualizarLinea(Long id, Linea linea)
    {
        Linea lineaExistente = lineaRepository.findById(id).orElse(null);

        if(lineaExistente != null)
        {
            lineaExistente.setNombre(linea.getNombre());;

            return lineaRepository.save(lineaExistente);
        }
        return null;
    }
}