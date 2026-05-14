package com.proper.service_pedido.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_pedido.model.DetallePedido;
import com.proper.service_pedido.model.Pedido;
import com.proper.service_pedido.repository.PedidoRepository;

@Service
public class PedidoService 
{
        @Autowired
        private WebClient.Builder webClientBuilder;

        @Autowired
        private PedidoRepository pedidoRepository;

        //Obtener todos los pedidos
        public List<Pedido> obtenerPedidos()
        {
                return pedidoRepository.findAll();
        }

        //Buscar pedido por vendedor
        public List<Pedido> obtenerPorVendedor(Long vendedorId)
        {
                return pedidoRepository.findByVendedorId(vendedorId);
        }

        //Buscar pedido por cliente
        public List<Pedido> obtenerPorCliente(Long clienteId)
        {
                return pedidoRepository.findByClienteId(clienteId);
        }

        //Obtener pedido por id
        public Pedido obtenerPedidoPorId(Long id)
        {
                return pedidoRepository.findById(id).orElse(null);
        }

        //Guardar pedido con detalles
        public Pedido guardarPedido(Pedido pedido)
        {
                //Se verifica si vienen detalles
                if (pedido.getDetalles() != null)
                {
                        for(DetallePedido detalle : pedido.getDetalles())
                        {
                                detalle.setPedido(pedido);

                                //Buscamos producto desde otro microservicio
                                Object datosProducto = webClientBuilder.build()
                                .get()
                                .uri("http://localhost:8082/api/v1/productos/" + detalle.getProductoId())
                                .retrieve()
                                .bodyToMono(Object.class)
                                .block();

                                //Convertimos el objeto a map para sacar el precio
                                Map<String, Object> productoMap =
                                (Map<String, Object>) datosProducto;

                                //Obtenemos el precio
                                Double precio = Double.valueOf(
                                productoMap.get("precio").toString());

                                //Se asigna el precio unitario
                                detalle.setPrecioUnitario(precio);

                                //Se calcula el subtotal
                                detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
                        }
                }
                //Guardamos primero el pedido 
                Pedido pedidoGuardado = pedidoRepository.save(pedido);

                //Calculamos el total del pedido
                double total = 0;

                for(DetallePedido detalle : pedidoGuardado.getDetalles())
                {
                        total += detalle.getSubtotal();
                }

                //Se calcula la bonificación
                double montoBonificacion = total * 0.1;

                //Creamos el objeto bonificación
                Map<String, Object> bonificacion = Map.of
                (
                        "monto", montoBonificacion,
                        "fecha", LocalDate.now(),
                        "vendedorId", pedidoGuardado.getVendedorId(),
                        "pedidoId", pedidoGuardado.getPedidoId()
                );

                //Se envia a microservicio bonificación
                webClientBuilder.build()
                .post()
                .uri("http://localhost:8086/api/v1/bonificaciones")
                .bodyValue(bonificacion)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

                return pedidoGuardado;
        }

        //Actualizar pedido
        public Pedido actualizarPedido(Long id, Pedido pedido)
        {
                Pedido pedidoCreado = pedidoRepository.findById(id).orElse(null);

                if(pedidoCreado != null)
                {
                        //Actualizamos el pedido que ya está creado
                        pedidoCreado.setFecha(pedido.getFecha());
                        pedidoCreado.setEstado(pedido.getEstado());
                        pedidoCreado.setClienteId(pedido.getClienteId());
                        pedidoCreado.setVendedorId(pedido.getVendedorId());

                        //Borramos los detalles antiguos
                        pedidoCreado.getDetalles().clear();

                        //Agregamos nuevos detalles
                        if(pedido.getDetalles() != null)
                        {
                                for(DetallePedido detalle : pedido.getDetalles())
                                {
                                        
                                        detalle.setPedido(pedidoCreado);

                                        //Buscamos producto desde otro microservicio
                                        Object datosProducto = webClientBuilder.build()
                                        .get()
                                        .uri("http://localhost:8082/api/v1/productos/" + detalle.getProductoId())
                                        .retrieve()
                                        .bodyToMono(Object.class)
                                        .block();

                                        //Convertimos el objeto a map para sacar el precio
                                        Map<String, Object> productoMap = (Map<String, Object>) datosProducto;

                                        Double precio = Double.valueOf(productoMap.get("precio").toString());

                                        //Se asigna el precio unitario
                                        detalle.setPrecioUnitario(precio);

                                        //Se calcula el subtotal
                                        detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());

                                        pedidoCreado.getDetalles().add(detalle);
                                }
                        }
                        //Guardamos pedido actualizado
                        Pedido pedidoActualizado = pedidoRepository.save(pedidoCreado);

                        //Calculamos total
                        double total = 0;

                        for(DetallePedido detallePedido : pedidoActualizado.getDetalles())
                        {
                                total += detallePedido.getSubtotal();
                        }

                        //Calculamos nueva bonificacion
                        double montoBonificacion = total * 0.1;

                        //Objeto para actualizar bonificación
                        Map<String, Object> bonificacion = Map.of(
                        "monto", montoBonificacion
                        );

                        webClientBuilder.build()
                        .put()
                        .uri("http://localhost:8086/api/v1/bonificaciones/pedido/"
                                + pedidoActualizado.getPedidoId())
                        .bodyValue(bonificacion)
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();

                        return pedidoActualizado;
                }
                return null;
        }

        //Eliminar pedido
        public void eliminarPedido(Long id)
        {
                pedidoRepository.deleteById(id);
        }
}