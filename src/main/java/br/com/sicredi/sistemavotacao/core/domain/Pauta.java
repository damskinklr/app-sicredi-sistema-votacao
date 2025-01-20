package br.com.sicredi.sistemavotacao.core.domain;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record Pauta(
        UUID id,
        String title,
        String description,
        List<Voto> votos,
        boolean aberta,
        LocalDateTime abertura,
        LocalDateTime fechamento
) {
    public static Pauta create(String title, String description) {
        return new Pauta(UUID.randomUUID(), title, description, null, false, null, null);
    }
}
