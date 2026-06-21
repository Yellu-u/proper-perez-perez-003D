package com.proper.service_vendedor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa a un Vendedor dentro del sistema")
public class Vendedor 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del vendedor", example = "1")
    private Long vendedorId;

    @NotBlank(message = "El RUN del vendedor es obligatorio")
    @Schema(description = "RUN o RUT identificador oficial del vendedor", example = "11222333-K", requiredMode = Schema.RequiredMode.REQUIRED)
    private String runVendedor;

    @NotBlank(message = "El nombre del vendedor es obligatorio")
    @Schema(description = "Nombres del vendedor", example = "Carlos Andrés", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreVendedor;

    @NotBlank(message = "El apellido del vendedor es obligatorio")
    @Schema(description = "Apellidos del vendedor", example = "Soto Mayor", requiredMode = Schema.RequiredMode.REQUIRED)
    private String apellidoVendedor;

    @Schema(description = "Correo electrónico corporativo de contacto", example = "carlos.soto@empresa.com")
    private String correoVendedor;

    @Schema(description = "Número telefónico del vendedor", example = "+56911223344")
    private String telefonoVendedor;
}