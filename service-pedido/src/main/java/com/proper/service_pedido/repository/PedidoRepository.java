package com.proper.service_pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proper.service_pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>
{
    List<Pedido> findByVendedorId(Long vendedorId);
    List<Pedido> findByClienteId(Long clienteId);
}