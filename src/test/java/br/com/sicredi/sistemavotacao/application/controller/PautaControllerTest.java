package br.com.sicredi.sistemavotacao.application.controller;

import br.com.sicredi.sistemavotacao.application.dto.PautaDto;
import br.com.sicredi.sistemavotacao.application.dto.VotoDto;
import br.com.sicredi.sistemavotacao.core.domain.Pauta;
import br.com.sicredi.sistemavotacao.core.domain.PautaMock;
import br.com.sicredi.sistemavotacao.core.domain.ResultadoVotacao;
import br.com.sicredi.sistemavotacao.core.domain.ResultadoVotacaoMock;
import br.com.sicredi.sistemavotacao.core.domain.Voto;
import br.com.sicredi.sistemavotacao.core.domain.VotoMock;
import br.com.sicredi.sistemavotacao.core.domain.enums.OpcaoVoto;
import br.com.sicredi.sistemavotacao.core.usecase.AbrirSessaoUseCase;
import br.com.sicredi.sistemavotacao.core.usecase.CadastrarPautaUseCase;
import br.com.sicredi.sistemavotacao.core.usecase.ContabilizarVotosUseCase;
import br.com.sicredi.sistemavotacao.core.usecase.ReceberVotoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PautaControllerTest {

    @Mock
    private CadastrarPautaUseCase cadastrarPautaUseCase;

    @Mock
    private AbrirSessaoUseCase abrirSessaoUseCase;

    @Mock
    private ReceberVotoUseCase receberVotoUseCase;

    @Mock
    private ContabilizarVotosUseCase contabilizarVotosUseCase;

    @InjectMocks
    private PautaController pautaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarPautaComSucesso() {
        var pauta = PautaMock.createMock();
        when(cadastrarPautaUseCase.execute(any(String.class), any(String.class))).thenReturn(Mono.just(pauta));

        Mono<ResponseEntity<Pauta>> result = pautaController.cadastrarPauta(new PautaDto("Titulo", "Descricao"));

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful() && response.getBody().equals(pauta))
                .verifyComplete();
    }

    @Test
    void deveAbrirSessaoComSucesso() {
        var pauta = PautaMock.createMock();
        when(abrirSessaoUseCase.execute(any(UUID.class), any(Duration.class))).thenReturn(Mono.just(pauta));

        Mono<ResponseEntity<Pauta>> result = pautaController.abrirSessao(UUID.randomUUID(), 10);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful() && response.getBody().equals(pauta))
                .verifyComplete();
    }

    @Test
    void deveRegistrarVotoComSucesso() {
        var voto = VotoMock.createMock();
        when(receberVotoUseCase.execute(any(UUID.class), any(String.class), any(OpcaoVoto.class))).thenReturn(Mono.just(voto));

        Mono<ResponseEntity<Voto>> result = pautaController.votar(UUID.randomUUID(), new VotoDto("associado1", OpcaoVoto.SIM));

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful() && response.getBody().equals(voto))
                .verifyComplete();
    }

    @Test
    void deveContabilizarVotosComSucesso() {
        var resultado = ResultadoVotacaoMock.createMock();
        when(contabilizarVotosUseCase.execute(any(UUID.class))).thenReturn(Mono.just(resultado));

        Mono<ResponseEntity<ResultadoVotacao>> result = pautaController.contabilizarVotos(UUID.randomUUID());

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful() && response.getBody().equals(resultado))
                .verifyComplete();
    }
}
