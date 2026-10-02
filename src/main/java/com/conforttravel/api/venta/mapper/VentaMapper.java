package com.conforttravel.api.venta.mapper;

import com.conforttravel.api.shared.mapper.MapStructConfig;
import com.conforttravel.api.venta.dto.VentaRequest;
import com.conforttravel.api.venta.dto.VentaResponse;
import com.conforttravel.api.venta.entity.Venta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface VentaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "agente", ignore = true)
    @Mapping(target = "tramos", ignore = true)   // <-- reemplaza a "viaje"
    Venta toEntity(VentaRequest dto);

    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "agenteId", source = "agente.id")
        // Ya NO mapeamos viajeId porque ese campo ya no existe en VentaResponse
    VentaResponse toResponseDto(Venta entity);
}