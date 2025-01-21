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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CadastrarPautaUseCaseTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private CadastrarPautaUseCase cadastrarPautaUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarPautaComSucesso() {
        Pauta pauta = new Pauta(null, "Titulo", "Descricao", true, null, null);
        when(pautaRepository.save(any(Pauta.class))).thenReturn(Mono.just(pauta));

        Mono<Pauta> result = cadastrarPautaUseCase.execute("Titulo", "Descricao");

        StepVerifier.create(result)
                .expectNextMatches(p -> p.titulo().equals("Titulo") && p.descricao().equals("Descricao"))
                .verifyComplete();
    }
}