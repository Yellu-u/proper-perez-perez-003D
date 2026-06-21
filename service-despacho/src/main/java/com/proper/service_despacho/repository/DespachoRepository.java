package com.proper.service_despacho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proper.service_despacho.model.Despacho;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Long>
{
        Despacho findByPedidoId(Long pedidoId);
}
