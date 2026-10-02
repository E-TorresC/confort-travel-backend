package com.conforttravel.api.tramo.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.checkin.dto.CheckInRequest;
import com.conforttravel.api.checkin.service.CheckInService;
import com.conforttravel.api.shared.exception.BusinessException;
import com.conforttravel.api.tramo.dto.TramoRequest;
import com.conforttravel.api.tramo.dto.TramoResponse;
import com.conforttravel.api.tramo.entity.TipoTramo;
import com.conforttravel.api.tramo.entity.TipoViaje;
import com.conforttravel.api.tramo.entity.Tramo;
import com.conforttravel.api.tramo.mapper.TramoMapper;
import com.conforttravel.api.tramo.repository.TramoRepository;
import com.conforttravel.api.venta.entity.Venta;
import com.conforttravel.api.venta.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TramoServiceImplTest {

    @Mock
    private TramoRepository tramoRepository;
    @Mock
    private VentaRepository ventaRepository;
    @Mock
    private TramoMapper tramoMapper;
    @Mock
    private AuditLogService auditLogService;
    @Mock
    private CheckInService checkInService;

    @InjectMocks
    private TramoServiceImpl tramoService;

    @Test
    void crearTramo_Exito_FechasValidas() {
        OffsetDateTime salida = OffsetDateTime.now().plusDays(1);
        OffsetDateTime llegada = salida.plusHours(5);

        TramoRequest request = new TramoRequest(TipoTramo.IDA, TipoViaje.INTERNACIONAL, "LIM", "MIA", salida, llegada, 1L);

        Venta venta = new Venta();
        venta.setId(1L);

        Tramo tramoEntity = new Tramo();
        tramoEntity.setOrigen("LIM");
        tramoEntity.setDestino("MIA");

        Tramo tramoGuardado = new Tramo();
        tramoGuardado.setId(10L);
        tramoGuardado.setOrigen("LIM");
        tramoGuardado.setDestino("MIA");

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(tramoMapper.toEntity(request)).thenReturn(tramoEntity);
        when(tramoRepository.save(tramoEntity)).thenReturn(tramoGuardado);
        when(tramoMapper.toResponseDto(tramoGuardado)).thenReturn(
                new TramoResponse(10L, TipoTramo.IDA, TipoViaje.INTERNACIONAL, "LIM", "MIA", salida, llegada, 1L, null, null)
        );

        TramoResponse response = tramoService.crear(request);

        assertNotNull(response);
        assertEquals("LIM", response.origen());
        verify(tramoRepository, times(1)).save(any(Tramo.class));
        verify(auditLogService, times(1)).registrar(eq("CREAR"), eq("TRAMO"), eq(10L), anyString());
        verify(checkInService, times(1)).crear(any(CheckInRequest.class));
    }

    @Test
    void crearTramo_LanzaBusinessException_CuandoLlegadaEsAnteriorASalida() {
        OffsetDateTime salida = OffsetDateTime.now().plusDays(1);
        OffsetDateTime llegadaInvalida = salida.minusHours(2);

        TramoRequest request = new TramoRequest(TipoTramo.IDA, TipoViaje.INTERNACIONAL, "LIM", "MIA", salida, llegadaInvalida, 1L);

        BusinessException exception = assertThrows(BusinessException.class, () -> tramoService.crear(request));
        assertEquals("La fecha/hora de llegada debe ser posterior a la fecha/hora de salida", exception.getMessage());

        verify(ventaRepository, never()).findById(anyLong());
        verify(tramoRepository, never()).save(any());
        verify(auditLogService, never()).registrar(anyString(), anyString(), anyLong(), anyString());
        verify(checkInService, never()).crear(any());
    }
}