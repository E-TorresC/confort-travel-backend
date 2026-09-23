package com.conforttravel.api.notificacion.mapper;

import com.conforttravel.api.notificacion.dto.NotificacionResponse;
import com.conforttravel.api.notificacion.entity.Notificacion;
import com.conforttravel.api.shared.mapper.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface NotificacionMapper {

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "checkInId", source = "checkIn.id")
    NotificacionResponse toResponseDto(Notificacion entity);
}