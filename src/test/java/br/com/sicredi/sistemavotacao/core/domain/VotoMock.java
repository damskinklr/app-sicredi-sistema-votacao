package br.com.sicredi.sistemavotacao.core.domain;

import br.com.sicredi.sistemavotacao.core.domain.enums.OpcaoVoto;
import reactor.core.publisher.Flux;

import java.util.UUID;

public class VotoMock {

    public static Voto createMock() {
        return new Voto(UUID.fromString("b554275f-3f2f-4f46-8756-f6c2e52ab601"), UUID.randomUUID().toString(), OpcaoVoto.SIM);
    }

    public static Flux<Voto> createMockFlux(){
        return Flux.just(createMock(), createMock());
    }
}
