package br.com.sicredi.sistemavotacao.application.dto;

import br.com.sicredi.sistemavotacao.core.domain.enums.OpcaoVoto;

public record VotoDto(
        String associadoId,
        OpcaoVoto voto
) {
}
