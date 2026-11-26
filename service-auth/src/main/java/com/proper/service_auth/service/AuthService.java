package com.proper.service_auth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proper.service_auth.dto.ActualizarPerfilRequest;
import com.proper.service_auth.dto.AuthResponse;
import com.proper.service_auth.dto.RegistroRequest;
import com.proper.service_auth.model.Rol;
import com.proper.service_auth.model.Usuario;
import com.proper.service_auth.repository.RolRepository;
import com.proper.service_auth.repository.UsuarioRepository;

@Service
public class AuthService {
        private final UsuarioRepository usuarioRepository;
        private final RolRepository rolRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;

        public AuthService(UsuarioRepository usuarioRepository,
                        RolRepository rolRepository,
                        JwtService jwtService,
                        PasswordEncoder passwordEncoder) {
                this.usuarioRepository = usuarioRepository;
                this.rolRepository = rolRepository;
                this.jwtService = jwtService;
                this.passwordEncoder = passwordEncoder;
        }

        public String registrar(RegistroRequest request) {
                if (usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())) {
                        throw new IllegalArgumentException("El nombre de usuario ya existe");
                }
                if (usuarioRepository.existsByCorreo(request.getCorreo())) {
                        throw new IllegalArgumentException("El correo ya está registrado");
                }

                Rol rolCliente = rolRepository.findByNombreRol("CLIENTE")
                                .orElseThrow(() -> new IllegalStateException("Falta el rol CLIENTE en la BD"));

                Usuario usuario = new Usuario();
                usuario.setNombreUsuario(request.getNombreUsuario());
                usuario.setCorreo(request.getCorreo());
                usuario.setNombreCompleto(request.getNombreCompleto());
                usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
                usuario.getRoles().add(rolCliente);

                usuarioRepository.save(usuario);
                return "Usuario Registrado";
        }

        public AuthResponse login(String username, String password) {
                Usuario user = usuarioRepository.findByNombreUsuario(username)
                                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

                if (!passwordEncoder.matches(password, user.getContrasena())) {
                        throw new RuntimeException("Credenciales inválidas");
                }

                List<String> roles = user.getRoles().stream()
                                .map(Rol::getNombreRol)
                                .collect(Collectors.toList());

                String token = jwtService.generarToken(username, roles);
                return new AuthResponse(token, username, user.getNombreCompleto(), roles);
        }

        public AuthResponse actualizarPerfil(String token, ActualizarPerfilRequest request){
                String nombreUsuarioActual = jwtService.extraerNombreUsuario(token);
                Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuarioActual).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                String nuevoCorreo = request.getCorreo();
                if (nuevoCorreo == null || nuevoCorreo.isBlank() || request.getNombreCompleto() == null || request.getNombreCompleto().isBlank()) {
                        throw new IllegalArgumentException("Nombre y correo son obligatorios");
                }

                if (!nuevoCorreo.equalsIgnoreCase(usuario.getCorreo()) && usuarioRepository.existsByCorreo(nuevoCorreo)){
                        throw new IllegalArgumentException("El correo ya está en uso");
                }

                usuario.setNombreCompleto(request.getNombreCompleto());
                usuario.setCorreo(nuevoCorreo);
                usuario.setNombreUsuario(nuevoCorreo); // el login usa este campo
                usuarioRepository.save(usuario);

                List<String> roles = usuario.getRoles().stream()
                                .map(Rol::getNombreRol)
                                .collect(Collectors.toList());
                String nuevoToken = jwtService.generarToken(nuevoCorreo, roles);
                return new AuthResponse(nuevoToken, nuevoCorreo, usuario.getNombreCompleto(), roles);
        }

        public void cambiarContrasena(String token, String nuevaContrasena) {
                String nombreUsuario = jwtService.extraerNombreUsuario(token);
                Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
                usuarioRepository.save(usuario);
        }
}