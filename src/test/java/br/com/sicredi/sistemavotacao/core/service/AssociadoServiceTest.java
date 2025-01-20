package br.com.sicredi.sistemavotacao.core.service;

import br.com.sicredi.sistemavotacao.core.domain.Associado;
import br.com.sicredi.sistemavotacao.core.domain.AssociadoMock;
import br.com.sicredi.sistemavotacao.infra.persistence.AssociadoRepositoryImpl;
import br.com.sicredi.sistemavotacao.infra.persistence.mappers.AssociadoMapper;
import br.com.sicredi.sistemavotacao.infra.persistence.mappers.AssociadoMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AssociadoServiceTest {
    @Mock
    private AssociadoRepositoryImpl associadoRepositoryImpl;
    @Mock
    private AssociadoMapper mapper;

    @InjectMocks
    private AssociadoService associadoService;


    private Associado associado;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        associado = AssociadoMock.createAssociadoMock();
    }

    @Test
    void testSave() {
        when(associadoRepositoryImpl.save(any(Associado.class))).thenReturn(Mono.just(associado));

        Mono<Associado> result = associadoService.save(associado);

        assertEquals(associado, result.block());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        when(associadoRepositoryImpl.findById(id)).thenReturn(Mono.just(associado));

        Mono<Associado> result = associadoService.findById(id);

        assertEquals(associado, result.block());
    }
}