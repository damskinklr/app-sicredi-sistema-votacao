package br.com.sicredi.sistemavotacao.infra.persistence.mappers;

import br.com.sicredi.sistemavotacao.core.domain.Associado;
import br.com.sicredi.sistemavotacao.infra.persistence.entities.AssociadoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

    AssociadoEntity toEntity(Associado associado);

    Associado toDomain(AssociadoEntity entity);
}
