package com.proper.service_productos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "linea")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa la Línea o Categoría a la que se asocian los productos")
public class Linea 
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "ID único autoincremental de la línea de productos", example = "3")
        private Long lineaId;

        @NotBlank(message = "El nombre de la línea comercial es obligatorio")
        @Schema(description = "Nombre de la categoría o línea comercial", example = "Linea Casino y Restaurant", requiredMode = Schema.RequiredMode.REQUIRED)
        private String nombre;
}