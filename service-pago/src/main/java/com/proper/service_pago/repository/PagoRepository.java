package com.proper.service_pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proper.service_pago.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>
{
        Pago findByPedidoId(Long pedidoId);
}