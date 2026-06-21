package com.proper.service_despacho.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_despacho.model.Despacho;
import com.proper.service_despacho.repository.DespachoRepository;

@Service
public class DespachoService 
{
        @Autowired
        private DespachoRepository despachoRepository;

        @Autowired
        private WebClient.Builder webClientBuilder;

        //Listar todos los despachos
        public List<Despacho> listarTodos()
        {
                List<Despacho> lista = despachoRepository.findAll();

                for(Despacho despacho : lista)
                {
                        enriquecerConPedido(despacho);
                }

                return lista;
        }

        //Buscar despacho por id
        public Optional<Despacho> buscarPorId(Long id)
        {
                Optional<Despacho> despacho = despachoRepository.findById(id);

                if(despacho.isPresent())
                {
                        enriquecerConPedido(despacho.get());
                }
                return despacho;
        }

        //Guardar despacho
        public Despacho guardarDespacho(Despacho despacho)
        {
                return despachoRepository.save(despacho);
        }

        //Actualizar estado del despacho según pedidoId
        public Despacho actualizarEstadoPorPedidoId(Long pedidoId, String estadoDespacho)
        {
                Despacho despacho = despachoRepository.findByPedidoId(pedidoId);

                if(despacho != null)
                {
                        despacho.setEstadoDespacho(estadoDespacho);

                        return despachoRepository.save(despacho);
                }
                return null;
        }

        //Eliminar despacho asociado a un pedido
        public void eliminarPorPedidoId(Long pedidoId)
        {
                Despacho despacho = despachoRepository.findByPedidoId(pedidoId);

                if(despacho != null)
                {
                        despachoRepository.delete(despacho);
                }
        }

        //Enriquecer datos con información del pedido
        private Despacho enriquecerConPedido(Despacho despacho)
        {
                if(despacho.getPedidoId() != null)
                {
                        Object pedido = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8085/api/v1/pedido/" + despacho.getPedidoId())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();

                        despacho.setPedido(pedido);
                }
                return despacho;
        }
}
