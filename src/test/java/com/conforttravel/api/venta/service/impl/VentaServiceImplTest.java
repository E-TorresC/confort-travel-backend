package com.conforttravel.api.venta.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.cliente.entity.Cliente;
import com.conforttravel.api.cliente.repository.ClienteRepository;
import com.conforttravel.api.shared.exception.BusinessException;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import com.conforttravel.api.venta.dto.VentaRequest;
import com.conforttravel.api.venta.dto.VentaResponse;
import com.conforttravel.api.venta.entity.Venta;
import com.conforttravel.api.venta.mapper.VentaMapper;
import com.conforttravel.api.venta.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceImplTest {

    @Mock
    private VentaRepository ventaRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private VentaMapper ventaMapper;
    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private VentaServiceImpl ventaService;

    @Test
    void crearVenta_Exito() {
        // Arrange
        VentaRequest request = new VentaRequest(
                OffsetDateTime.now(),
                new BigDecimal("500.00"),
                new BigDecimal("650.00"), // Precio final válido
                "Sin observaciones",
                1, 0, 1L, 1L
        );

        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Usuario agente = new Usuario();
        agente.setId(1L);

        Venta ventaMapeada = new Venta();
        ventaMapeada.setPrecioInicial(request.precioInicial());
        ventaMapeada.setPrecioFinal(request.precioFinal());

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(ventaMapper.toEntity(request)).thenReturn(ventaMapeada);

        Venta ventaGuardada = new Venta();
        ventaGuardada.setId(10L);
        ventaGuardada.setCliente(cliente);
        when(ventaRepository.save(ventaMapeada)).thenReturn(ventaGuardada);

        when(ventaMapper.toResponseDto(ventaGuardada)).thenReturn(
                new VentaResponse(10L, request.fechaCompra(), request.precioInicial(), request.precioFinal(),
                        new BigDecimal("150.00"), "Sin observaciones", 1, 0, 1L, 1L, null, null, null, 0L)
        );

        // Act
        VentaResponse response = ventaService.crear(request);

        // Assert
        assertNotNull(response);
        assertEquals(new BigDecimal("150.00"), response.ganancia());
        verify(ventaRepository, times(1)).save(any(Venta.class));
        verify(auditLogService, times(1)).registrar(eq("CREAR"), eq("VENTA"), eq(10L), anyString());
    }

    @Test
    void crearVenta_LanzaBusinessException_CuandoPrecioFinalEsMenorAInicial() {
        // Arrange
        VentaRequest request = new VentaRequest(
                OffsetDateTime.now(),
                new BigDecimal("650.00"), // Inicial mayor
                new BigDecimal("500.00"), // Final menor (Inválido)
                "Sin observaciones",
                1, 0, 1L, 1L
        );

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> ventaService.crear(request));
        assertEquals("El precio final no puede ser menor que el precio inicial", exception.getMessage());

        verify(ventaRepository, never()).save(any());
        verify(auditLogService, never()).registrar(anyString(), anyString(), anyLong(), anyString());
    }
}