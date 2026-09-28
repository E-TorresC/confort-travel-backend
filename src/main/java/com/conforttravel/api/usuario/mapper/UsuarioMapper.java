package com.conforttravel.api.usuario.mapper;

import com.conforttravel.api.shared.mapper.MapStructConfig;
import com.conforttravel.api.usuario.dto.UsuarioCreateRequest;
import com.conforttravel.api.usuario.dto.UsuarioRequest;
import com.conforttravel.api.usuario.dto.UsuarioResponse;
import com.conforttravel.api.usuario.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Usuario toEntity(UsuarioCreateRequest dto);

    UsuarioResponse toResponseDto(Usuario entity);
}