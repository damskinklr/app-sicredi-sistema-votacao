package br.com.sicredi.sistemavotacao.core.service;

import br.com.sicredi.sistemavotacao.core.domain.Associado;
import br.com.sicredi.sistemavotacao.infra.persistence.AssociadoRepositoryImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
public class AssociadoService {

    private AssociadoRepositoryImpl associadoRepositoryImpl;

    public AssociadoService(AssociadoRepositoryImpl associadoRepositoryImpl) {
        this.associadoRepositoryImpl = associadoRepositoryImpl;
    }

    public Mono<Associado> save(final Associado associado) {
        return associadoRepositoryImpl.save(associado);
    }

    public Mono<Associado> findById(UUID id) {
        return associadoRepositoryImpl.findById(id);
    }

}
