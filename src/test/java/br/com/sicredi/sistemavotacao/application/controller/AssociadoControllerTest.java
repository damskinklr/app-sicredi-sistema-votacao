package br.com.sicredi.sistemavotacao.application.controller;

import br.com.sicredi.sistemavotacao.core.domain.Associado;
import br.com.sicredi.sistemavotacao.core.domain.AssociadoMock;
import br.com.sicredi.sistemavotacao.core.service.AssociadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AssociadoControllerTest {

    @Mock
    private AssociadoService associadoService;

    @InjectMocks
    private AssociadoController associadoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarAssociadoComSucesso() {
        var associado = AssociadoMock.createAssociadoMock();
        when(associadoService.save(any(Associado.class))).thenReturn(Mono.just(associado));

        Mono<Associado> result = associadoController.criarNovoAssociado(associado);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.cpf().equals(associado.cpf()))
                .verifyComplete();
    }

    @Test
    void deveBuscarAssociadoPorIdComSucesso() {
        var associado = AssociadoMock.createAssociadoMock();
        when(associadoService.findById(any(UUID.class))).thenReturn(Mono.just(associado));

        Mono<Associado> result = associadoController.retornarAssociadoPorId(associado.id());

        StepVerifier.create(result)
                .expectNextMatches(response -> response.cpf().equals(associado.cpf()))
                .verifyComplete();
    }
}
