package com.proper.service_productos.service;

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

import com.proper.service_productos.model.Linea;
import com.proper.service_productos.model.Producto;
import com.proper.service_productos.repository.LineaRepository;
import com.proper.service_productos.repository.ProductoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class ProductosServiceTest {
    @Mock
    private ProductoRepository productoRepository; // Simulamos la BD de Productos

    @Mock
    private LineaRepository lineaRepository; // Simulamos la BD de Líneas

    @Mock
    private WebClient.Builder webClientBuilder; // Simulamos el WebClient

    @InjectMocks
    private ProductoService productoService; // Inyectamos los mocks en tu servicio

    @Test
    @DisplayName("Debería eliminar un producto y mandar la orden de borrar su inventario externo")
    void eliminarProductoTest() {
        // --- 1. PREPARACIÓN ---
        Long productoId = 1L;

        // Mock de la cadena de WebClient adaptada para el método DELETE
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.UriSpec uriSpec = Mockito.mock(WebClient.UriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec); 
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        
        // Devolvemos un Mono vacío (Mono.empty()) porque la respuesta espera un Void.class
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        // --- 2. EJECUCIÓN ---
        productoService.eliminarProducto(productoId);

        // --- 3. VERIFICACIÓN ---
        // Verificamos que se haya ejecutado el borrado en la base de datos local
        verify(productoRepository, times(1)).deleteById(productoId);
    }

    @Test
    @DisplayName("Debería buscar un producto por ID exitosamente con sus datos correctos")
    void buscarProductoPorIdTest() {
        // --- 1. PREPARACIÓN ---
        Long productoId = 1L;
        
        // Creamos los objetos simulados respetando las variables exactas de tus entidades
        Linea lineaMock = new Linea(3L, "Linea Casino y Restaurant");
        Producto productoMock = new Producto(productoId, "Detergente cachupin", 1450.50f, lineaMock);

        // Comportamiento de la BD local
        when(productoRepository.findById(productoId)).thenReturn(Optional.of(productoMock));

        // --- 2. EJECUCIÓN ---
        Optional<Producto> resultadoOptional = productoService.buscarProductoPorId(productoId);

        // --- 3. VERIFICACIÓN ---
        assertTrue(resultadoOptional.isPresent(), "El producto debería existir");
        Producto resultado = resultadoOptional.get();
        
        // Validamos que los campos coincidan exactamente
        assertEquals("Detergente cachupin", resultado.getProductoNombre());
        assertEquals(1450.50f, resultado.getPrecio());
        
        assertNotNull(resultado.getLinea(), "La línea del producto no debe ser nula");
        assertEquals("Linea Casino y Restaurant", resultado.getLinea().getNombre());

        verify(productoRepository, times(1)).findById(productoId);
    }

}
