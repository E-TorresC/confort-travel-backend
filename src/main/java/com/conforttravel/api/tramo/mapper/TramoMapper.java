package com.conforttravel.api.tramo.mapper;

import com.conforttravel.api.shared.mapper.MapStructConfig;
import com.conforttravel.api.tramo.dto.TramoRequest;
import com.conforttravel.api.tramo.dto.TramoResponse;
import com.conforttravel.api.tramo.entity.Tramo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface TramoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo", ignore = true)
    @Mapping(target = "venta", ignore = true)
    @Mapping(target = "reserva", ignore = true)
    @Mapping(target = "checkIn", ignore = true)
    Tramo toEntity(TramoRequest dto);

    @Mapping(target = "ventaId", source = "venta.id")
    @Mapping(target = "reservaId", source = "reserva.id")
    @Mapping(target = "checkInId", source = "checkIn.id")
    @Mapping(target = "clienteNombres", source = "venta.cliente.nombres")
    @Mapping(target = "clienteApellidos", source = "venta.cliente.apellidos")
    TramoResponse toResponseDto(Tramo entity);
}