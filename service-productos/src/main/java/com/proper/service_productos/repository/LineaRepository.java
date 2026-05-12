package com.proper.service_productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proper.service_productos.model.Linea;

@Repository
public interface LineaRepository extends JpaRepository<Linea, Long>
{
        //Buscar por nombre (ej: linea hogar, linea automotriz, linea casino y restaurant)
        Linea findByNombre(String nombre);

        //Buscar por nombre ignorando mayúsculas/minúsculas
        Linea findByNombreIgnoreCase(String nombre);
}