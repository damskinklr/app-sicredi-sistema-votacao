package br.com.sicredi.sistemavotacao.core.domain;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record Pauta(
        UUID id,
        String titulo,
        String descricao,
        boolean aberta,
        LocalDateTime abertura,
        LocalDateTime fechamento
) {
    public static Pauta create(String titulo, String descricao) {
        return new Pauta(UUID.randomUUID(), titulo, descricao,  false, null, null);
    }
}
