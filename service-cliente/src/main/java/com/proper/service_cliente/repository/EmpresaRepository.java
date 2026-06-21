package com.proper.service_cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proper.service_cliente.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,Long> 
{
        Empresa findByEmpresaId(Long empresaId);
}