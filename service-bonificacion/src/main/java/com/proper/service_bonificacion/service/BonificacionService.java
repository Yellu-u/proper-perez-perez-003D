package com.proper.service_bonificacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_bonificacion.model.Bonificacion;
import com.proper.service_bonificacion.repository.BonificacionRepository;

@Service
public class BonificacionService 
{

    @Autowired
    private BonificacionRepository bonificacionRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    

    public List<Bonificacion> listarTodas()
    {
        List<Bonificacion> lista = bonificacionRepository.findAll();

        for(Bonificacion bonificacion : lista)
        {
            enriquecerConPedidoYVendedor(bonificacion);
        }

        return lista;
    }


    //Obtener pedido por Id
    public Optional<Bonificacion> buscarPorId(Long id)
    {
        Optional<Bonificacion> bonificacion = bonificacionRepository.findById(id);

        if(bonificacion.isPresent())
        {
            enriquecerConPedidoYVendedor(bonificacion.get());
        }

        return bonificacion;
    }


    public Bonificacion guardarBonificacion(Bonificacion bonificacion)
    {
        return bonificacionRepository.save(bonificacion);
    }

    
    //Enriquecer los datos 
    private Bonificacion enriquecerConPedidoYVendedor(Bonificacion bonificacion) 
    {
        if (bonificacion.getVendedorId() != null) 
        {
            Object vendedor = webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/v1/vendedores/" + bonificacion.getVendedorId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            bonificacion.setVendedor(vendedor);
        }
        if (bonificacion.getPedidoId() != null) 
        {
            Object pedido = webClientBuilder.build()
                .get()
                .uri("http://localhost:8085/api/v1/pedido/" + bonificacion.getPedidoId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            bonificacion.setPedido(pedido);
        }

        return bonificacion;
    }

    //Actualizamos bonificación por pedido
    public Bonificacion actualizarPorPedidoId(Long pedidoId, Double nuevoMonto)
    {
        Bonificacion bonificacion = bonificacionRepository.findByPedidoId(pedidoId);

        if(bonificacion != null)
        {
            bonificacion.setMonto(nuevoMonto);

            return bonificacionRepository.save(bonificacion);
        }

        return null;
    }   

    //eliminamos bonificación
    public void eliminar(Long pedidoId) 
    {
        Bonificacion bonificacion = bonificacionRepository.findByPedidoId(pedidoId);

        if(bonificacion != null)
        {
            bonificacionRepository.delete(bonificacion);
        }
    }
}