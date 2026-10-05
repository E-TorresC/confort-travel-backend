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
    @Mapping(target = "tramoCodigo", source = "tramo.codigo")
    @Mapping(target = "aerolineaId", source = "aerolinea.id")
    @Mapping(target = "aerolineaNombre", source = "aerolinea.nombre")
    @Mapping(target = "clienteNombres", source = "tramo.venta.cliente.nombres")
    @Mapping(target = "clienteApellidos", source = "tramo.venta.cliente.apellidos")
    ReservaResponse toResponseDto(Reserva entity);
}