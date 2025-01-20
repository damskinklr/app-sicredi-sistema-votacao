package br.com.sicredi.sistemavotacao.core.domain;

import br.com.sicredi.sistemavotacao.core.domain.enums.OpcaoVoto;

import java.time.LocalDateTime;

public record Voto(
        Associado associado,
        OpcaoVoto option,
        LocalDateTime timestamp
) {
    public static Voto create(Associado associado, OpcaoVoto option) {
        return new Voto(
                associado,
                option,
                LocalDateTime.now()
        );
    }
}
