package com.conforttravel.api.cliente.service.impl;

import com.conforttravel.api.cliente.dto.ClienteRequest;
import com.conforttravel.api.cliente.dto.ClienteResponse;
import com.conforttravel.api.cliente.entity.Cliente;
import com.conforttravel.api.cliente.mapper.ClienteMapper;
import com.conforttravel.api.cliente.repository.ClienteRepository;
import com.conforttravel.api.cliente.service.ClienteService;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    @Transactional
    public ClienteResponse crear(ClienteRequest request) {
        Cliente cliente = clienteMapper.toEntity(request);

        Cliente guardado = clienteRepository.save(cliente);

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

        return clienteMapper.toResponseDto(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Cliente cliente = obtenerEntidad(id);

        clienteRepository.delete(cliente);
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