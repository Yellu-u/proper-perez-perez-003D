package com.proper.service_cliente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.proper.service_cliente.model.Cliente;
import com.proper.service_cliente.repository.ClienteRepository;
import com.proper.service_cliente.repository.EmpresaRepository;

@ExtendWith(MockitoExtension.class)//Usamos Mockito para simular objetos
public class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private EmpresaRepository empresaRepository;
    @InjectMocks
    private ClienteService clienteService;
    @Test
    @DisplayName("Deberia guardar un cliente correctamente")
    void guardarClienteTest()
    {
        Cliente cliente = new Cliente();
        cliente.setNombreCliente("Manuel");
        cliente.setRunCliente("12333232-4");
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation->{
            Cliente c=invocation.getArgument(0);
            c.setClienteId(1L);
            return c;
        });

    Cliente resultado = clienteService.crearCliente(cliente);
    assertNotNull(resultado);
    assertEquals(1L, resultado.getClienteId());
    assertEquals("Manuel", resultado.getNombreCliente());
    verify(clienteRepository,times(1)).save(cliente);
    }

}
