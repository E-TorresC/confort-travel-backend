package com.conforttravel.api.venta.mapper;

import com.conforttravel.api.shared.mapper.MapStructConfig;
import com.conforttravel.api.venta.dto.VentaServicioRequest;
import com.conforttravel.api.venta.dto.VentaServicioResponse;
import com.conforttravel.api.venta.entity.VentaServicio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface VentaServicioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "venta", ignore = true)
    @Mapping(target = "servicioAdicional", ignore = true)
    VentaServicio toEntity(VentaServicioRequest dto);

    @Mapping(target = "ventaId", source = "venta.id")
    @Mapping(target = "servicioAdicionalId", source = "servicioAdicional.id")
    VentaServicioResponse toResponseDto(VentaServicio entity);
}