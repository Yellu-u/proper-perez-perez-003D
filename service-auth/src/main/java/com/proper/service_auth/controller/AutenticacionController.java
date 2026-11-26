package com.proper.service_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proper.service_auth.dto.ActualizarPerfilRequest;
import com.proper.service_auth.dto.AuthRequest;
import com.proper.service_auth.dto.CambiarContrasenaRequest;
import com.proper.service_auth.dto.RegistroRequest;
import com.proper.service_auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autentication", description = "Endpoints para registro y login de usuarios")
public class AutenticacionController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Registrar un nuevo usuario", description = "Guarda el usuario con la contraseña encriptada y rol CLIENTE")
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody RegistroRequest request) {
        try {
            return ResponseEntity.ok(authService.registrar(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @Operation(summary = "Iniciar Sesión", description = "Retorna token JWT, nombre de usuario y roles si las credenciales son válidas")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request.getNombreUsuario(), request.getContrasena()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar perfil", description = "Cambia nombre y correo del usuario autenticado")
    @PutMapping("/perfil")
    public ResponseEntity<?> actualizarPerfil(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ActualizarPerfilRequest request) {
        try {
            String token = authHeader.replace("Bearer ", "");
            return ResponseEntity.ok(authService.actualizarPerfil(token, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @Operation(summary = "Cambiar contraseña", description = "Guarda la nueva contraseña encriptada")
    @PutMapping("/perfil/contrasena")
    public ResponseEntity<?> cambiarContrasena(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CambiarContrasenaRequest request) {
        try {
            String token = authHeader.replace("Bearer ", "");
            authService.cambiarContrasena(token, request.getContrasena());
            return ResponseEntity.ok("Contraseña actualizada");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
