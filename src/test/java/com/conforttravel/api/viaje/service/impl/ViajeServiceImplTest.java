package com.conforttravel.api.viaje.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.shared.exception.ConflictException;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.venta.entity.Venta;
import com.conforttravel.api.venta.repository.VentaRepository;
import com.conforttravel.api.viaje.dto.ViajeRequest;
import com.conforttravel.api.viaje.dto.ViajeResponse;
import com.conforttravel.api.viaje.entity.Viaje;
import com.conforttravel.api.viaje.mapper.ViajeMapper;
import com.conforttravel.api.viaje.repository.ViajeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViajeServiceImplTest {

    @Mock
    private ViajeRepository viajeRepository;
    @Mock
    private VentaRepository ventaRepository;
    @Mock
    private ViajeMapper viajeMapper;
    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private ViajeServiceImpl viajeService;

    @Test
    void crearViaje_Exito() {
        // Arrange
        ViajeRequest request = new ViajeRequest(1L);

        Venta venta = new Venta();
        venta.setId(1L);

        Viaje viajeMapeado = new Viaje();
        Viaje viajeGuardado = new Viaje();
        viajeGuardado.setId(10L);
        viajeGuardado.setVenta(venta);

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(viajeRepository.existsByVentaId(1L)).thenReturn(false); // No existe viaje para esta venta
        when(viajeMapper.toEntity(request)).thenReturn(viajeMapeado);
        when(viajeRepository.save(viajeMapeado)).thenReturn(viajeGuardado);
        when(viajeMapper.toResponseDto(viajeGuardado)).thenReturn(new ViajeResponse(10L, 1L));

        // Act
        ViajeResponse response = viajeService.crear(request);

        // Assert
        assertNotNull(response);
        assertEquals(10L, response.id());
        assertEquals(1L, response.ventaId());
        verify(viajeRepository, times(1)).save(any(Viaje.class));
        verify(auditLogService, times(1)).registrar(eq("CREAR"), eq("VIAJE"), eq(10L), anyString());
    }

    @Test
    void crearViaje_LanzaConflictException_CuandoVentaYaTieneViaje() {
        // Arrange
        ViajeRequest request = new ViajeRequest(1L);
        Venta venta = new Venta();
        venta.setId(1L);

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(viajeRepository.existsByVentaId(1L)).thenReturn(true); // Ya existe un viaje

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class, () -> viajeService.crear(request));
        assertEquals("La venta con id 1 ya tiene un viaje asociado", exception.getMessage());

        verify(viajeRepository, never()).save(any());
        verify(auditLogService, never()).registrar(anyString(), anyString(), anyLong(), anyString());
    }

    @Test
    void crearViaje_LanzaResourceNotFound_CuandoVentaNoExiste() {
        // Arrange
        ViajeRequest request = new ViajeRequest(99L);
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> viajeService.crear(request));
        assertTrue(exception.getMessage().contains("Venta no encontrada"));
    }
}