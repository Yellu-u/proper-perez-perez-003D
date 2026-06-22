package com.proper.service_inventario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.proper.service_inventario.model.Inventario;
import com.proper.service_inventario.repository.InventarioRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    @DisplayName("Debería guardar un inventario si el producto existe en el microservicio externo")
    void guardarInventarioTest() {
        // --- 1. PREPARACIÓN ---
        Inventario inventarioInput = new Inventario();
        inventarioInput.setProductoId(12L);
        inventarioInput.setStockActual(100);
        inventarioInput.setStockMinimo(20);

        Inventario inventarioGuardado = new Inventario(1L, 100, 20, LocalDate.now(), 12L, null);

        // Simulamos la cadena de WebClient para la llamada GET a Productos
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        // Retornamos un objeto genérico simulando que el producto sí existe
        Object productoMock = new Object();

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(productoMock));

        // Simulamos la persistencia en el repositorio local
        when(inventarioRepository.save(Mockito.any(Inventario.class))).thenReturn(inventarioGuardado);

        // --- 2. EJECUCIÓN ---
        Inventario resultado = inventarioService.guardarInventario(inventarioInput);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdInventario());
        assertEquals(12L, resultado.getProductoId());

        verify(inventarioRepository, times(1)).save(Mockito.any(Inventario.class));
    }

    @Test
    @DisplayName("Debería descontar stock correctamente y registrar el ID del pedido")
    void descontarStockTest() {
        // --- 1. PREPARACIÓN ---
        Long productoId = 12L;
        Integer cantidadADescontar = 10;
        Long pedidoId = 101L;

        // Simulamos el estado actual del inventario en la base de datos (Stock Inicial: 50)
        Inventario inventarioExistente = new Inventario(1L, 50, 15, LocalDate.now().minusDays(1), productoId, null);
        
        List<Inventario> listaRegistros = new ArrayList<>();
        listaRegistros.add(inventarioExistente);

        // Comportamiento del Repositorio al buscar por ProductoId
        when(inventarioRepository.findByProductoId(productoId)).thenReturn(listaRegistros);
        
        // Comportamiento al guardar la actualización
        when(inventarioRepository.save(Mockito.any(Inventario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // --- 2. EJECUCIÓN ---
        Inventario resultado = inventarioService.descontarStock(productoId, cantidadADescontar, pedidoId);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        assertEquals(40, resultado.getStockActual()); // 50 - 10 = 40
        assertEquals(pedidoId, resultado.getPedidoId());
        assertEquals(LocalDate.now(), resultado.getFechaActualizacion());

        verify(inventarioRepository, times(1)).findByProductoId(productoId);
        verify(inventarioRepository, times(1)).save(Mockito.any(Inventario.class));
    }

    

}
