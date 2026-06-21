package com.proper.service_pago.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_pago.model.Pago;
import com.proper.service_pago.repository.PagoRepository;

@Service
public class PagoService 
{
        @Autowired
        private PagoRepository pagoRepository;

        @Autowired
        private WebClient.Builder webClientBuilder;

        public List<Pago> listarTodos()
        {
                List<Pago> lista = pagoRepository.findAll();

                for(Pago pago : lista)
                {
                        enriquecerConPedido(pago);
                }
                return lista;
        }
        
        //Obtener pedido por Id
        public Optional<Pago> buscarPorId(Long id)
        {
                Optional<Pago> pago = pagoRepository.findById(id);

                if(pago.isPresent())
                {
                        enriquecerConPedido(pago.get());
                }
                return pago;
        }

        //Guardar pago
        public Pago guardarPago(Pago pago)
        {
                return pagoRepository.save(pago);
        }

        //Actualizar estado por pedido Id
        public Pago actualizarEstadoPorPedidoId(Long pedidoId, String estadoPago, String metodoPago)
        {
                Pago pago = pagoRepository.findByPedidoId(pedidoId);

                if(pago != null)
                {
                        pago.setEstadoPago(estadoPago);
                        pago.setMetodoPago(metodoPago);

                        Pago pagoGuardado = pagoRepository.save(pago);

                        if ("PAGADO".equalsIgnoreCase(estadoPago)) 
                        {
                                try {
                                        String jsonPedido = webClientBuilder.build()
                                        .get()
                                        .uri("http://localhost:8085/api/v1/pedido/" + pedidoId)
                                        .retrieve()
                                        .bodyToMono(String.class)
                                        .block();

                                        
                                        Long clienteId = null;
                                        if (jsonPedido != null && jsonPedido.contains("\"clienteId\":")) {
                                                String[] partes = jsonPedido.split("\"clienteId\":");
                                                String valor = partes[1].split(",")[0].replaceAll("[^0-9]", "").trim();
                                                clienteId = Long.valueOf(valor);
                                        }

                                        if (clienteId != null) {
                                                Map<String, Object> factura = Map.of(
                                                        "total", pagoGuardado.getMonto(),
                                                        "fechaFacturacion", java.time.LocalDate.now().toString(), // Solución al @NotNull de Facturación
                                                        "estado", "EMITIDA",
                                                        "pedidoId", pedidoId,
                                                        "clienteId", clienteId,
                                                        "pagoId", pagoGuardado.getPagoId()
                                                );

                                                // Enviamos el POST al microservicio de Facturación
                                                webClientBuilder.build()
                                                .post()
                                                .uri("http://localhost:8089/api/v1/facturacion")
                                                .bodyValue(factura)
                                                .retrieve()
                                                .bodyToMono(String.class)
                                                .block();
                                                
                                        }
                                        
                                } catch (Exception e) {
                                       
                                }
                        }

                        return pagoGuardado;
                }
                return null;
        }

        public Pago actualizarMontoPorPedidoId(Long pedidoId, Double nuevoMonto)
        {
                Pago pago = pagoRepository.findByPedidoId(pedidoId);

                if(pago != null)
                {
                        pago.setMonto(nuevoMonto);

                        return pagoRepository.save(pago);
                }

                return null;
        }

        //Eliminar por pedido Id
        public void eliminarPorPedidoId(Long pedidoId)
        {
                Pago pago = pagoRepository.findByPedidoId(pedidoId);

                if(pago != null)
                {
                        pagoRepository.delete(pago);
                }
        }

        //Enriquecemos los datos con pedido
        private Pago enriquecerConPedido(Pago pago)
        {
                if(pago.getPedidoId() != null)
                {
                        try {
                                Object pedido = webClientBuilder.build()
                                .get()
                                .uri("http://localhost:8085/api/v1/pedido/" + pago.getPedidoId()) 
                                .retrieve()
                                .bodyToMono(Object.class)
                                .block();

                                pago.setPedido(pedido);
                        } catch (Exception e) {
                                pago.setPedido(null);
                        }
                }
                return pago;
        }
}