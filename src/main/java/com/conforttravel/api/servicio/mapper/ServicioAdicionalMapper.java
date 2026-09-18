package com.conforttravel.api.servicio.mapper;

import com.conforttravel.api.servicio.dto.ServicioAdicionalRequest;
import com.conforttravel.api.servicio.dto.ServicioAdicionalResponse;
import com.conforttravel.api.servicio.entity.ServicioAdicional;
import com.conforttravel.api.shared.mapper.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface ServicioAdicionalMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ServicioAdicional toEntity(ServicioAdicionalRequest dto);

    ServicioAdicionalResponse toResponseDto(ServicioAdicional entity);
}