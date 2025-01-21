package br.com.sicredi.sistemavotacao.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public Pauta abrirSessao(boolean aberta, LocalDateTime abertura, LocalDateTime fechamento) {
        return new Pauta(id, titulo, descricao, aberta, abertura, fechamento);
    }
}
