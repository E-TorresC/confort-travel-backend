package com.conforttravel.api.auth.service.impl;

import com.conforttravel.api.auth.dto.LoginRequest;
import com.conforttravel.api.auth.dto.LoginResponse;
import com.conforttravel.api.auth.security.JwtService;
import com.conforttravel.api.usuario.entity.Rol;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_CredencialesCorrectas_RetornaLoginResponse() {
        // Arrange
        LoginRequest request = new LoginRequest("agente@conforttravel.com", "password123");

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email("agente@conforttravel.com")
                .passwordHash("hashed_password")
                .rol(Rol.AGENTE)
                .build();

        String expectedToken = "jwt_token_mock";

        // Simulamos que el AuthenticationManager no lanza excepción (autenticación exitosa)
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(usuarioRepository.findByEmail("agente@conforttravel.com")).thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(any())).thenReturn(expectedToken);

        // Act
        LoginResponse response = authService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedToken, response.token());
        assertEquals("Bearer", response.type());
        assertEquals(1L, response.userId());
        assertEquals("agente@conforttravel.com", response.email());
        assertEquals("AGENTE", response.rol());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_CredencialesIncorrectas_LanzaExcepcion() {
        // Arrange
        LoginRequest request = new LoginRequest("admin@conforttravel.com", "clave_mala");

        // Simulamos que el AuthenticationManager rechaza las credenciales
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authService.login(request));

        // Verificamos que no se intentó buscar el usuario ni generar el token
        verify(usuarioRepository, never()).findByEmail(anyString());
        verify(jwtService, never()).generateToken(any());
    }
}