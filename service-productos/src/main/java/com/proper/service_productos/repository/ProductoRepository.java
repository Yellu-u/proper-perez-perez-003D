package com.proper.service_productos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proper.service_productos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>
{
        //Buscar por ID
        Producto findByProductoId(Long productoId);

        //Reporte productos por línea
        @Query("""
        SELECT 
                p.linea.nombre AS Linea,
                COUNT(p) AS cantidad
        FROM Producto p
        GROUP BY p.linea.nombre
                """)
        List<Object[]> conteoPorLinea();
}