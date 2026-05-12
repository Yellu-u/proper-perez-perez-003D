package com.proper.service_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proper.service_cliente.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> 
{
    Cliente findByRunCliente(String runCliente);
}
