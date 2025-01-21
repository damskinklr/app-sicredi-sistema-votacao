package br.com.sicredi.sistemavotacao.core.domain;

import java.util.UUID;

public record ResultadoVotacao(
        UUID pautaId,
        long votosSim,
        long votosNao,
        long totalVotos
) {
    public static ResultadoVotacao create(UUID pautaId, long votosSim, long votosNao) {
        return new ResultadoVotacao(
                pautaId,
                votosSim,
                votosNao,
                votosSim + votosNao
        );
    }

    public boolean hasWinner() {
        return votosSim != votosNao;
    }

    public boolean isApproved() {
        return votosSim > votosNao;
    }

    public double getApprovalPercentage() {
        return totalVotos > 0 ? (double) votosSim / totalVotos * 100 : 0;
    }
}
