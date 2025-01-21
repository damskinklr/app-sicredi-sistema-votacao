package br.com.sicredi.sistemavotacao.core.usecase;

import br.com.sicredi.sistemavotacao.core.domain.Pauta;
import br.com.sicredi.sistemavotacao.core.domain.PautaMock;
import br.com.sicredi.sistemavotacao.core.domain.Voto;
import br.com.sicredi.sistemavotacao.core.domain.VotoMock;
import br.com.sicredi.sistemavotacao.core.domain.enums.OpcaoVoto;
import br.com.sicredi.sistemavotacao.core.repository.PautaRepository;
import br.com.sicredi.sistemavotacao.core.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReceberVotoUseCaseTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private ReceberVotoUseCase receberVotoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveReceberVotoComSucesso() {
        var voto = VotoMock.createMock();
        var pauta = PautaMock.createMock();
        when(pautaRepository.findById(any(UUID.class))).thenReturn(Mono.just(pauta));
        when(votoRepository.existsByPautaIdAndAssociadoId(any(UUID.class), any(String.class))).thenReturn(Mono.just(false));
        when(votoRepository.save(any(Voto.class))).thenReturn(Mono.just(voto));

        Mono<Voto> result = receberVotoUseCase.execute(voto.pautaId(), voto.associadoId(), voto.opcaoVoto());

        StepVerifier.create(result)
                .expectNextMatches(v -> v.associadoId().equals(voto.associadoId()) && v.opcaoVoto() == OpcaoVoto.SIM)
                .verifyComplete();
    }

    @Test
    void deveRetornarErroQuandoSessaoEncerrada() {
        UUID pautaId = UUID.randomUUID();
        Pauta pauta = new Pauta(pautaId, "Titulo", "Descricao", true, LocalDateTime.now().minusHours(1), LocalDateTime.now().minusMinutes(30));
        when(pautaRepository.findById(any(UUID.class))).thenReturn(Mono.just(pauta));
        when(pautaRepository.save(any(Pauta.class))).thenReturn(Mono.just(pauta));

        Mono<Voto> result = receberVotoUseCase.execute(pautaId, "associado1", OpcaoVoto.SIM);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalStateException && throwable.getMessage().equals("Sessão encerrada"))
                .verify();
    }

    @Test
    void deveRetornarErroQuandoSessaoEncerradaDataFechamentoMenorQueAgora() {
        UUID pautaId = UUID.randomUUID();
        Pauta pauta = new Pauta(pautaId, "Titulo", "Descricao", true, LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1));
        when(pautaRepository.findById(any(UUID.class))).thenReturn(Mono.just(pauta));
        when(pautaRepository.save(any(Pauta.class))).thenReturn(Mono.just(pauta));

        Mono<Voto> result = receberVotoUseCase.execute(pautaId, "associado1", OpcaoVoto.SIM);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalStateException && throwable.getMessage().equals("Sessão encerrada"))
                .verify();
    }

    @Test
    void deveRetornarErroQuandoPautaNaoEstaAberta() {
        UUID pautaId = UUID.randomUUID();
        Pauta pauta = new Pauta(pautaId, "Titulo", "Descricao", false, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        when(pautaRepository.findById(any(UUID.class))).thenReturn(Mono.just(pauta));

        Mono<Voto> result = receberVotoUseCase.execute(pautaId, "associado1", OpcaoVoto.SIM);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalStateException && throwable.getMessage().equals("Sessão encerrada"))
                .verify();
    }

    @Test
    void deveRetornarErroQuandoAssociadoJaVotou() {
        UUID pautaId = UUID.randomUUID();
        Pauta pauta = new Pauta(pautaId, "Titulo", "Descricao", true, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        when(pautaRepository.findById(any(UUID.class))).thenReturn(Mono.just(pauta));
        when(votoRepository.existsByPautaIdAndAssociadoId(any(UUID.class), any(String.class))).thenReturn(Mono.just(true));

        Mono<Voto> result = receberVotoUseCase.execute(pautaId, "associado1", OpcaoVoto.SIM);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalStateException && throwable.getMessage().equals("Associado já votou"))
                .verify();
    }

    @Test
    void devePropagarErroComContextoAdicional() {
        UUID pautaId = UUID.randomUUID();
        Pauta pauta = new Pauta(pautaId, "Titulo", "Descricao", true, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        when(pautaRepository.findById(any(UUID.class))).thenReturn(Mono.just(pauta));
        when(votoRepository.existsByPautaIdAndAssociadoId(any(UUID.class), any(String.class))).thenReturn(Mono.error(new RuntimeException("Erro de teste")));

        Mono<Voto> result = receberVotoUseCase.execute(pautaId, "associado1", OpcaoVoto.SIM);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage().equals("Erro de teste"))
                .verify();
    }
}