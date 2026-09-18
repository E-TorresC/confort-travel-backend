package com.conforttravel.api.aerolinea.mapper;

import com.conforttravel.api.aerolinea.dto.AerolineaRequest;
import com.conforttravel.api.aerolinea.dto.AerolineaResponse;
import com.conforttravel.api.aerolinea.entity.Aerolinea;
import com.conforttravel.api.shared.mapper.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface AerolineaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Aerolinea toEntity(AerolineaRequest dto);

    AerolineaResponse toResponseDto(Aerolinea entity);
}