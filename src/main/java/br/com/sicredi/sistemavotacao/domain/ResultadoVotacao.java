package br.com.sicredi.sistemavotacao.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResultadoVotacao(
        UUID agendaId,
        long yesVotes,
        long noVotes,
        long totalVotes,
        LocalDateTime closedAt
) {
    public static ResultadoVotacao create(UUID agendaId, long yesVotes, long noVotes) {
        return new ResultadoVotacao(
                agendaId,
                yesVotes,
                noVotes,
                yesVotes + noVotes,
                LocalDateTime.now()
        );
    }

    public boolean hasWinner() {
        return yesVotes != noVotes;
    }

    public boolean isApproved() {
        return yesVotes > noVotes;
    }

    public double getApprovalPercentage() {
        return totalVotes > 0 ? (double) yesVotes / totalVotes * 100 : 0;
    }
}
