package br.com.sicredi.sistemavotacao.core.repository;

import br.com.sicredi.sistemavotacao.core.domain.Associado;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AssociadoRepository {
    Mono<Associado> save(Associado associado);
    Mono<Associado> findById(UUID id);
}
