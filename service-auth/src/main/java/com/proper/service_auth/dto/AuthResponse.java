package com.proper.service_auth.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
        private String token;
        private String nombreUsuario;
        private String nombreCompleto;
        private List<String> roles;
}