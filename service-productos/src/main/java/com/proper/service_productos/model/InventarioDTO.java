package com.proper.service_productos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioDTO {
    private Long id;
    private Long productoId;
    private Integer cantidadFisica;
}
