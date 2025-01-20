package br.com.sicredi.sistemavotacao.core.domain;

import br.com.sicredi.sistemavotacao.core.domain.enums.StatusVoto;

import java.util.UUID;

public record Associado(
        UUID id,
        String name,
        String cpf,
        StatusVoto statusVoto
) {

    public static Associado create(String name, String cpf, StatusVoto statusVoto) {
        return new Associado(UUID.randomUUID(), name, cpf, statusVoto);
    }

    public boolean canVote() {
        return statusVoto == StatusVoto.ABLE_TO_VOTE;
    }
}
