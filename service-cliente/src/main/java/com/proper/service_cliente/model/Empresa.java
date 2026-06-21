package com.proper.service_cliente.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "empresa")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa a una Empresa o Institución asociada a los clientes")
public class Empresa 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental de la empresa", example = "1")
    private Long empresaId;

    @Schema(description = "Razón social o nombre legal de la empresa", example = "Logística S.A.")
    private String razonSocial;

    @Schema(description = "Dirección física de la empresa", example = "Av. Vitacura 1234, Santiago")
    private String direccionEmpresa;

    @Schema(description = "Teléfono fijo corporativo de la empresa", example = "+5622334455")
    private String telefonoEmpresa;
}