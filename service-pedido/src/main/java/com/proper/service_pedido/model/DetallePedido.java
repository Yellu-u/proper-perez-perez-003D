package com.proper.service_pedido.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un artículo individual dentro del detalle de un pedido")
public class DetallePedido 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del detalle de pedido", example = "501")
    private long detalleId;

    @Schema(description = "Cantidad de unidades del producto solicitadas", example = "5")
    private int cantidad;

    @Schema(description = "Precio unitario histórico del artículo", example = "1500.00")
    private Double precioUnitario;

    @Schema(description = "Subtotal acumulado para este ítem (Cantidad x Precio)", example = "7500.00")
    private Double subtotal;

    @Schema(description = "ID del Producto seleccionado (Referencia lógica al microservicio de Productos)", example = "12")
    private Long productoId; 

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    @Schema(description = "Pedido principal al cual pertenece este renglón de detalle")
    private Pedido pedido;
}