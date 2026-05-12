package com.proper.service_productos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long productoId;
        private String productoNombre;
        private float precio;
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "lineaId")
        private Linea linea;
}