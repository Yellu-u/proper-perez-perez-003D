package com.proper.service_productos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.proper.service_productos.model.Producto;
import com.proper.service_productos.repository.LineaRepository;
import com.proper.service_productos.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)//Usamos Mockito para simular objetos
class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;//Simulamos el repository
    @Mock
    private LineaRepository lineaRepository;
    @InjectMocks
    private ProductoService productoService;//Injectamos el Mock al servicio real
    @Test
    @DisplayName("Deberia guardar un producto correctamente")
    void guardarProductoTest()
    {
        Producto producto = new Producto();
        producto.setProductoNombre("Lisoform Vainilla");
        producto.setLinea(null);
    }

}
