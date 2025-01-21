package br.com.sicredi.sistemavotacao.infra.persistence.mappers;

import br.com.sicredi.sistemavotacao.core.domain.Pauta;
import br.com.sicredi.sistemavotacao.infra.persistence.entities.PautaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PautaMapper {

    PautaEntity toEntity(Pauta pauta);

    Pauta toDomain(PautaEntity entity);
}
