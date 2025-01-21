package br.com.sicredi.sistemavotacao.core.repository;

import br.com.sicredi.sistemavotacao.core.domain.Voto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface VotoRepository {

    Mono<Voto> save(Voto voto);
    Mono<Boolean> existsByPautaIdAndAssociadoId(UUID pautaId, String associadoId);
    Flux<Voto> findByPautaId(UUID pautaId);
}
