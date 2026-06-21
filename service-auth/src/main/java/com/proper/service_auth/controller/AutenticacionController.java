package com.proper.service_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proper.service_auth.dto.AuthRequest;
import com.proper.service_auth.model.Usuario;
import com.proper.service_auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autentication",description = "Endpoints para registro y login de usuarios")
public class AutenticacionController {
    @Autowired
    private AuthService authService;
    @Operation(summary = "Registrar un nuevo suario",description = "Guarda el usuario con la contraseña encriptada")
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody Usuario usuario)
    {
        return ResponseEntity.ok(authService.registrar(usuario));
    }

    @Operation(summary = "Iniciar Sesión",description = "Retorna el Token JWT si las credenciales son válidas")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request)
    {
        try{
            String token = authService.login(request.getNombreUsuario(),request.getContrasena());
            return ResponseEntity.ok(token);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
