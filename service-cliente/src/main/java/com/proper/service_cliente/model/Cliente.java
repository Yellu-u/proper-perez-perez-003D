package com.proper.service_cliente.model;

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
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa a un Cliente en el sistema")
public class Cliente 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del cliente", example = "1")
    private Long clienteId;

    @Schema(description = "RUN o Identificación oficial del cliente", example = "12345678-9")
    private String runCliente;

    @Schema(description = "Nombres del cliente", example = "Juan Pablo")
    private String nombreCliente;

    @Schema(description = "Apellidos del cliente", example = "Pérez Gómez")
    private String apellidoCliente;

    @Schema(description = "Teléfono de contacto del cliente", example = "+56987654321")
    private String telefonoCliente;

    @Schema(description = "Correo electrónico de contacto", example = "juan.perez@email.com")
    private String correoCliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresaId")
    @Schema(description = "Empresa a la que se encuentra asociado el cliente")
    private Empresa empresa;
}