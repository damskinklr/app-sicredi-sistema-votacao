package br.com.sicredi.sistemavotacao.core.domain;

import java.util.UUID;

public class ResultadoVotacaoMock {

    public static ResultadoVotacao createMock() {
        return new ResultadoVotacao(UUID.randomUUID(), 5L, 2L, 7L);
    }
}
