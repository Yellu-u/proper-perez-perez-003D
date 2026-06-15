package com.proper.service_reporte.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_reporte.model.EstadisticaVendedor;
import com.proper.service_reporte.model.Reporte;
import com.proper.service_reporte.repository.ReporteRepository;

@Service
public class ReporteService 
{
    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Reporte> listarTodos()
    {
        return reporteRepository.findAll();
    }

    public Optional<Reporte> buscarPorId(Long id)
    {
        return reporteRepository.findById(id);
    }

    public Reporte generarReporte(LocalDate fechaInicio, LocalDate fechaFin)
    {
        List<Map<String, Object>> pedidos = webClientBuilder.build()
        .get()
        .uri("http://localhost:8085/api/v1/pedido")
        .retrieve()
        .bodyToMono(List.class)
        .block();

        List<Map<String, Object>> pagos = webClientBuilder.build()
        .get()
        .uri("http://localhost:8087/api/v1/pagos")
        .retrieve()
        .bodyToMono(List.class)
        .block();

        List<Map<String, Object>> despachos = webClientBuilder.build()
        .get()
        .uri("http://localhost:8088/api/v1/despachos")
        .retrieve()
        .bodyToMono(List.class)
        .block();

        List<Map<String, Object>> bonificaciones = webClientBuilder.build()
        .get()
        .uri("http://localhost:8086/api/v1/bonificaciones")
        .retrieve()
        .bodyToMono(List.class)
        .block();

        double totalVentas = 0;
        int pagosPendientes = 0;
        int pagosPagados = 0;
        int despachosPendientes = 0;
        int despachosEntregados = 0;
        double totalBonificaciones = 0;

        int totalPedidos;

        if(pedidos != null)
        {
                totalPedidos = pedidos.size();
        }
        else
        {
                totalPedidos = 0;
        }

        if(pagos != null)
        {
            for(Map<String, Object> pago : pagos)
            {
                totalVentas += Double.valueOf(pago.get("monto").toString());

                String estadoPago = pago.get("estadoPago").toString();

                if(estadoPago.equalsIgnoreCase("PENDIENTE"))
                {
                    pagosPendientes++;
                }
                else if(estadoPago.equalsIgnoreCase("PAGADO"))
                {
                    pagosPagados++;
                }
            }
        }

        if(despachos != null)
        {
            for(Map<String, Object> despacho : despachos)
            {
                String estadoDespacho = despacho.get("estadoDespacho").toString();

                if(estadoDespacho.equalsIgnoreCase("PENDIENTE"))
                {
                    despachosPendientes++;
                }
                else if(estadoDespacho.equalsIgnoreCase("ENTREGADO"))
                {
                    despachosEntregados++;
                }
            }
        }

        if(bonificaciones != null)
        {
            for(Map<String, Object> bonificacion : bonificaciones)
            {
                totalBonificaciones += Double.valueOf(bonificacion.get("monto").toString());
            }
        }

        Reporte reporte = new Reporte();

        reporte.setFechaInicio(fechaInicio);
        reporte.setFechaFin(fechaFin);
        reporte.setFechaGeneracion(LocalDate.now());
        reporte.setTotalPedidos(totalPedidos);
        reporte.setTotalVentas(totalVentas);
        reporte.setPagosPendientes(pagosPendientes);
        reporte.setPagosPagados(pagosPagados);
        reporte.setDespachosPendientes(despachosPendientes);
        reporte.setDespachosEntregados(despachosEntregados);
        reporte.setTotalBonificaciones(totalBonificaciones);

        List<EstadisticaVendedor> estadisticas = generarEstadisticasVendedores(pedidos, bonificaciones, reporte);

        reporte.setEstadisticasVendedores(estadisticas);

        return reporteRepository.save(reporte);
    }

    private List<EstadisticaVendedor> generarEstadisticasVendedores(
            List<Map<String, Object>> pedidos,
            List<Map<String, Object>> bonificaciones,
            Reporte reporte)
    {
        List<EstadisticaVendedor> estadisticas = new ArrayList<>();

        if(pedidos == null)
        {
            return estadisticas;
        }

        for(Map<String, Object> pedido : pedidos)
        {
            Long vendedorId = Long.valueOf(pedido.get("vendedorId").toString());

            EstadisticaVendedor estadisticaExistente = null;

            for(EstadisticaVendedor estadistica : estadisticas)
            {
                if(estadistica.getVendedorId().equals(vendedorId))
                {
                    estadisticaExistente = estadistica;
                    break;
                }
            }

            if(estadisticaExistente == null)
            {
                Object vendedor = webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/v1/vendedores/" + vendedorId)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

                Map<String, Object> vendedorMap = (Map<String, Object>) vendedor;

                String nombreVendedor =
                        vendedorMap.get("nombreVendedor").toString()
                        + " "
                        + vendedorMap.get("apellidoVendedor").toString();

                EstadisticaVendedor nueva = new EstadisticaVendedor();

                nueva.setVendedorId(vendedorId);
                nueva.setNombreVendedor(nombreVendedor);
                nueva.setTotalPedidos(0);
                nueva.setTotalVentas(0.0);
                nueva.setTotalBonificaciones(0.0);
                nueva.setPromedioVenta(0.0);
                nueva.setReporte(reporte);

                estadisticas.add(nueva);

                estadisticaExistente = nueva;
            }

            estadisticaExistente.setTotalPedidos(estadisticaExistente.getTotalPedidos() + 1);

            double totalPedido = 0;

            List<Map<String, Object>> detalles =(List<Map<String, Object>>) pedido.get("detalles");

            if(detalles != null)
            {
                for(Map<String, Object> detalle : detalles)
                {
                    totalPedido += Double.valueOf(detalle.get("subtotal").toString());
                }
            }

            estadisticaExistente.setTotalVentas(estadisticaExistente.getTotalVentas() + totalPedido);
        }

        if(bonificaciones != null)
        {
            for(Map<String, Object> bonificacion : bonificaciones)
            {
                Long vendedorId = Long.valueOf(bonificacion.get("vendedorId").toString());
                Double monto = Double.valueOf(bonificacion.get("monto").toString());

                for(EstadisticaVendedor estadistica : estadisticas)
                {
                    if(estadistica.getVendedorId().equals(vendedorId))
                    {
                        estadistica.setTotalBonificaciones(estadistica.getTotalBonificaciones() + monto);
                    }
                }
            }
        }

        for(EstadisticaVendedor estadistica : estadisticas)
        {
            if(estadistica.getTotalPedidos() > 0)
            {
                estadistica.setPromedioVenta(estadistica.getTotalVentas() / estadistica.getTotalPedidos());
            }
        }

        return estadisticas;
    }

    public void eliminarReporte(Long id)
    {
        reporteRepository.deleteById(id);
    }
}