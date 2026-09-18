package com.conforttravel.api.checkin.mapper;

import com.conforttravel.api.checkin.dto.CheckInRequest;
import com.conforttravel.api.checkin.dto.CheckInResponse;
import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.shared.mapper.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface CheckInMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaDisponible", ignore = true)
    @Mapping(target = "fechaNotificacion", ignore = true)
    @Mapping(target = "fechaAtencion", ignore = true)
    @Mapping(target = "tramo", ignore = true)
    CheckIn toEntity(CheckInRequest dto);

    @Mapping(target = "tramoId", source = "tramo.id")
    CheckInResponse toResponseDto(CheckIn entity);
}