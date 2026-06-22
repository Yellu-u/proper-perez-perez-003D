package com.proper.service_reporte.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



import java.time.LocalDate;
import java.util.List;
import java.util.Map;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_reporte.model.Reporte;
import com.proper.service_reporte.repository.ReporteRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {
    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    void generarReporteTest() {
        // --- 1. PREPARACIÓN ---
        LocalDate inicio = LocalDate.of(2026, 6, 1);
        LocalDate fin = LocalDate.of(2026, 6, 30);
        Long pedidoId = 99L;
        Long vendedorId = 7L;

        // Mapas de prueba simulando lo que entregan los otros microservicios
        Map<String, Object> detalle = Map.of("subtotal", 150.0);
        List<Map<String, Object>> listaPedidos = List.of(Map.of(
            "pedidoId", pedidoId, "vendedorId", vendedorId, "fecha", "2026-06-15", "detalles", List.of(detalle)
        ));

        List<Map<String, Object>> listaPagos = List.of(Map.of(
            "pedidoId", pedidoId, "monto", 150.0, "estadoPago", "PAGADO"
        ));

        List<Map<String, Object>> listaDespachos = List.of(Map.of(
            "pedidoId", pedidoId, "estadoDespacho", "ENTREGADO"
        ));

        List<Map<String, Object>> listaBonif = List.of(Map.of(
            "vendedorId", vendedorId, "monto", 50.0, "fechaEmision", "2026-06-20"
        ));

        Map<String, Object> vendedor = Map.of(
            "nombreVendedor", "Carlos", "apellidoVendedor", "Mendoza"
        );

        // Mock de la cadena de WebClient EXACTAMENTE como sale en tu Word
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        
        // ¡Tal cual el Word! Aceptamos cualquier URL
        when(uriSpec.uri(anyString())).thenReturn(headersSpec); 
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        // Como tu código hace 4 llamadas seguidas a List.class, se las entregamos en orden
        when(responseSpec.bodyToMono(List.class)).thenReturn(
            Mono.just(listaPedidos),     // 1ra llamada: pedidos
            Mono.just(listaPagos),       // 2da llamada: pagos
            Mono.just(listaDespachos),   // 3ra llamada: despachos
            Mono.just(listaBonif)        // 4ta llamada: bonificaciones
        );

        // Como tu código hace 1 llamada a Map.class para buscar al vendedor
        when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(vendedor));

        // Mock del repositorio
        when(reporteRepository.save(any(Reporte.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // --- 2. EJECUCIÓN ---
        Reporte resultado = reporteService.generarReporte(inicio, fin);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalPedidos());
        assertEquals(150.0, resultado.getTotalVentas());
        assertEquals(1, resultado.getPagosPagados());
        assertEquals(1, resultado.getDespachosEntregados());
        assertEquals(50.0, resultado.getTotalBonificaciones());
        
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

}
