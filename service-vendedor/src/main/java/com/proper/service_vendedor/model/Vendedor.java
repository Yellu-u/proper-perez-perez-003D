package com.proper.service_vendedor.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendedor 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendedorId;
    private String runVendedor;
    private String nombreVendedor;
    private String apellidoVendedor;
    private String correoVendedor;
    private String telefonoVendedor;
}
