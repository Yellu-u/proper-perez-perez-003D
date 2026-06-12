package com.proper.service_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


import com.proper.service_inventario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario,Long>
{
    
    List<Inventario> findByProductoId(Long productoId);

    void deleteByProductoId(Long productoId);


}
