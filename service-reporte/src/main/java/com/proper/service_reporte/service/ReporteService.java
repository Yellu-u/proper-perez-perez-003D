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
        // 1. Descarga de datos crudos utilizando los puertos oficiales de tu ecosistema
        List<Map<String, Object>> todosLosPedidos = webClientBuilder.build()
        .get()
        .uri("http://localhost:8085/api/v1/pedido") // Pedidos en 8085
        .retrieve()
        .bodyToMono(List.class)
        .block();

        List<Map<String, Object>> todosLosPagos = webClientBuilder.build()
        .get()
        .uri("http://localhost:8087/api/v1/pagos") // Pagos en 8087
        .retrieve()
        .bodyToMono(List.class)
        .block();

        List<Map<String, Object>> todosLosDespachos = webClientBuilder.build()
        .get()
        .uri("http://localhost:8090/api/v1/despachos") // Despachos corregido a 8090
        .retrieve()
        .bodyToMono(List.class)
        .block();

        List<Map<String, Object>> todasLasBonificaciones = webClientBuilder.build()
        .get()
        .uri("http://localhost:8086/api/v1/bonificaciones") // Bonificaciones en 8086
        .retrieve()
        .bodyToMono(List.class)
        .block();

        // Variables de acumulación para el reporte gerencial
        List<Map<String, Object>> pedidosFiltrados = new ArrayList<>();
        double totalVentas = 0;
        int pagosPendientes = 0;
        int pagosPagados = 0;
        int despachosPendientes = 0;
        int despachosEntregados = 0;
        double totalBonificaciones = 0;

        // FILTRADO DE PEDIDOS POR EL RANGO SOLICITADO
        if (todosLosPedidos != null) {
            for (Map<String, Object> ped : todosLosPedidos) {
                if (ped.get("fecha") != null) {
                    LocalDate fechaPed = LocalDate.parse(ped.get("fecha").toString());
                    if (!fechaPed.isBefore(fechaInicio) && !fechaPed.isAfter(fechaFin)) {
                        pedidosFiltrados.add(ped);
                    }
                }
            }
        }

        int totalPedidos = pedidosFiltrados.size();

        // CRUCE Y CONTEO DE FINANZAS (PAGOS)
        if (todosLosPagos != null) {
            for (Map<String, Object> pago : todosLosPagos) {
                if (pago.get("pedidoId") != null) {
                    Long pedIdPago = Long.valueOf(pago.get("pedidoId").toString());
                    
                    boolean perteneceAlRango = pedidosFiltrados.stream()
                        .anyMatch(p -> Long.valueOf(p.get("pedidoId").toString()).equals(pedIdPago));

                    if (perteneceAlRango) {
                        totalVentas += Double.valueOf(pago.get("monto").toString());
                        String estadoPago = pago.get("estadoPago").toString();

                        if (estadoPago.equalsIgnoreCase("PENDIENTE")) {
                            pagosPendientes++;
                        } else if (estadoPago.equalsIgnoreCase("PAGADO")) {
                            pagosPagados++;
                        }
                    }
                }
            }
        }

        // CRUCE Y CONTEO LOGÍSTICO (DESPACHOS)
        if (todosLosDespachos != null) {
            for (Map<String, Object> despacho : todosLosDespachos) {
                if (despacho.get("pedidoId") != null) {
                    Long pedIdDesp = Long.valueOf(despacho.get("pedidoId").toString());
                    
                    boolean perteneceAlRango = pedidosFiltrados.stream()
                        .anyMatch(p -> Long.valueOf(p.get("pedidoId").toString()).equals(pedIdDesp));

                    if (perteneceAlRango) {
                        String estadoDespacho = despacho.get("estadoDespacho").toString();
                        if (estadoDespacho.equalsIgnoreCase("PENDIENTE")) {
                            despachosPendientes++;
                        } else if (estadoDespacho.equalsIgnoreCase("ENTREGADO")) {
                            despachosEntregados++;
                        }
                    }
                }
            }
        }

        // CONTEO DE BONIFICACIONES EN EL PERÍODO
        if (todasLasBonificaciones != null) {
            for (Map<String, Object> bonif : todasLasBonificaciones) {
                if (bonif.get("fechaEmision") != null) { 
                    LocalDate fechaBonif = LocalDate.parse(bonif.get("fechaEmision").toString());
                    if (!fechaBonif.isBefore(fechaInicio) && !fechaBonif.isAfter(fechaFin)) {
                        totalBonificaciones += Double.valueOf(bonif.get("monto").toString());
                    }
                } else {
                    totalBonificaciones += Double.valueOf(bonif.get("monto").toString());
                }
            }
        }

        // 3. Empaquetado del informe de KPIs
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

        // Generar el desglose estadístico por ejecutivo comercial
        List<EstadisticaVendedor> estadisticas = generarEstadisticasVendedores(pedidosFiltrados, todasLasBonificaciones, reporte, fechaInicio, fechaFin);
        reporte.setEstadisticasVendedores(estadisticas);

        return reporteRepository.save(reporte);
    }

    private List<EstadisticaVendedor> generarEstadisticasVendedores(
            List<Map<String, Object>> pedidos,
            List<Map<String, Object>> bonificaciones,
            Reporte reporte,
            LocalDate fechaInicio,
            LocalDate fechaFin)
    {
        List<EstadisticaVendedor> estadisticas = new ArrayList<>();

        if (pedidos == null) {
            return estadisticas;
        }

        for (Map<String, Object> pedido : pedidos) {
            Long vendedorId = Long.valueOf(pedido.get("vendedorId").toString());
            EstadisticaVendedor estadisticaExistente = null;

            for (EstadisticaVendedor estadistica : estadisticas) {
                if (estadistica.getVendedorId().equals(vendedorId)) {
                    estadisticaExistente = estadistica;
                    break;
                }
            }

            if (estadisticaExistente == null) {
                // Buscamos el vendedor en el puerto 8084
                Map<String, Object> vendedorMap = webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/v1/vendedores/" + vendedorId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

                String nombreVendedor = "Vendedor " + vendedorId;
                if (vendedorMap != null) {
                    nombreVendedor = vendedorMap.get("nombreVendedor").toString() 
                                     + " " 
                                     + vendedorMap.get("apellidoVendedor").toString();
                }

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
            List<Map<String, Object>> detalles = (List<Map<String, Object>>) pedido.get("detalles");

            if (detalles != null) {
                for (Map<String, Object> detalle : detalles) {
                    totalPedido += Double.valueOf(detalle.get("subtotal").toString());
                }
            }
            estadisticaExistente.setTotalVentas(estadisticaExistente.getTotalVentas() + totalPedido);
        }

        // Asignar bonificaciones cruzando el rango temporal
        if (bonificaciones != null) {
            for (Map<String, Object> bonificacion : bonificaciones) {
                Long vendedorId = Long.valueOf(bonificacion.get("vendedorId").toString());
                Double monto = Double.valueOf(bonificacion.get("monto").toString());
                
                boolean aplicar = true;
                if (bonificacion.get("fechaEmision") != null) {
                    LocalDate fBonif = LocalDate.parse(bonificacion.get("fechaEmision").toString());
                    if (fBonif.isBefore(fechaInicio) || fBonif.isAfter(fechaFin)) {
                        aplicar = false;
                    }
                }

                if (aplicar) {
                    for (EstadisticaVendedor estadistica : estadisticas) {
                        if (estadistica.getVendedorId().equals(vendedorId)) {
                            estadistica.setTotalBonificaciones(estadistica.getTotalBonificaciones() + monto);
                        }
                    }
                }
            }
        }

        // Calcular ticket promedio
        for (EstadisticaVendedor estadistica : estadisticas) {
            if (estadistica.getTotalPedidos() > 0) {
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