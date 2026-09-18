package com.conforttravel.api.aerolinea.service.impl;

import com.conforttravel.api.aerolinea.dto.AerolineaRequest;
import com.conforttravel.api.aerolinea.dto.AerolineaResponse;
import com.conforttravel.api.aerolinea.entity.Aerolinea;
import com.conforttravel.api.aerolinea.mapper.AerolineaMapper;
import com.conforttravel.api.aerolinea.repository.AerolineaRepository;
import com.conforttravel.api.aerolinea.service.AerolineaService;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AerolineaServiceImpl implements AerolineaService {

    private final AerolineaRepository aerolineaRepository;
    private final AerolineaMapper aerolineaMapper;

    @Override
    @Transactional
    public AerolineaResponse crear(AerolineaRequest request) {
        Aerolinea aerolinea = aerolineaMapper.toEntity(request);

        if (aerolinea.getActivo() == null) {
            aerolinea.setActivo(true);
        }

        Aerolinea guardada = aerolineaRepository.save(aerolinea);

        return aerolineaMapper.toResponseDto(guardada);
    }

    @Override
    public AerolineaResponse obtenerPorId(Long id) {
        return aerolineaMapper.toResponseDto(obtenerEntidad(id));
    }

    @Override
    public List<AerolineaResponse> obtenerTodos() {
        return aerolineaRepository.findAll()
                .stream()
                .map(aerolineaMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public AerolineaResponse actualizar(Long id, AerolineaRequest request) {
        Aerolinea aerolinea = obtenerEntidad(id);

        aerolinea.setNombre(request.nombre());
        aerolinea.setCodigo(request.codigo());

        if (request.activo() != null) {
            aerolinea.setActivo(request.activo());
        }

        Aerolinea actualizada = aerolineaRepository.save(aerolinea);

        return aerolineaMapper.toResponseDto(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Aerolinea aerolinea = obtenerEntidad(id);

        aerolineaRepository.delete(aerolinea);
    }

    private Aerolinea obtenerEntidad(Long id) {
        return aerolineaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aerolínea no encontrada con id: " + id
                        )
                );
    }
}