package br.com.sicredi.sistemavotacao.core.usecase;

import br.com.sicredi.sistemavotacao.core.domain.ResultadoVotacao;
import br.com.sicredi.sistemavotacao.core.domain.VotoMock;
import br.com.sicredi.sistemavotacao.core.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ContabilizarVotosUseCaseTest {

    @Mock
    private VotoRepository votoRepository;

    @InjectMocks
    private ContabilizarVotosUseCase contabilizarVotosUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveContabilizarVotosComSucesso() {
        var pautaId = UUID.fromString("b554275f-3f2f-4f46-8756-f6c2e52ab601");
        var votos = VotoMock.createMockFlux();
        when(votoRepository.findByPautaId(any(UUID.class))).thenReturn(votos);

        Mono<ResultadoVotacao> result = contabilizarVotosUseCase.execute(pautaId);

        StepVerifier.create(result)
                .expectNextMatches(r -> r.totalVotos() == 2)
                .verifyComplete();
    }

    @Test
    void deveRetornarErroQuandoNaoEncontrarVotos() {
        UUID pautaId = UUID.randomUUID();
        when(votoRepository.findByPautaId(any(UUID.class))).thenReturn(Flux.empty());

        Mono<ResultadoVotacao> result = contabilizarVotosUseCase.execute(pautaId);

        StepVerifier.create(result)
                .expectNextMatches(resultadoVotacao -> resultadoVotacao.totalVotos() == 0)
                .verifyComplete();
    }
}