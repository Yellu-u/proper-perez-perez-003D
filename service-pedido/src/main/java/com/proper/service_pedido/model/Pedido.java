package com.proper.service_pedido.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa la cabecera de un Pedido en el sistema")
public class Pedido 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del pedido", example = "23")
    private Long pedidoId;

    @NotNull(message = "La fecha del pedido es obligatoria")
    @Schema(description = "Fecha en la que se registra el pedido", example = "2026-06-21", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fecha;

    @NotBlank(message = "El estado del pedido es obligatorio")
    @Schema(description = "Estado actual de la orden de pedido", example = "EN PROCESO", requiredMode = Schema.RequiredMode.REQUIRED)
    private String estado; //ej: completo, incompleto, en proceso
    
    @NotNull(message = "El ID del vendedor es obligatorio")
    @Schema(description = "ID del Vendedor asignado (Referencia lógica al microservicio de Vendedores)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long vendedorId;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del Cliente solicitante (Referencia lógica al microservicio de Clientes)", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long clienteId;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "Lista detallada con los artículos incluidos en el pedido")
    private List<DetallePedido> detalles;
}