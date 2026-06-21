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


                // AUTOMATIZACIÓN DE STOCK: Comunicación con Microservicio Inventario
                if (pedidoGuardado.getDetalles() != null) {
                        for(DetallePedido detalle : pedidoGuardado.getDetalles()) {
                                webClientBuilder.build()
                                .put()
                                .uri("http://localhost:8088/api/v1/inventario/producto/" 
                                     + detalle.getProductoId() + "/descontar?cantidad=" 
                                     + detalle.getCantidad() + "&pedidoId=" + pedidoGuardado.getPedidoId())
                                .retrieve()
                                .bodyToMono(Object.class)
                                .block();
                        }
                }

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

                //Creamos el objeto pago
                Map<String, Object> pago = Map.of(
                        "monto", total,
                        "metodoPago", "PENDIENTE",
                        "estadoPago", "PENDIENTE",
                        "fechaPago", LocalDate.now(),
                        "pedidoId", pedidoGuardado.getPedidoId()
                );

                //Se envia a microservicio pago
                webClientBuilder.build()
                .post()
                .uri("http://localhost:8087/api/v1/pagos")
                .bodyValue(pago)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

                //Se consulta el objeto de cliente
                Object datosCliente = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/api/v1/clientes/" + pedidoGuardado.getClienteId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();

                Map<String, Object> clienteMap = (Map<String, Object>) datosCliente;

                Map<String, Object> empresaMap = (Map<String, Object>) clienteMap.get("empresa");

                //a través del map empresa sacamos la dirección que irá asociada al despacho
                String direccionEmpresa = empresaMap.get("direccionEmpresa").toString();

                //Creamos una regla de negocio para la estimación de la fecha
                int cantidadTotal = 0;

                for(DetallePedido detalle : pedidoGuardado.getDetalles())
                {
                        cantidadTotal += detalle.getCantidad();
                }

                LocalDate fechaEstimada;

                if(cantidadTotal <= 20) //Si la cantidad de productos es menor o = a 20 se estima un tiempo de 3 días
                {
                        fechaEstimada = LocalDate.now().plusDays(3);
                }
                else if(cantidadTotal <= 50) //Si la cantidad de productos es menor o = a 50 se estima un tiempo de 5 días
                {
                        fechaEstimada = LocalDate.now().plusDays(5);
                }
                else
                {
                        fechaEstimada = LocalDate.now().plusDays(7);
                }

                //Se crea el objeto despacho
                Map<String, Object> despacho = Map.of(
                        "direccionEntrega", direccionEmpresa,
                        "fechaEstimada", fechaEstimada,
                        "estadoDespacho", "PENDIENTE",
                        "pedidoId", pedidoGuardado.getPedidoId()
                );

                //Se envia a microservicio despacho
                webClientBuilder.build()
                .post()
                .uri("http://localhost:8090/api/v1/despachos")
                .bodyValue(despacho)
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

                                        //Descontar nuevas cantidades en inventario
                                        webClientBuilder.build()
                                        .put()
                                        .uri("http://localhost:8088/api/v1/inventario/producto/" 
                                             + detalle.getProductoId() + "/descontar?cantidad=" 
                                             + detalle.getCantidad() + "&pedidoId=" + pedidoCreado.getPedidoId())
                                        .retrieve()
                                        .bodyToMono(Object.class)
                                        .block();
                                        
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

                        //Objeto para actualizar pago
                        Map<String, Object> pago = Map.of(
                                        "monto", total
                        );

                        webClientBuilder.build()
                        .put()
                        .uri("http://localhost:8087/api/v1/pagos/pedido/" + pedidoActualizado.getPedidoId() + "/monto")
                        .bodyValue(pago)
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
                //Eliminar bonificación asociada
                webClientBuilder.build()
                .delete()
                .uri("http://localhost:8086/api/v1/bonificaciones/pedido/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

                webClientBuilder.build()
                .delete()
                .uri("http://localhost:8087/api/v1/pagos/pedido/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

                webClientBuilder.build()
                .delete()
                .uri("http://localhost:8090/api/v1/despachos/pedido/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

                //Eliminar pedido
                pedidoRepository.deleteById(id);
        }
}