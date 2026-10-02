/*
package com.conforttravel.api.venta.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.cliente.entity.Cliente;
import com.conforttravel.api.cliente.repository.ClienteRepository;
import com.conforttravel.api.shared.exception.BusinessException;
import com.conforttravel.api.shared.util.GeneradorCodigoUtil;   // ⬅️ IMPORT NUEVO
import com.conforttravel.api.usuario.entity.Rol;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import com.conforttravel.api.venta.dto.VentaRequest;
import com.conforttravel.api.venta.dto.VentaResponse;
import com.conforttravel.api.venta.entity.Venta;
import com.conforttravel.api.venta.mapper.VentaMapper;
import com.conforttravel.api.venta.repository.VentaRepository;
import com.conforttravel.api.venta.specification.VentaSpecificationBuilder;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
    private VentaSpecificationBuilder ventaSpecificationBuilder;
    @Mock
    private AuditLogService auditLogService;

    // ⬇️ ESTE ERA EL QUE FALTABA
    @Mock
    private GeneradorCodigoUtil generadorCodigoUtil;

    @InjectMocks
    private VentaServiceImpl ventaService;

    private Usuario agenteValido(Long id) {
        Usuario agente = new Usuario();
        agente.setId(id);
        agente.setRol(Rol.AGENTE);
        agente.setActivo(true);
        return agente;
    }

    @Test
    void crearVenta_Exito() {
        // Arrange
        VentaRequest request = new VentaRequest(
                OffsetDateTime.now(),
                new BigDecimal("500.00"),
                new BigDecimal("650.00"),
                "Sin observaciones",
                1, 0, 1L, 1L, null
        );

        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Usuario agente = agenteValido(1L);

        Venta ventaMapeada = new Venta();
        ventaMapeada.setPrecioInicial(request.precioInicial());
        ventaMapeada.setPrecioFinal(request.precioFinal());

        Venta ventaGuardada = new Venta();
        ventaGuardada.setId(10L);
        ventaGuardada.setCodigo("VTA002");   // ⬅️ corregido: era "TRM002"
        ventaGuardada.setCliente(cliente);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(agente));
        when(ventaMapper.toEntity(request)).thenReturn(ventaMapeada);

        // ⬇️ ESTE STUB ES EL QUE FALTABA
        // Ajusta "VENTA" y "VTA" a los valores reales de VentaServiceImpl línea 59
        when(generadorCodigoUtil.generarSiguiente("VENTA", "VTA"))
                .thenReturn("VTA002");

        when(ventaRepository.save(ventaMapeada)).thenReturn(ventaGuardada);

        when(ventaMapper.toResponseDto(ventaGuardada)).thenReturn(
                new VentaResponse(
                        10L, "VTA002",                    // ⬅️ corregido: era "TRM002"
                        request.fechaCompra(),
                        request.precioInicial(),
                        request.precioFinal(),
                        new BigDecimal("150.00"),
                        "Sin observaciones",
                        1, 0, 1L, 1L,
                        null, null, 0L
                )
        );

        // Act
        VentaResponse response = ventaService.crear(request);

        // Assert
        assertNotNull(response);
        assertEquals(new BigDecimal("150.00"), response.ganancia());
        assertEquals("VTA002", response.codigo());
        verify(ventaRepository, times(1)).save(any(Venta.class));
        verify(auditLogService, times(1))
                .registrar(eq("CREAR"), eq("VENTA"), eq(10L), anyString());
    }

    @Test
    void crearVenta_LanzaBusinessException_CuandoPrecioFinalEsMenorAInicial() {
        VentaRequest request = new VentaRequest(
                OffsetDateTime.now(),
                new BigDecimal("650.00"),
                new BigDecimal("500.00"),
                "Sin observaciones",
                1, 0, 1L, 1L, null
        );

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> ventaService.crear(request)
        );
        assertEquals(
                "El precio final no puede ser menor que el precio inicial",
                exception.getMessage()
        );

        verify(ventaRepository, never()).save(any());
        verify(auditLogService, never())
                .registrar(anyString(), anyString(), anyLong(), anyString());

        // ⬇️ Verifica que no se llamó al generador cuando falla la validación
        verify(generadorCodigoUtil, never())
                .generarSiguiente(anyString(), anyString());
    }

    @Test
    void crearVenta_LanzaBusinessException_CuandoUsuarioNoEsAgente() {
        VentaRequest request = new VentaRequest(
                OffsetDateTime.now(),
                new BigDecimal("500.00"),
                new BigDecimal("650.00"),
                "Sin observaciones",
                1, 0, 1L, 1L, null
        );

        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Usuario usuarioNoAgente = new Usuario();
        usuarioNoAgente.setId(1L);
        usuarioNoAgente.setRol(Rol.ADMIN);
        usuarioNoAgente.setActivo(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioNoAgente));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> ventaService.crear(request)
        );
        assertEquals(
                "El usuario asignado a la venta debe tener el rol de AGENTE",
                exception.getMessage()
        );

        verify(ventaRepository, never()).save(any());
        verify(generadorCodigoUtil, never())
                .generarSiguiente(anyString(), anyString());
    }

    @Test
    void crearVenta_LanzaBusinessException_CuandoAgenteNoEstaActivo() {
        VentaRequest request = new VentaRequest(
                OffsetDateTime.now(),
                new BigDecimal("500.00"),
                new BigDecimal("650.00"),
                "Sin observaciones",
                1, 0, 1L, 1L, null
        );

        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Usuario agenteInactivo = new Usuario();
        agenteInactivo.setId(1L);
        agenteInactivo.setRol(Rol.AGENTE);
        agenteInactivo.setActivo(false);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(agenteInactivo));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> ventaService.crear(request)
        );
        assertEquals(
                "El agente asignado a la venta debe encontrarse activo",
                exception.getMessage()
        );

        verify(ventaRepository, never()).save(any());
        verify(generadorCodigoUtil, never())
                .generarSiguiente(anyString(), anyString());
    }
}*/
