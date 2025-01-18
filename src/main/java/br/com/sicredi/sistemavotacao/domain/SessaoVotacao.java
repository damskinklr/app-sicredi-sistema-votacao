package br.com.sicredi.sistemavotacao.domain;

import java.time.LocalDateTime;

public record SessaoVotacao(
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isOpen
) {
    public static SessaoVotacao create(LocalDateTime startTime, LocalDateTime endTime) {
        return new SessaoVotacao(startTime, endTime, true);
    }
}
