package com.proper.service_cliente.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empresa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empresaId;
    private String razonSocial;
    private String direccionEmpresa;
    private String telefonoEmpresa;
}