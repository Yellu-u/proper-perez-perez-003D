package com.proper.service_vendedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proper.service_vendedor.model.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long>
{
    Vendedor findByvendedorId(Long vendedorId);
}