package br.com.sicredi.sistemavotacao.core.domain;

import br.com.sicredi.sistemavotacao.core.domain.enums.OpcaoVoto;

import java.util.UUID;

public record Voto(
        UUID pautaId,
        String associadoId,
        OpcaoVoto opcaoVoto
) {
    public static Voto create(UUID pautaId, String associadoId, OpcaoVoto opcaoVoto) {
        return new Voto(
                pautaId,
                associadoId,
                opcaoVoto
        );
    }
}
