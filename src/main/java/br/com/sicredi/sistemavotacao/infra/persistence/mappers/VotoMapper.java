package br.com.sicredi.sistemavotacao.infra.persistence.mappers;

import br.com.sicredi.sistemavotacao.core.domain.Voto;
import br.com.sicredi.sistemavotacao.infra.persistence.entities.VotoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VotoMapper {

    VotoEntity toEntity(Voto voto);

    Voto toDomain(VotoEntity entity);
}
