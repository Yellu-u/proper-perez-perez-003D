package com.proper.service_bonificacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_bonificacion.model.Bonificacion;
import com.proper.service_bonificacion.repository.BonificacionRepository;

@Service
public class BonificacionService {

    @Autowired
    private BonificacionRepository bonificacionRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    

    public List<Bonificacion> listarTodas(){
        return bonificacionRepository.findAll();
    }


    //Obtener pedido por Id
    public Optional<Bonificacion> buscarPorId(Long id)
    {
        return bonificacionRepository.findById(id).map(this::enriquecerConPedidoYVendedor);

    }


    public Bonificacion guardarBonificacion(Bonificacion bonificacion){
    
        //Se valida si el Vendedor Existe antes de guardar
        if(bonificacion.getVendedorId()!=null){
            Object vendedor = webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/v1/vendedores/" + bonificacion.getVendedorId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            bonificacion.setVendedor(vendedor);
        }

        //Se valida si el Pedido Existe antes de guardar
        if (bonificacion.getPedidoId()!= null){
            Object pedido = webClientBuilder.build()
                .get()
                .uri("http://localhost:8087/api/v1/pedidos/" + bonificacion.getPedidoId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            bonificacion.setPedido(pedido);
        }

        //Guarda y Devuelve la bonificacion enriquecida
        return bonificacionRepository.save(bonificacion);
    }

    
    //Enriquecer los datos 
    private Bonificacion enriquecerConPedidoYVendedor(Bonificacion bonificacion) {
        if (bonificacion.getVendedorId() != null) {
            Object vendedor = webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/v1/vendedores/" + bonificacion.getVendedorId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            bonificacion.setVendedor(vendedor);
        }
        if (bonificacion.getPedidoId() != null) {
            Object pedido = webClientBuilder.build()
                .get()
                .uri("http://localhost:8087/api/v1/pedidos/" + bonificacion.getPedidoId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            bonificacion.setPedido(pedido);
        }

        return bonificacion;
    }

    //Guardar pedido 


    public void eliminar(Long bonificacionId) {
        bonificacionRepository.deleteById(bonificacionId);
    }


}