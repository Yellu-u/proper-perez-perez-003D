package com.proper.service_productos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "linea")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Linea 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long lineaId;
        private String nombre;
}