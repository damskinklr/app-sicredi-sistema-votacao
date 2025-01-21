package br.com.sicredi.sistemavotacao.infra.gateway.response;

import br.com.sicredi.sistemavotacao.core.domain.enums.StatusVoto;

public record CpfValidationResponse(
        StatusVoto status
) {
}
