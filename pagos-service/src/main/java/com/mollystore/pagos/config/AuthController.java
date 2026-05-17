package com.mollystore.pagos.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Endpoint de autenticacion JWT.
 * POST /api/auth/login  { "username": "admin", "password": "admin123" }
 * Retorna: { "token": "eyJ..." }
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        log.info("Intento de login para usuario: {}", username);
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        String token = jwtUtil.generateToken(auth.getName());
        log.info("Login exitoso para usuario: {}", username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
