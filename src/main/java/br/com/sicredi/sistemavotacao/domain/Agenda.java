package br.com.sicredi.sistemavotacao.domain;

import java.util.UUID;

public record Agenda(
        UUID id,
        String title,
        String description,
        SessaoVotacao sessaoVotacao
) {
    public static Agenda create(String title, String description) {
        return new Agenda(UUID.randomUUID(), title, description, null);
    }

    public Agenda withVotingSession(SessaoVotacao sessaoVotacao) {
        return new Agenda(this.id, this.title, this.description, sessaoVotacao);
    }
}
