package com.conforttravel.api.viaje.mapper;

import com.conforttravel.api.shared.mapper.MapStructConfig;
import com.conforttravel.api.viaje.dto.ViajeRequest;
import com.conforttravel.api.viaje.dto.ViajeResponse;
import com.conforttravel.api.viaje.entity.Viaje;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface ViajeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "venta", ignore = true)
    @Mapping(target = "tramos", ignore = true)
    Viaje toEntity(ViajeRequest dto);

    @Mapping(target = "ventaId", source = "venta.id")
    ViajeResponse toResponseDto(Viaje entity);
}