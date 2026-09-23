package com.conforttravel.api.venta.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.cliente.entity.Cliente;
import com.conforttravel.api.cliente.repository.ClienteRepository;
import com.conforttravel.api.shared.dto.PageResponse;
import com.conforttravel.api.shared.exception.BusinessException;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import com.conforttravel.api.venta.dto.VentaFilterRequest;
import com.conforttravel.api.venta.dto.VentaRequest;
import com.conforttravel.api.venta.dto.VentaResponse;
import com.conforttravel.api.venta.entity.Venta;
import com.conforttravel.api.venta.mapper.VentaMapper;
import com.conforttravel.api.venta.repository.VentaRepository;
import com.conforttravel.api.venta.service.VentaService;
import com.conforttravel.api.venta.specification.VentaSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final VentaMapper ventaMapper;
    private final VentaSpecificationBuilder ventaSpecificationBuilder;
    private final AuditLogService auditLogService;

    @Override
    @Transactional
    public VentaResponse crear(VentaRequest request) {
        validarPrecios(request);

        Cliente cliente = obtenerCliente(request.clienteId());
        Usuario agente = obtenerAgente(request.agenteId());

        Venta venta = ventaMapper.toEntity(request);

        venta.setCliente(cliente);
        venta.setAgente(agente);

        Venta guardada = ventaRepository.save(venta);

        auditLogService.registrar(
                "CREAR",
                "VENTA",
                guardada.getId(),
                "Se creó la venta para el cliente: "
                        + guardada.getCliente().getId()
        );

        return ventaMapper.toResponseDto(guardada);
    }

    @Override
    public VentaResponse obtenerPorId(Long id) {
        return ventaMapper.toResponseDto(obtenerEntidad(id));
    }

    @Override
    public List<VentaResponse> obtenerTodos() {
        return ventaRepository.findAll()
                .stream()
                .map(ventaMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public VentaResponse actualizar(
            Long id,
            VentaRequest request
    ) {
        validarPrecios(request);

        Venta venta = obtenerEntidad(id);

        Cliente cliente = obtenerCliente(request.clienteId());
        Usuario agente = obtenerAgente(request.agenteId());

        venta.setFechaCompra(request.fechaCompra());
        venta.setPrecioInicial(request.precioInicial());
        venta.setPrecioFinal(request.precioFinal());
        venta.setObservaciones(request.observaciones());
        venta.setAdultos(request.adultos());
        venta.setInfantes(request.infantes());
        venta.setCliente(cliente);
        venta.setAgente(agente);

        Venta actualizada = ventaRepository.save(venta);

        auditLogService.registrar(
                "ACTUALIZAR",
                "VENTA",
                actualizada.getId(),
                "Se actualizó la venta"
        );

        return ventaMapper.toResponseDto(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Venta venta = obtenerEntidad(id);

        auditLogService.registrar(
                "ELIMINAR",
                "VENTA",
                venta.getId(),
                "Se eliminó la venta"
        );

        ventaRepository.delete(venta);
    }

    @Override
    public PageResponse<VentaResponse> buscar(
            VentaFilterRequest filter,
            Pageable pageable
    ) {
        return PageResponse.from(
                ventaRepository
                        .findAll(
                                ventaSpecificationBuilder.build(filter),
                                pageable
                        )
                        .map(ventaMapper::toResponseDto)
        );
    }

    private Venta obtenerEntidad(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Venta no encontrada con id: " + id
                        )
                );
    }

    private Cliente obtenerCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado con id: " + id
                        )
                );
    }

    private Usuario obtenerAgente(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario agente no encontrado con id: " + id
                        )
                );
    }

    private void validarPrecios(VentaRequest request) {
        BigDecimal precioInicial = request.precioInicial();
        BigDecimal precioFinal = request.precioFinal();

        if (precioFinal.compareTo(precioInicial) < 0) {
            throw new BusinessException(
                    "El precio final no puede ser menor que el precio inicial"
            );
        }
    }
}