package com.proper.service_vendedor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.proper.service_vendedor.model.Vendedor;
import com.proper.service_vendedor.repository.VendedorRepository;

@ExtendWith(MockitoExtension.class)//Usamos Mockito para simular objetos
class VendedorServiceTest {
    @Mock
    private VendedorRepository vendedorRepository;

    @InjectMocks
    private VendedorService vendedorService;
    @Test
    @DisplayName("Deberiamos guardar un vendedor correctamente")
    void guardarVendedorTest()
    {
        Vendedor vendedor = new Vendedor();
        vendedor.setNombreVendedor("Miguel");
        vendedor.setRunVendedor("18765555-2");

        when(vendedorRepository.save(any(Vendedor.class))).thenAnswer(invocation->{
            Vendedor v=invocation.getArgument(0);
            v.setVendedorId(1L);
            return v;
        });
    Vendedor resultado = vendedorService.guardar(vendedor);
    assertEquals(1L, resultado.getVendedorId());
    assertEquals("Miguel", resultado.getNombreVendedor());
    verify(vendedorRepository,times(1)).save(vendedor);
    }


}
