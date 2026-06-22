package com.proper.service_bonificacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_bonificacion.model.Bonificacion;
import com.proper.service_bonificacion.model.PedidoDTO;
import com.proper.service_bonificacion.model.VendedorDTO;
import com.proper.service_bonificacion.repository.BonificacionRepository;

import reactor.core.publisher.Mono;


@ExtendWith(MockitoExtension.class)
public class BonificacionServiceTest {
    
    @Mock
    private BonificacionRepository bonificacionRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private BonificacionService bonificacionService;

    @Test
    @DisplayName("Debería buscar una bonificación por ID y enriquecerla usando DTOs")
    void buscarPorIdConEnriquecimientoDTOTest() {
        // --- 1. PREPARACIÓN (Escenario) ---
        Long bonificacionId = 1L;
        Long vendedorId = 5L;
        Long pedidoId = 101L;

        // Instanciamos el objeto local Bonificacion
        Bonificacion bonificacionMock = new Bonificacion();
        bonificacionMock.setBonificacionId(bonificacionId);
        bonificacionMock.setMonto(25000.50);
        bonificacionMock.setFecha(LocalDate.now());
        bonificacionMock.setVendedorId(vendedorId);
        bonificacionMock.setPedidoId(pedidoId);

        // Instanciamos los DTOs simulados (Tal cual la guía de la profesora)
        VendedorDTO vendedorMockExternal = new VendedorDTO(vendedorId, "Ana María Silva");
        PedidoDTO pedidoMockExternal = new PedidoDTO(pedidoId, "PROCESADO");

        // Mock del repositorio local
        when(bonificacionRepository.findById(bonificacionId)).thenReturn(Optional.of(bonificacionMock));

        // Mock de la cadena de WebClient (Estructura idéntica al Word)
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec); 
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        
        // Entregamos los DTOs ordenados según las llamadas del Service (1° Vendedor, 2° Pedido)
        when(responseSpec.bodyToMono(Object.class)).thenReturn(
            Mono.just(vendedorMockExternal), 
            Mono.just(pedidoMockExternal)    
        );

        // --- 2. EJECUCIÓN (When) ---
        Optional<Bonificacion> resultadoOptional = bonificacionService.buscarPorId(bonificacionId);

        // --- 3. VERIFICACIÓN (Then) ---
        assertTrue(resultadoOptional.isPresent(), "La bonificación debe existir");
        Bonificacion resultado = resultadoOptional.get();
        
        // Verificamos que los objetos transitorios se hayan adjuntado con éxito
        assertNotNull(resultado.getVendedor(), "El objeto vendedor no debe ser nulo");
        assertNotNull(resultado.getPedido(), "El objeto pedido no debe ser nulo");

        // Convertimos el Object al DTO real para verificar sus propiedades internas de forma segura
        VendedorDTO datosVendedor = (VendedorDTO) resultado.getVendedor();
        assertEquals("Ana María Silva", datosVendedor.getNombreVendedor());
        assertEquals(vendedorId, datosVendedor.getVendedorId());

        PedidoDTO datosPedido = (PedidoDTO) resultado.getPedido();
        assertEquals("PROCESADO", datosPedido.getEstado());
        assertEquals(pedidoId, datosPedido.getPedidoId());

        // Verificamos que la BD local se haya consultado exactamente una vez
        verify(bonificacionRepository, times(1)).findById(bonificacionId);
    }
    
}
