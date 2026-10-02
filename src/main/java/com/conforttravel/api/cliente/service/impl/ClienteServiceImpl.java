package com.conforttravel.api.cliente.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.cliente.dto.ClienteFilterRequest;
import com.conforttravel.api.cliente.dto.ClienteRequest;
import com.conforttravel.api.cliente.dto.ClienteResponse;
import com.conforttravel.api.cliente.entity.Cliente;
import com.conforttravel.api.cliente.mapper.ClienteMapper;
import com.conforttravel.api.cliente.repository.ClienteRepository;
import com.conforttravel.api.cliente.service.ClienteService;
import com.conforttravel.api.shared.dto.PageResponse;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.shared.util.GeneradorCodigoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.conforttravel.api.cliente.specification.ClienteSpecificationBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final ClienteSpecificationBuilder clienteSpecificationBuilder;
    private final AuditLogService auditLogService;
    private final GeneradorCodigoUtil generadorCodigoUtil;

    @Override
    @Transactional
    public ClienteResponse crear(ClienteRequest request) {
        Cliente cliente = clienteMapper.toEntity(request);

        String ultimoCodigo = clienteRepository.findTopByOrderByIdDesc()
                .map(Cliente::getCodigo)
                .orElse(null);
        cliente.setCodigo(generadorCodigoUtil.generarSiguiente("CLI", ultimoCodigo));

        Cliente guardado = clienteRepository.save(cliente);

        auditLogService.registrar(
                "CREAR",
                "CLIENTE",
                guardado.getId(),
                "Se creó el cliente: "
                        + guardado.getNombres()
                        + " "
                        + guardado.getApellidos()
        );

        return clienteMapper.toResponseDto(guardado);
    }

    @Override
    public ClienteResponse obtenerPorId(Long id) {
        Cliente cliente = obtenerEntidad(id);

        return clienteMapper.toResponseDto(cliente);
    }

    @Override
    public List<ClienteResponse> obtenerTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public ClienteResponse actualizar(Long id, ClienteRequest request) {
        Cliente cliente = obtenerEntidad(id);

        cliente.setNombres(request.nombres());
        cliente.setApellidos(request.apellidos());
        cliente.setTelefono(request.telefono());

        Cliente actualizado = clienteRepository.save(cliente);

        auditLogService.registrar(
                "ACTUALIZAR",
                "CLIENTE",
                actualizado.getId(),
                "Se actualizó el cliente: "
                        + actualizado.getNombres()
                        + " "
                        + actualizado.getApellidos()
        );

        return clienteMapper.toResponseDto(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Cliente cliente = obtenerEntidad(id);

        auditLogService.registrar(
                "ELIMINAR",
                "CLIENTE",
                cliente.getId(),
                "Se eliminó el cliente: "
                        + cliente.getNombres()
                        + " "
                        + cliente.getApellidos()
        );

        clienteRepository.delete(cliente);
    }

    @Override
    public PageResponse<ClienteResponse> buscar(
            ClienteFilterRequest filter,
            Pageable pageable
    ) {
        return PageResponse.from(
                clienteRepository
                        .findAll(
                                clienteSpecificationBuilder.build(filter),
                                pageable
                        )
                        .map(clienteMapper::toResponseDto)
        );
    }

    private Cliente obtenerEntidad(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado con id: " + id
                        )
                );
    }
}