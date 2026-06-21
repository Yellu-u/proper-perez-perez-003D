package com.proper.service_facturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proper.service_facturacion.model.Facturacion;
import java.util.List;


public interface FacturacionRepository extends JpaRepository<Facturacion, Long>{

    List<Facturacion> findByClienteId(Long clienteId);

    List<Facturacion> findByPedidoId(Long pedidoId);

}
