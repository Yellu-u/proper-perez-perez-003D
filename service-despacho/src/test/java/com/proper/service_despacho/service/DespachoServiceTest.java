package com.proper.service_despacho.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_despacho.model.Despacho;
import com.proper.service_despacho.model.PedidoDTO;
import com.proper.service_despacho.repository.DespachoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class DespachoServiceTest {
    @Mock
    private DespachoRepository despachoRepository; // Simulamos la BD local de despachos

    @Mock
    private WebClient.Builder webClientBuilder; // Simulamos el cliente HTTP

    @InjectMocks
    private DespachoService despachoService; // Inyectamos los mocks en el servicio real

    @Test
    @DisplayName("Debería buscar un despacho por ID y enriquecerlo con su Pedido usando DTOs")
    void buscarPorIdConEnriquecimientoPedidoTest() {
        // --- 1. PREPARACIÓN (Escenario) ---
        Long despachoId = 1L;
        Long pedidoId = 202L;

        // Instanciamos el objeto local Despacho simulado
        Despacho despachoMock = new Despacho();
        despachoMock.setDespachoId(despachoId);
        despachoMock.setPedidoId(pedidoId);
        despachoMock.setEstadoDespacho("EN_RUTA");

        // Instanciamos el DTO simulado con los datos externos del Pedido
        PedidoDTO pedidoMockExternal = new PedidoDTO(pedidoId, "PAGADO", 45000.0);

        // Configuramos el comportamiento del repositorio local
        when(despachoRepository.findById(despachoId)).thenReturn(Optional.of(despachoMock));

        // Mock de la cadena de WebClient (Estructura idéntica a la de tu Word)
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec); // anyString() evita caídas por URLs
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        
        // Retornamos el DTO del pedido envuelto en un Mono
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(pedidoMockExternal));

        // --- 2. EJECUCIÓN (When) ---
        Optional<Despacho> resultadoOptional = despachoService.buscarPorId(despachoId);

        // --- 3. VERIFICACIÓN (Then) ---
        // Comprobamos que el despacho exista
        assertTrue(resultadoOptional.isPresent(), "El despacho deberia existir en la BD");
        Despacho resultado = resultadoOptional.get();
        
        // Verificamos que el objeto transitorio 'pedido' no esté vacío
        assertNotNull(resultado.getPedido(), "El objeto pedido enriquecido no debe ser nulo");

        // Convertimos el Object al DTO real para verificar sus campos internos de manera segura
        PedidoDTO datosPedido = (PedidoDTO) resultado.getPedido();
        assertEquals("PAGADO", datosPedido.getEstado());
        assertEquals(pedidoId, datosPedido.getPedidoId());
        assertEquals(45000.0, datosPedido.getTotal());

        // Verificamos que se haya consultado la base de datos local exactamente 1 vez
        verify(despachoRepository, times(1)).findById(despachoId);
    }
}
