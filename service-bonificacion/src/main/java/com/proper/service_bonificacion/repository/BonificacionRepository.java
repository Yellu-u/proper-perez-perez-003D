package com.proper.service_bonificacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proper.service_bonificacion.model.Bonificacion;
import java.util.List;






@Repository

public interface BonificacionRepository extends JpaRepository<Bonificacion,Long>{

    List<Bonificacion> findByBonificacionId(Long bonificacionId);

    List<Bonificacion> findByPedidoId(Long pedidoId);

    List<Bonificacion> findByVendedorId(Long vendedorId);


}
