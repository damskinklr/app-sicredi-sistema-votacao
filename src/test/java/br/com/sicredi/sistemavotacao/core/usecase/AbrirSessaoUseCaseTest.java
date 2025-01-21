package br.com.sicredi.sistemavotacao.core.usecase;

import br.com.sicredi.sistemavotacao.core.domain.Pauta;
import br.com.sicredi.sistemavotacao.core.repository.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AbrirSessaoUseCaseTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private AbrirSessaoUseCase abrirSessaoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAbrirSessaoComSucesso() {
        UUID pautaId = UUID.randomUUID();
        Pauta pauta = new Pauta(pautaId, "Titulo", "Descricao", true, null, null);
        when(pautaRepository.findById(any(UUID.class))).thenReturn(Mono.just(pauta));
        when(pautaRepository.save(any(Pauta.class))).thenReturn(Mono.just(pauta));

        Mono<Pauta> result = abrirSessaoUseCase.execute(pautaId, Duration.ofMinutes(10));

        StepVerifier.create(result)
                .expectNextMatches(p -> p.descricao() != null)
                .verifyComplete();
    }

    @Test
    void deveRetornarErroQuandoPautaNaoEncontrada() {
        UUID pautaId = UUID.randomUUID();
        when(pautaRepository.findById(any(UUID.class))).thenReturn(Mono.empty());

        Mono<Pauta> result = abrirSessaoUseCase.execute(pautaId, Duration.ofMinutes(10));

        StepVerifier.create(result)
                .verifyComplete();
    }
}
