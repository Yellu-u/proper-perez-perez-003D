package com.proper.service_pago.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_pago.model.Pago;
import com.proper.service_pago.repository.PagoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository; // Puedes nombrarlo como gustes o pagoRepository

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void buscarIdCOnPedidoCompletoTest(){
        Long pagoId = 1L;
        Long pedidoId = 500L;

        // Construimos el objeto Pago simulado localmente
        Pago pagoMock = new Pago();
        pagoMock.setPagoId(pagoId);
        pagoMock.setPedidoId(pedidoId);
        pagoMock.setMonto(25000.0);
        pagoMock.setEstadoPago("PENDIENTE");

        Map<String, Object> pedidoMockExternal = Map.of(
            "pedidoId", pedidoId,
            "descripcion", "Compra de Detergentes"
        );

        when(pagoRepository.findById(pagoId)).thenReturn(Optional.of(pagoMock));

        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        
        // Retornamos el objeto simulado mapeado a Object.class tal cual tu código real
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(pedidoMockExternal));

        // --- 2. EJECUCIÓN ---
        Optional<Pago> resultadoOptional = pagoService.buscarPorId(pagoId);

        // --- 3. VERIFICACIÓN ---
        assertTrue(resultadoOptional.isPresent(), "El Pago debe existir");
        Pago resultado = resultadoOptional.get();
        
        assertNotNull(resultado.getPedido(), "El Pedido enriquecido no debe ser nulo");

        // Casteamos el objeto dinámico para comprobar que la simulación funcionó
        java.util.Map<?, ?> datosPedido = (java.util.Map<?, ?>) resultado.getPedido();
        assertEquals("Compra de Detergentes", datosPedido.get("descripcion"));
        assertEquals(pedidoId, datosPedido.get("pedidoId"));

        // Verificamos el acceso al repositorio local
        verify(pagoRepository, times(1)).findById(pagoId);
    }


}
