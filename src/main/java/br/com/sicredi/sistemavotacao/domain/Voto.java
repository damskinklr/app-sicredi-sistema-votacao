package br.com.sicredi.sistemavotacao.domain;

import br.com.sicredi.sistemavotacao.domain.enums.OpcaoVoto;

import java.time.LocalDateTime;
import java.util.UUID;

public record Voto(
        UUID id,
        UUID agendaId,
        Associado associado,
        OpcaoVoto option,
        LocalDateTime timestamp
) {
    public static Voto create(UUID agendaId, Associado associado, OpcaoVoto option) {
        return new Voto(
                UUID.randomUUID(),
                agendaId,
                associado,
                option,
                LocalDateTime.now()
        );
    }
}
