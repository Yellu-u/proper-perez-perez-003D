package com.proper.service_cliente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proper.service_cliente.model.Cliente;
import com.proper.service_cliente.model.Empresa;
import com.proper.service_cliente.repository.ClienteRepository;
import com.proper.service_cliente.repository.EmpresaRepository;


@Service
public class ClienteService 
{
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    //Se buscan todos los clientes
    public List<Cliente> listarTodosClientes()
    {
        return clienteRepository.findAll();
    }

    //Se busca cliente por id
    public Optional<Cliente> buscarClientePorId(Long id)
    {
        return clienteRepository.findById(id);
    }

    //Se buscan todas las empresas
    public List<Empresa> listarTodasEmpresas()
    {
        return empresaRepository.findAll();
    }

    //Se busca empresa por id
    public Optional<Empresa> buscarEmpresaPorId(Long id)
    {
        return empresaRepository.findById(id);
    }

    //Se crea cliente
    public Cliente crearCliente(Cliente cliente)
    {
        return clienteRepository.save(cliente);
    }

    //Se crea empresa
    public Empresa crearEmpresa(Empresa empresa)
    {
        return empresaRepository.save(empresa);
    }

    //Se elimina cliente
    public void eliminarCliente(Long id)
    {
        clienteRepository.deleteById(id);
    }

    //Se elimina empresa
    public void eliminarEmpresa(Long id)
    {
        empresaRepository.deleteById(id);
    }

    //Se modifica cliente
    public Cliente actualizarCliente(Long id, Cliente cliente)
    {
        Cliente clienteExistente = clienteRepository.findById(id).orElse(null);

        if(clienteExistente != null)
        {
            clienteExistente.setRunCliente(cliente.getRunCliente());
            clienteExistente.setNombreCliente(cliente.getNombreCliente());
            clienteExistente.setApellidoCliente(cliente.getApellidoCliente());
            clienteExistente.setTelefonoCliente(cliente.getTelefonoCliente());
            clienteExistente.setCorreoCliente(cliente.getCorreoCliente());
            clienteExistente.setEmpresa(cliente.getEmpresa());

            return clienteRepository.save(clienteExistente);
        }
        return null;
    }

    //Se modifica empresa
    public Empresa actualizarEmpresa(Long id, Empresa empresa)
    {
        Empresa empresaExistente = empresaRepository.findById(id).orElse(null);

        if(empresaExistente != null)
        {
            empresaExistente.setRazonSocial(empresa.getRazonSocial());
            empresaExistente.setDireccionEmpresa(empresa.getDireccionEmpresa());
            empresaExistente.setTelefonoEmpresa(empresa.getTelefonoEmpresa());

            return empresaRepository.save(empresaExistente);
        }
        return null;
    }
}
