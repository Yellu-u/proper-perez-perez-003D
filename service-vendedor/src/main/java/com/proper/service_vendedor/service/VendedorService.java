package com.proper.service_vendedor.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proper.service_vendedor.model.Vendedor;
import com.proper.service_vendedor.repository.VendedorRepository;

@Service
public class VendedorService 
{

    @Autowired
    private VendedorRepository vendedorRepository;

    //Se buscan todos los vendedores
    public List<Vendedor> listarTodos()
    {
        return vendedorRepository.findAll();
    }

    //Se busca vendedor por id
    public Optional<Vendedor> buscarPorId(Long vendedorId)
    {
        return vendedorRepository.findById(vendedorId);
    }

    //Se guarda vendedor
    public Vendedor guardar(Vendedor vendedor)
    {
        return vendedorRepository.save(vendedor);

    }

    //Se elimina vendedor    
    public void eliminar(Long vendedorId)
    {
        vendedorRepository.deleteById(vendedorId);
    }
    
    //Se actualiza vendedor
    public Vendedor actualizarVendedor(Long id, Vendedor vendedor)
    {
        Vendedor vendedorExistente = vendedorRepository.findById(id).orElse(null);

        if(vendedorExistente != null)
        {
            vendedorExistente.setNombreVendedor(vendedor.getNombreVendedor());
            vendedorExistente.setApellidoVendedor(vendedor.getApellidoVendedor());
            vendedorExistente.setCorreoVendedor(vendedor.getCorreoVendedor());
            vendedorExistente.setRunVendedor(vendedor.getRunVendedor());
            vendedorExistente.setTelefonoVendedor(vendedor.getTelefonoVendedor());

            return vendedorRepository.save(vendedorExistente);
        }
        return null;
    }
}