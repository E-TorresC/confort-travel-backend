package com.conforttravel.api.cliente.controller;

import com.conforttravel.api.cliente.dto.ClienteRequest;
import com.conforttravel.api.cliente.repository.ClienteRepository;
import com.conforttravel.api.usuario.entity.Rol;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
        usuarioRepository.deleteAll();

        Usuario agente = Usuario.builder()
                .nombre("Agente")
                .apellido("Test")
                .email("agente@conforttravel.com")
                .passwordHash(passwordEncoder.encode("Password123*"))
                .rol(Rol.AGENTE)  // ⚠️ Ajusta al nombre real de tu enum
                .activo(true)
                .build();

        usuarioRepository.save(agente);
    }

    @Test
    @WithMockUser(username = "agente@conforttravel.com", roles = {"AGENTE"})
    void crearCliente_FlujoCompleto_DebeGuardarEnBaseDeDatos() throws Exception {

        ClienteRequest request = new ClienteRequest(
                "Carlos",
                "Mendoza",
                "+51 987654321"
        );

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombres").value("Carlos"))
                .andExpect(jsonPath("$.apellidos").value("Mendoza"))
                .andExpect(jsonPath("$.id").exists());

        assertEquals(1, clienteRepository.count(),
                "Debe haber exactamente 1 cliente guardado en la base de datos");
    }
}