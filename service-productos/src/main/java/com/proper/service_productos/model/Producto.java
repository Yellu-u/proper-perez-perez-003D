package com.proper.service_productos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un Producto dentro del sistema")
public class Producto 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "ID único autoincremental del producto", example = "1")
        private Long productoId;

        @NotBlank(message = "El nombre del producto es obligatorio")
        @Schema(description = "Nombre descriptivo del producto", example = "Detergente cachupin", requiredMode = Schema.RequiredMode.REQUIRED)
        private String productoNombre;

        @NotNull(message = "El precio no puede estar vacío")
        @Schema(description = "Precio unitario del producto en valor flotante", example = "1450.50", requiredMode = Schema.RequiredMode.REQUIRED)
        private Float precio; // Cambiado a Float objeto para permitir validación @NotNull
        
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "lineaId")
        @Schema(description = "Línea o categoría comercial a la que pertenece el producto")
        private Linea linea;
}