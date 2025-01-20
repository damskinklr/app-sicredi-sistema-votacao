package br.com.sicredi.sistemavotacao.core.repository;

import br.com.sicredi.sistemavotacao.core.domain.Pauta;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PautaRepository {

    Mono<Pauta> save(Pauta pauta);

    Mono<Pauta> findById(UUID id);
}
