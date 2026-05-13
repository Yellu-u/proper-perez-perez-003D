package com.proper.service_pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proper.service_pedido.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long>
{
    List<DetallePedido> findByProductoId(Long productoId);
}