package com.proper.service_auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@Getter 
@Setter 
public class ActualizarPerfilRequest {
    
    private String nombreCompleto;
    private String correo;

}
