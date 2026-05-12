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

    public List<Vendedor> listarTodos()
    {
        return vendedorRepository.findAll();
    }

    public Optional<Vendedor> buscarPorId(Long vendedorId)
    {
        return vendedorRepository.findById(vendedorId);
    }

    public Vendedor guardar(Vendedor vendedor)
    {
        return vendedorRepository.save(vendedor);

    }
    
    public void eliminar(Long vendedorId)
    {
        vendedorRepository.deleteById(vendedorId);
    }
    
}