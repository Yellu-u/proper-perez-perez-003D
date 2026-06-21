package com.proper.service_facturacion.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proper.service_facturacion.model.Facturacion;
import com.proper.service_facturacion.repository.FacturacionRepository;

@Service
public class FacturacionService {
    
    @Autowired
    private FacturacionRepository facturacionRepository;

    public List<Facturacion> obtenerFacturacion()
    {
        return facturacionRepository.findAll();
    }

    public Facturacion obtenerFacturacionid(Long id)
    {
        return facturacionRepository.findById(id).orElse(null);
    }

    public Facturacion guardarFacturacion(Facturacion facturacion)
    {
        facturacion.setFechaFacturacion(LocalDate.now());

        if(facturacion.getEstado()==null)
        {
            facturacion.setEstado("EMITIDA");
        }
        return facturacionRepository.save(facturacion);
    }

    public Facturacion actualizarFactura(Long id, Facturacion facturacion)
    {
        Facturacion facturacionExistente = facturacionRepository.findById(id).orElse(null);

        if(facturacionExistente != null)
        {
            facturacionExistente.setEstado(facturacion.getEstado());

            return facturacionRepository.save(facturacionExistente);
        }
        return null;
    }
    public void eliminarFactura(Long id){
        facturacionRepository.deleteById(id);
    }


}
