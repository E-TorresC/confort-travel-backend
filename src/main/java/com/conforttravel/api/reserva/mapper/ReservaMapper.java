package com.conforttravel.api.reserva.mapper;

import com.conforttravel.api.reserva.dto.ReservaRequest;
import com.conforttravel.api.reserva.dto.ReservaResponse;
import com.conforttravel.api.reserva.entity.Reserva;
import com.conforttravel.api.shared.mapper.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface ReservaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tramo", ignore = true)
    @Mapping(target = "aerolinea", ignore = true)
    Reserva toEntity(ReservaRequest dto);

    @Mapping(target = "tramoId", source = "tramo.id")
    @Mapping(target = "aerolineaId", source = "aerolinea.id")
    ReservaResponse toResponseDto(Reserva entity);
}